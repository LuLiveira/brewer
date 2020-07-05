package br.com.lucas.brewer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.lucas.brewer.model.dto.FotoDTO;

/**
 * 
 * @author Lucas Oliveira
 *
 */

public class FotoStorageRunnable implements Runnable {

	private MultipartFile[] file;
	private DeferredResult<FotoDTO> resultado;
	private FotoStorageLocal fotoStorage;

	public FotoStorageRunnable(MultipartFile[] file, DeferredResult<FotoDTO> resultado, FotoStorageLocal fotoStorage) {
		this.file = file;
		this.resultado = resultado;
		this.fotoStorage = fotoStorage;
	}

	@Override
	public void run() {
		String nomeFoto = fotoStorage.saveImageInTempDirectory(file);
		String contentType = file[0].getContentType();
		resultado.setResult(new FotoDTO(nomeFoto, contentType));
	}

}
