package br.com.lucas.brewer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.lucas.brewer.dao.EstiloDAO;
import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.repository.filter.EstiloFilter;

@Component
public class EstiloDAOImpl implements EstiloDAO {

	private Object[] filtro;
	JdbcTemplate jdbcTemplate;

	public EstiloDAOImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Estilo insertAndReturn(Estilo estilo) {
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		StringBuilder query = new StringBuilder();
		query.append(" INSERT INTO estilo ").append(" ( nome ) values ( ? ) ");

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, estilo.getNome());
				return statement;
			}
		}, holder);

		long primaryKey = holder.getKey().longValue();

		return selectById(primaryKey);
	}

	@Override
	public void insert(Estilo estilo) {
		StringBuilder query = new StringBuilder();
		query.append(" INSERT INTO estilo ").append(" ( nome ) values ( ? ) ");

		jdbcTemplate.update(query.toString(), estilo.getNome());
	}

	@Override
	public Estilo selectById(Long id) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM estilo WHERE id = ? ");

		return jdbcTemplate.queryForObject(query.toString(), new Object[] { id },
				(rs, rowNumber) -> new Estilo(rs.getLong("id"), rs.getString("nome")));
	}

	@Override
	public Optional<Estilo> selectEstiloByNameIgnoreCase(String nome) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM estilo WHERE upper(nome) = ? ");

		Estilo estilo = jdbcTemplate.queryForObject(query.toString(), new Object[] { nome },
				(rs, rowNumber) -> new Estilo(rs.getLong("id"), rs.getString("nome")));

		return Optional.ofNullable(estilo);
	}

	@Override
	public List<Estilo> selectAll() {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM estilo ");

		return jdbcTemplate.query(query.toString(),
				(rs, rowNumber) -> new Estilo(rs.getLong("id"), rs.getString("nome")));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Estilo> selectByFilter(EstiloFilter estiloFilter, Pageable page) { // talvez centralizar futuramente
																					// pois metodos parecidos sao
																					// usandos para filtrar outras
																					// entidade (cerveja/usuario/etc)
		int pageRows = page.getPageSize();
		int offset = page.getPageNumber() * pageRows;
		filtro = new Object[1];

		StringBuilder query = new StringBuilder();

		query.append(" select * from estilo where nome ");

		if(!StringUtils.isEmpty(estiloFilter.getNome())) {
			query.append(" like ? ");
			filtro[0] = "%"+ estiloFilter.getNome() +"%";
		}else {
			query.append(" != ? ");
			filtro[0] = "";
		}
		
		orderBy(page, query);

		query.append(String.format(" limit %s", pageRows));
		query.append(String.format(" offset %s", offset));

		List<Estilo> estiloList = jdbcTemplate.query(query.toString(), filtro,
				(rs, rowNumber) -> new Estilo(rs.getLong("id"), rs.getString("nome")));
		
		return new PageImpl<>(estiloList, page, total());
	}
	
	private void orderBy(Pageable page, StringBuilder query) {
		Sort sort = page.getSort();
		if(!sort.isUnsorted()) {
			Sort.Order order = sort.iterator().next();
			String campo  	 = order.getProperty();
			query.append(String.format(" order by %s ", order.isAscending() ? Order.asc(campo) : Order.desc(campo)));
		}		
	}

	private int total() {
		StringBuilder query = new StringBuilder();
		query.append(" select count(id) from estilo ")
			 .append(" where nome ");
		
		if(!StringUtils.isEmpty(filtro[0])) {
			query.append(" like ? ");
		}else {
			query.append(" != ? ");
		}
		
		return jdbcTemplate.queryForObject(query.toString(), filtro, Integer.class);
	}

}
