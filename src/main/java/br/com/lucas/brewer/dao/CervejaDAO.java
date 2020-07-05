package br.com.lucas.brewer.dao;

import java.util.List;
import java.util.Optional;

import br.com.lucas.brewer.model.Cerveja;
import br.com.lucas.brewer.repository.filter.CervejaFilter;

public interface CervejaDAO {

	void insertCerveja(Cerveja cerveja);
	Optional<List<String>> findCervejaBySku(String sku);
	List<Cerveja> selectAll();
	List<Cerveja> selectByFilter(CervejaFilter filter);
}
