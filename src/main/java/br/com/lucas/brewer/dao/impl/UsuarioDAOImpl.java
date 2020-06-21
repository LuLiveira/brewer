package br.com.lucas.brewer.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import br.com.lucas.brewer.dao.UsuarioDAO;
import br.com.lucas.brewer.model.Usuario;

@Component
public class UsuarioDAOImpl implements UsuarioDAO {

	JdbcTemplate jdbcTemplate;

	public UsuarioDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void insertUsuario(Usuario usuario) {
		StringBuilder query = new StringBuilder();
		query.append(" INSERT INTO usuario ").append(" (uuid, nome, email, senha ) ").append(" values ( ?, ?, ?, ? ) ");

		jdbcTemplate.update(query.toString(), // query
				usuario.getUuid().toString(), usuario.getNome(), usuario.getEmail(), usuario.getSenha());
	}

	@Override
	public long insertAndReturn(Usuario usuario) {
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		StringBuilder query = new StringBuilder();

		query.append(" INSERT INTO usuario ").append(" (uuid, nome, email, senha, ativo ) ")
				.append(" values ( ?, ?, ?, ?, ? ) ");

		jdbcTemplate.update((con) -> {
			PreparedStatement statement = con.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, usuario.getUuid().toString());
			statement.setString(2, usuario.getNome());
			statement.setString(3, usuario.getEmail());
			statement.setString(4, usuario.getSenha());
			statement.setBoolean(5, usuario.getAtivo());
			return statement;
		}, holder);

		long primaryKey = holder.getKey().longValue();

		return primaryKey;
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM usuario WHERE upper(email) = ? ");
		Usuario usuario = null;
		try {
			usuario = jdbcTemplate.queryForObject(query.toString(), new Object[] { email },
					(rs, rowNumber) -> new Usuario(
							rs.getLong("id"), 
							rs.getBoolean("valido"),
							rs.getString("nome"),
							rs.getString("email"),
							rs.getBoolean("ativo")));
		} catch (EmptyResultDataAccessException e) {
			return Optional.ofNullable(usuario);
		}
		return Optional.ofNullable(usuario);
	}

	@Override
	public Optional<Usuario> findById(Long id) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM usuario WHERE id = ? AND valido = false ");
		Usuario usuario = null;
		try {
			usuario = jdbcTemplate.queryForObject(query.toString(), new Object[] { id },
					(rs, rowNumber) -> new Usuario(rs.getLong("id"), rs.getBoolean("valido"), rs.getString("uuid")));
		} catch (EmptyResultDataAccessException e) {
			return Optional.ofNullable(usuario);
		}
		return Optional.ofNullable(usuario);
	}

	@Override
	public void updateToAtivo(Usuario usuario) {
		StringBuilder query = new StringBuilder();

		query.append(" UPDATE usuario SET valido = ? WHERE id = ? ");

		jdbcTemplate.update(query.toString(), usuario.getValido(), usuario.getId());
	}

}
