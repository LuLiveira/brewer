package br.com.lucas.brewer.dao;

import java.util.List;

public interface DAO<T> {
	
	void insert(T entity);
	
	List<T> selectAll();

	T insertAndReturn(T estilo); // Semelhante ao SaveAndFlush da JPA.

	T selectById(Long id);

}