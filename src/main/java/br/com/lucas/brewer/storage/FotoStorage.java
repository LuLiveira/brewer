package br.com.lucas.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	String saveImageInTempDirectory(MultipartFile[] files);

	byte[] recuperarFotoTemporaria(String nome);
	
}
