package br.com.lucas.brewer.storage.local;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import br.com.lucas.brewer.service.exception.FalhaRemovendoFotoTemporariaException;
import br.com.lucas.brewer.storage.FotoStorageLocal;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

/***
 * 
 * @author Lucas Oliveira
 *
 */
public class FotoStorageLocalImpl implements FotoStorageLocal {

	private static final Logger LOGGER = LoggerFactory.getLogger(FotoStorageLocalImpl.class);
	private final String FOLDER_NAME = "\\.brewerimages";
	private final String TEMP_FOLDER_NAME = "\\temp";

	private final String local = System.getProperty("user.home").concat(FOLDER_NAME);
	private final String localTemporario = local.concat(TEMP_FOLDER_NAME);

	public FotoStorageLocalImpl() {
		montaPasta(local);
		montaPasta(localTemporario);
	}

	@Override
	public String saveImageInTempDirectory(MultipartFile[] files) {
		String novoNome = null;
		if (!isEmpty(files)) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(localTemporario.concat("\\" + novoNome)));
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.error("NÃO FOI POSSÍVEL GRAVAR A IMAGEM NO LOCAL TEMPORÁRIO.");
			}
		}
		return novoNome;
	}

	@Override
	public void saveImage(String foto) {
		File tempPath = new File(this.localTemporario);
		File path = new File(this.local);
		try {
			Files.move(tempPath.toPath().resolve(foto), path.toPath().resolve(foto));
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("NÃO FOI POSSÍVEL MOVER A IMAGEM PARA O LOCAL.");
		}
		
		try {
			Thumbnails.of(path.toPath().resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL); //redimensionando a imagem da cerveja
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("NÃO FOI POSSÍVEL REDIMENSIONAR A IMAGEM.");
		} 
	}

	@Override
	public byte[] recuperarFotoTemporaria(String nome) {
		Path path = Paths.get(localTemporario.concat("\\" + nome));
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new RuntimeException("NÃO FOI POSSÍVEL RECUPERAR A IMAGEM DA CERVEJA.");
		}
	}
	
	@Override
	public byte[] recuperarFoto(String nome) {
		Path path = Paths.get(local.concat("\\" + nome));
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new RuntimeException("NÃO FOI POSSÍVEL RECUPERAR A IMAGEM DA CERVEJA.");
		}
	}

	private void montaPasta(String path) {
		File file = new File(path);
		file.mkdirs();
		LOGGER.info("DIRETÓRIO: " + path + " CRIADO COM SUCESSO!");
	}

	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;
		return novoNome;
	}

	public void removerImagemTemporariaDaCerveja(String foto) throws FalhaRemovendoFotoTemporariaException {
		File tempPath = new File(this.localTemporario+"\\"+foto);
		if(tempPath.delete()) {
			LOGGER.info("A IMAGEM TEMPORARIA "+ foto +" FOI REMOVIDA");
		}else {
			throw new FalhaRemovendoFotoTemporariaException("Falha removendo foto temporária.");
		}
	}
}
