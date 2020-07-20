package br.com.lucas.brewer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import br.com.lucas.brewer.dao.EstiloDAO;
import br.com.lucas.brewer.model.Estilo;

@Component
public class EstiloDAOImpl implements EstiloDAO {

	JdbcTemplate jdbcTemplate;

	public EstiloDAOImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Estilo insertAndReturn(Estilo estilo) {
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		StringBuilder query = new StringBuilder();
		query.append(" INSERT INTO estilo ")
			 .append(" ( nome ) values ( ? ) ");

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, estilo.getNome());
				return statement;
			}
		}, holder);
		
		long primaryKey = holder.getKey().longValue();
		
		return selectEstiloById(primaryKey);
	}

	@Override
	public void insert(Estilo estilo) {
		StringBuilder query = new StringBuilder();
		query.append(" INSERT INTO estilo ")
		     .append(" ( nome ) values ( ? ) ");

		jdbcTemplate.update(query.toString(), estilo.getNome());
	}

	@Override
	public Estilo selectEstiloById(Long id) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM estilo WHERE id = ? ");
		
		return jdbcTemplate.queryForObject(
													query.toString(), 
													new Object[]{id}, 
													(rs, rowNumber) -> new Estilo(rs.getLong("id"), rs.getString("nome"))
												);
	}

	@Override
	public Optional<Estilo> selectEstiloByNameIgnoreCase(String nome) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM estilo WHERE upper(nome) = ? ");
		
		Estilo estilo = jdbcTemplate.queryForObject(
													query.toString(), 
													new Object[]{nome}, 
													(rs, rowNumber) -> new Estilo(rs.getLong("id"), rs.getString("nome"))
												);
		
		return Optional.ofNullable(estilo);
	}

	@Override
	public List<Estilo> selectAll() {
		StringBuffer query = new StringBuffer();
		query.append(" SELECT * FROM estilo ");
		
		return jdbcTemplate.query(
				query.toString(), 
				(rs, rowNumber) -> new Estilo(rs.getLong("id"), rs.getString("nome"))
			);
	}

}
