package br.com.lucas.brewer.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.brewer.dao.UsuarioDAO;
import br.com.lucas.brewer.mail.Mailer;
import br.com.lucas.brewer.model.Usuario;
import br.com.lucas.brewer.service.exception.UsuarioExistsException;
import br.com.lucas.brewer.service.exception.UsuarioInvalidoException;

@Service
public class UsuarioService {
	
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private Mailer mailer;
	
	public UsuarioService(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public long cadastrarNovo(Usuario usuario) throws UsuarioInvalidoException, UsuarioExistsException {
		Optional<Usuario> usuarioExists = usuarioDAO.findByEmail(usuario.getEmail());
		
		if(usuarioExists.isPresent()) {
			if(usuarioExists.get().getAtivo()) {
				throw new UsuarioExistsException("Usuário já cadastrado");
			}else {
				throw new UsuarioInvalidoException("O usuário com este e-mail esta aguardando validação", new Throwable(String.valueOf(usuarioExists.get().getId())));
			}
		}
		
		usuario.setUuid(UUID.randomUUID().toString());
		long id = usuarioDAO.insertAndReturn(usuario);
		
		mailer.enviar(new String[] {String.valueOf(id), usuario.getUuid(), usuario.getEmail()});
		
		return id;
	}

	public void validaUsuario(Usuario usuario) throws UsuarioInvalidoException, IllegalArgumentException {
		Optional<Usuario> usuarioExists = usuarioDAO.findById(usuario.getId());
		if(!usuarioExists.isPresent()) {
			throw new UsuarioInvalidoException("Não existe um usuário pendente de verificação para este ID.");
		}
		
		Usuario usuarioExist = usuarioExists.get();
		
		if (usuarioExist.equals(usuario) && usuario.getUuid().equals(usuarioExist.getUuid())) {
			usuario.setValido(true);
			usuarioDAO.updateToAtivo(usuario);
		}else {
			throw new IllegalArgumentException("Código inválido");
		}
		
		
	}

}
