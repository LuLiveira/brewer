package br.com.lucas.brewer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.repository.filter.EstiloFilter;

public interface EstiloDAO {

	Estilo insertAndReturn(Estilo estilo); // Semelhante ao SaveAndFlush da JPA.

	void insert(Estilo estilo);

	Estilo selectEstiloById(Long id);

	Optional<Estilo> selectEstiloByNameIgnoreCase(String nome);

	List<Estilo> selectAll();

	Page<Estilo> selectByFilter(EstiloFilter estiloFilter, Pageable page);

}
