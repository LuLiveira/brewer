package br.com.lucas.brewer.storage.local;

import java.io.File;

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

	private void montaPasta(String path) {
		File file = new File(path);
		file.mkdirs();
		LOGGER.info("DIRETÓRIO: " + path + " CRIADO COM SUCESSO!");
	}

	@Override
	public void saveImageInTempDirectory(MultipartFile[] files) {

	}
}
