package br.com.lucas.brewer.dao;

import java.util.List;
import java.util.Optional;

import br.com.lucas.brewer.model.Estilo;

public interface EstiloDAO {
	
	Estilo insertAndReturn(Estilo estilo); //Semelhante ao SaveAndFlush da JPA.
	
	void insert(Estilo estilo);
	
	Estilo selectEstiloById(Long id);
	
	Optional<Estilo> selectEstiloByNameIgnoreCase(String nome);

	List<Estilo> selectAll();
	
}
