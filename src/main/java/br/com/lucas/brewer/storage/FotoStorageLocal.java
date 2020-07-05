package br.com.lucas.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

import br.com.lucas.brewer.service.exception.FalhaRemovendoFotoTemporariaException;

public interface FotoStorageLocal {

	String saveImageInTempDirectory(MultipartFile[] files);

	byte[] recuperarFotoTemporaria(String nome);

	void saveImage(String foto);

	void removerImagemTemporariaDaCerveja(String foto) throws FalhaRemovendoFotoTemporariaException;

	byte[] recuperarFoto(String nome);
}
