package br.com.lucas.brewer.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.lucas.brewer.dao.CervejaDAO;
import br.com.lucas.brewer.model.Cerveja;
import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.model.enums.Origem;
import br.com.lucas.brewer.model.enums.Sabor;

@Component
public class CervejaDAOImpl implements CervejaDAO {
	
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	public CervejaDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void insertCerveja(Cerveja cerveja) {
		StringBuilder query = new StringBuilder();
		query.append(" INSERT INTO cerveja ")
			 .append(" (sku, nome, descricao, valor, teor_alcoolico, comissao, sabor, origem, id_estilo, quantidade_estoque, foto) ")
			 .append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
		
		jdbcTemplate.update(query.toString(), //query
							cerveja.getSku(),
							cerveja.getNome(),
							cerveja.getDescricao(),
							cerveja.getValor(),
							cerveja.getTeorAlcoolico(),
							cerveja.getComissao(),
							cerveja.getSabor().getDescricao(),
							cerveja.getOrigem().getDescricao(),
							cerveja.getEstilo().getId(),
							cerveja.getQuantidadeEstoque(),
							cerveja.getFoto()
						);
	}

	@Override
	public Optional<String> findCervejaBySku(String sku) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT sku FROM cerveja WHERE sku = ? ");
		
		sku = jdbcTemplate.queryForObject(query.toString(), new Object[] {sku}, (rs, rowNumber) -> rs.getString("sku"));
		return Optional.ofNullable(sku);
	}

	@Override
	public List<Cerveja> selectAll() {
		StringBuilder query = new StringBuilder();
		query.append(" select c.*, e.nome as estilo from cerveja c inner join estilo e on c.id_estilo = e.id ");
		
		return jdbcTemplate.query(query.toString(), (rs, rowNumber) -> new Cerveja(
					rs.getLong("id"),
					rs.getString("sku"),
					rs.getString("nome"),
					rs.getString("descricao"),
					rs.getBigDecimal("valor"),
					rs.getBigDecimal("teor_alcoolico"),
					rs.getBigDecimal("comissao"),
					rs.getInt("quantidade_estoque"),
					rs.getString("origem"),
					rs.getString("sabor"),
					Estilo.estiloFactory(rs.getLong("id_estilo"), rs.getString("estilo")),
					rs.getString("foto"),
					rs.getString("content_type")
				));
	}
}
