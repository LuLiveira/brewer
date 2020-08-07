package br.com.lucas.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.lucas.brewer.model.dto.FotoDTO;
import br.com.lucas.brewer.storage.FotoStorageLocal;
import br.com.lucas.brewer.storage.FotoStorageRunnable;

/**
 * 
 * @author Lucas Oliveira
 *
 */

@RestController
@RequestMapping("/fotos")
public class FotoController {

	@Autowired
	private FotoStorageLocal fotoStorage;
	
	@PostMapping
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<FotoDTO> result = new DeferredResult<>();
		Thread thread = new Thread(new FotoStorageRunnable(files, result, fotoStorage));
		thread.start();
		return result;
	}
	
	@GetMapping("/temp/{nome:.*}")
	public byte[] recuperarFotoTemporaria(@PathVariable String nome) { 
		return fotoStorage.recuperarFotoTemporaria(nome);
	}
	
	@GetMapping("/{nome:.*}")
	public byte[] recuperarFoto(@PathVariable String nome) { 
		return fotoStorage.recuperarFoto(nome);
	}
}
