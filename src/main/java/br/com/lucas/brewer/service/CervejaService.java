package br.com.lucas.brewer.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.brewer.model.Cerveja;
import br.com.lucas.brewer.repository.CervejaRepository;

@Service
public class CervejaService {

	@Autowired
	private CervejaRepository cervejaRepository;

	@Transactional
	public void salvar(Cerveja cerveja) {
		cervejaRepository.save(cerveja);
	}
}