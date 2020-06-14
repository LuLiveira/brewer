package br.com.lucas.brewer.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.brewer.dao.EstiloDAO;
import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.repository.EstiloRepository;
import br.com.lucas.brewer.service.exception.NomeEstiloJaCadastradoExcetion;

@Service
public class EstiloService {

	@Autowired @Deprecated
	private EstiloRepository estiloRepository;
	
	@Autowired
	private EstiloDAO estiloDAO;

	@Transactional
	public Estilo salvar(Estilo estilo) {
		validaEstiloExistente(estilo);
		return estiloDAO.insertAndReturn(estilo);
	}

	private void validaEstiloExistente(Estilo estilo) {
		Optional<Estilo> existingEstilo = estiloDAO.selectEstiloByNameIgnoreCase(estilo.getNome());
		if (existingEstilo.isPresent()) {
			throw new NomeEstiloJaCadastradoExcetion("Nome do estilo já cadastrado");
		}
	}
}
