package br.com.lucas.brewer.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.lucas.brewer.dao.CervejaDAO;
import br.com.lucas.brewer.model.Cerveja;
import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.repository.CervejaRepository;
import br.com.lucas.brewer.service.exception.CervejaDuplicadaException;

@Service
public class CervejaService {

	@Autowired @Deprecated
	private CervejaRepository cervejaRepository;
	
	@Autowired
	private CervejaDAO cervejaDAO;

	@Autowired
	private EstiloService estiloService;
	
	@Transactional
	public void cadastrarNova(Cerveja cerveja) throws CervejaDuplicadaException {
		
		Optional<String> id = cervejaDAO.findCervejaBySku(cerveja.getSku());
		
		if(!StringUtils.isEmpty(id)) {
			throw new CervejaDuplicadaException("Já existe uma cerveja cadastrada com este SKU.");
		}
		
		cervejaDAO.insertCerveja(cerveja);
	}

	public Estilo cadastrarNovo(Estilo estilo) {
		return estiloService.salvar(estilo);
	}
}
