package br.com.lucas.brewer.service.event.cerveja;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.lucas.brewer.storage.FotoStorageLocal;

@Component
public class CervejaListener {
	
	private FotoStorageLocal fotoStorage;
	
	//private FotoStorageS3 fotoStorageS3; sera usado quando integrar com AmazonS3

	public CervejaListener(FotoStorageLocal fotoStorage) {
		this.fotoStorage = fotoStorage;
	}
	
	@EventListener
	public void cervejaSalva(CervejaEvent event) {
		fotoStorage.saveImage(event.getCerveja().getFoto());
	}
}
