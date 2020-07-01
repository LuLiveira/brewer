package br.com.lucas.brewer.dao;

import java.util.List;
import java.util.Optional;

import br.com.lucas.brewer.model.Cerveja;

public interface CervejaDAO {

	void insertCerveja(Cerveja cerveja);
	Optional<String> findCervejaBySku(String sku);
	List<Cerveja> selectAll();
}
