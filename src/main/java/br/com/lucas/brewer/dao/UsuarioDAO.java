package br.com.lucas.brewer.dao;

import java.util.Optional;

import br.com.lucas.brewer.model.Usuario;

public interface UsuarioDAO {

	void insertUsuario(Usuario usuario);
	
	long insertAndReturn(Usuario usuario);

	Optional<Usuario> findByEmail(String email);

	Optional<Usuario> findById(Long id);

	void updateToAtivo(Usuario usuario);
}
