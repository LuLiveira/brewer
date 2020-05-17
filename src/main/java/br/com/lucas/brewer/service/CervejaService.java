package br.com.lucas.brewer.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.brewer.model.Cerveja;
import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.repository.CervejaRepository;

@Service
public class CervejaService {

	@Autowired
	private CervejaRepository cervejaRepository;

	@Autowired
	private EstiloService estiloService;
	
	@Transactional
	public void salvar(Cerveja cerveja) {
		cervejaRepository.save(cerveja);
	}

	public Estilo salvar(Estilo estilo) {
		return estiloService.salvar(estilo);
	}
}
