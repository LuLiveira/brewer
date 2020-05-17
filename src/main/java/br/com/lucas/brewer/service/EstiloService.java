package br.com.lucas.brewer.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.repository.EstiloRepository;

@Service
public class EstiloService {

	@Autowired
	private EstiloRepository estiloRepository;
	
	@Transactional
	public void salvar(@Valid Estilo estilo) {
		estiloRepository.save(estilo);
	}

}
