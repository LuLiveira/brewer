package br.com.lucas.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	void saveImageInTempDirectory(MultipartFile[] files);
	
}
