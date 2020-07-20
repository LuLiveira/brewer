package br.com.lucas.brewer.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.com.lucas.brewer.dao.CervejaDAO;
import br.com.lucas.brewer.model.Cerveja;
import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.repository.CervejaRepository;
import br.com.lucas.brewer.service.event.cerveja.CervejaEvent;
import br.com.lucas.brewer.service.exception.CervejaDuplicadaException;
import br.com.lucas.brewer.service.exception.FalhaRemovendoFotoTemporariaException;
import br.com.lucas.brewer.storage.FotoStorageLocal;

@Service
public class CervejaService {

	@SuppressWarnings("unused")	@Autowired @Deprecated
	private CervejaRepository cervejaRepository;
	
	private final CervejaDAO cervejaDAO;
	private final ApplicationEventPublisher publisher;

	@Autowired
	private EstiloService estiloService;
	
	@Autowired
	private FotoStorageLocal fotoStorageLocal;

	
	public CervejaService(CervejaDAO cervejaDAO, ApplicationEventPublisher publisher) {
		this.cervejaDAO = cervejaDAO;
		this.publisher = publisher;
	}
	
	@Transactional
	public void cadastrarNova(Cerveja cerveja) throws CervejaDuplicadaException {
		
		Optional<List<String>> sku = cervejaDAO.findCervejaBySku(cerveja.getSku());
		
		if(!sku.get().isEmpty()) {
			throw new CervejaDuplicadaException("Já existe uma cerveja cadastrada com este SKU.");
		}
		
		cervejaDAO.insertCerveja(cerveja);
		
		publisher.publishEvent(new CervejaEvent(cerveja));
	}

	public Estilo cadastrarNovo(Estilo estilo) {
		return estiloService.salvar(estilo);
	}

	public String removerImagemTemporariaDaCerveja(String nomeFotoJson) {
		JSONObject json = new JSONObject(nomeFotoJson);
		String nomeFotoValue = (String) json.get("nomeFotoJson");
		String msg = null;
		try {
			fotoStorageLocal.removerImagemTemporariaDaCerveja(nomeFotoValue);
		}catch(FalhaRemovendoFotoTemporariaException e){
			msg = e.getMessage();
		}
		return msg;
	}
}
