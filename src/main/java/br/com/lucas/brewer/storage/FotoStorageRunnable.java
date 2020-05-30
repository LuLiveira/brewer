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

	public FotoStorageRunnable(MultipartFile[] file, DeferredResult<FotoDTO> resultado) {
		this.file = file;
		this.resultado = resultado;
	}

	@Override
	public void run() {
		String nomeFoto = file[0].getOriginalFilename();
		//file[0].getOriginalFilename().replace(file[0].getOriginalFilename(), "");
		String contentType = file[0].getContentType();
		resultado.setResult(new FotoDTO(nomeFoto, contentType));
	}

}
