package br.com.lucas.brewer.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.repository.EstiloRepository;
import br.com.lucas.brewer.service.exception.NomeEstiloJaCadastradoExcetion;

@Service
public class EstiloService {

	@Autowired
	private EstiloRepository estiloRepository;

	@Transactional
	public Estilo salvar(Estilo estilo) {
		validaEstiloExistente(estilo);
		return estiloRepository.saveAndFlush(estilo);
	}

	private void validaEstiloExistente(Estilo estilo) {
		Optional<Estilo> existingEstilo = estiloRepository.findByNomeIgnoreCase(estilo.getNome());
		if (existingEstilo.isPresent()) {
			throw new NomeEstiloJaCadastradoExcetion("Nome do estilo já cadastrado");
		}
	}
}
