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

import br.com.lucas.brewer.storage.FotoStorage;

/***
 * 
 * @author Lucas Oliveira
 *
 */
public class FotoStorageLocal implements FotoStorage {

	private static final Logger LOGGER = LoggerFactory.getLogger(FotoStorageLocal.class);
	private final String FOLDER_NAME = "\\.brewerimages";
	private final String TEMP_FOLDER_NAME = "\\temp";

	private final String local = System.getProperty("user.dir").concat(FOLDER_NAME);
	private final String localTemporario = local.concat(TEMP_FOLDER_NAME);

	public FotoStorageLocal() {
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
	public byte[] recuperarFotoTemporaria(String nome) {
		Path path = Paths.get(localTemporario.concat("\\"+nome));
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
}
