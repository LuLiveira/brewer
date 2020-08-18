package br.com.lucas.brewer.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.repository.filter.EstiloFilter;

public interface EstiloDAO extends DAO<Estilo>{

	Optional<Estilo> selectEstiloByNameIgnoreCase(String nome);

	Page<Estilo> selectByFilter(EstiloFilter estiloFilter, Pageable page);

}
