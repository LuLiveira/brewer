package br.com.lucas.brewer.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.lucas.brewer.model.Cerveja;
import br.com.lucas.brewer.repository.filter.CervejaFilter;

public interface CervejaDAO extends DAO<Cerveja> {

	Optional<String> findCervejaBySku(String sku);

	Page<Cerveja> selectByFilter(CervejaFilter filter, Pageable page);
}
