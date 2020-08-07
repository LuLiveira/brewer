package br.com.lucas.brewer.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.lucas.brewer.dao.CervejaDAO;
import br.com.lucas.brewer.model.Cerveja;
import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.repository.filter.CervejaFilter;

@Component
public class CervejaDAOImpl implements CervejaDAO {

	JdbcTemplate jdbcTemplate;

	@Autowired //verificar necessidade da annotation
	public CervejaDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void insertCerveja(Cerveja cerveja) {
		StringBuilder query = new StringBuilder();
		query.append(" INSERT INTO cerveja ")
			.append(" (sku, nome, descricao, valor, teor_alcoolico, comissao, sabor, origem, id_estilo, quantidade_estoque, foto) ")
			.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");

		jdbcTemplate.update(query.toString(), // query
				cerveja.getSku(), cerveja.getNome(), 
				cerveja.getDescricao(), 
				cerveja.getValor(),
				cerveja.getTeorAlcoolico(),
				cerveja.getComissao(), 
				cerveja.getSabor().getDescricao(),
				cerveja.getOrigem().getDescricao(), 
				cerveja.getEstilo().getId(), 
				cerveja.getQuantidadeEstoque(),
				cerveja.getFoto());
	}

	@Override
	public Optional<String> findCervejaBySku(String sku) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT sku FROM cerveja WHERE sku = ? ");

		try {
			String skuExists = jdbcTemplate.queryForObject(query.toString(), new Object[] { sku },
					(rs, rowNumber) -> rs.getString("sku"));
			return Optional.ofNullable(skuExists);
		}catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Cerveja> selectAll() {
		StringBuilder query = new StringBuilder();
		query.append(" select c.*, e.nome as estilo from cerveja c inner join estilo e on c.id_estilo = e.id ");

		return jdbcTemplate.query(query.toString(),
				(rs, rowNumber) -> new Cerveja(
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
						rs.getString("content_type")));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cerveja> selectByFilter(CervejaFilter filter, Pageable page) { //método de estudo (pode ser melhorado)

		int pageRows = page.getPageSize();
		int offset = page.getPageNumber() * pageRows;
		
		StringBuilder query = new StringBuilder();
		
		query.append(" select c.*, e.nome as estilo from cerveja c ")
			.append(" inner join estilo e on c.id_estilo = e.id ")
			.append(" where ");

		Object[] filtro = filterByParams(filter, query);
		
		orderBy(page, query);
			
		query.append(String.format(" limit %s", pageRows));
		query.append(String.format(" offset %s ", offset));

		List<Cerveja> cervejaList = jdbcTemplate.query(query.toString(), filtro,
				(rs, rowNumber) -> new Cerveja(
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
						rs.getString("content_type")));
		
		return new PageImpl<>(cervejaList, page, total(filter));
	}

	private void orderBy(Pageable page, StringBuilder query) {
		Sort sort = page.getSort();
		if(!sort.isUnsorted()) {
			Sort.Order order = sort.iterator().next();
			String campo  	 = order.getProperty();
			query.append(String.format(" order by %s ", order.isAscending() ? Order.asc(campo) : Order.desc(campo)));
		}
	}

	private int total(CervejaFilter filter) {
		StringBuilder query = new StringBuilder();
		query.append(" select count(c.id) from cerveja c ")
			 .append(" inner join estilo e on c.id_estilo = e.id ")
			 .append(" where ");
		
		Object[] filtro = filterByParams(filter, query);
		
		return jdbcTemplate.queryForObject(query.toString(), filtro, Integer.class);
	}

	private Object[] filterByParams(CervejaFilter filter, StringBuilder query) {
		Object[] filtro = new Object[7];

		if (!StringUtils.isEmpty(filter.getSku())) {
			query.append(" c.sku like ? ");
			filtro[0] = "%" + filter.getSku() + "%";
		} else {
			query.append(" c.sku != ? ");
			filtro[0] = "";
		}
		if (!StringUtils.isEmpty(filter.getNome())) {
			query.append(" and c.nome like ? ");
			filtro[1] = "%" + filter.getNome() + "%";
		} else {
			query.append(" and c.nome != ? ");
			filtro[1] = "";
		}
		if (filter.getEstilo() != null && !StringUtils.isEmpty(filter.getEstilo().getNome())) {
			query.append(" and e.nome = ? ");
			filtro[2] = filter.getEstilo().getNome();
		} else {
			query.append(" and e.nome != ? ");
			filtro[2] = "";
		}
		if (filter.getSabor() != null && !StringUtils.isEmpty(filter.getSabor().getDescricao())) {
			query.append(" and c.sabor = ? ");
			filtro[3] = filter.getSabor().getDescricao();
		} else {
			query.append(" and c.sabor != ? ");
			filtro[3] = "";
		}
		if (filter.getOrigem() != null && !StringUtils.isEmpty(filter.getOrigem().getDescricao())) {
			query.append(" and c.origem = ? ");
			filtro[4] = filter.getOrigem().getDescricao();
		} else {
			query.append(" and c.origem != ? ");
			filtro[4] = "";
		}
		if (filter.getValorDe() != null && filter.getValorAte() != null) {
			query.append(" and c.valor between ? and ? ");
			filtro[5] = filter.getValorDe();
			filtro[6] = filter.getValorAte();
		} else {
			query.append(" and c.valor > ?  and c.valor < ? ");
			filtro[5] = "0";
			filtro[6] = "999999999";
		}
		
		return filtro;
	}
}
