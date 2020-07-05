package br.com.lucas.brewer.service.event.usuario;

import br.com.lucas.brewer.model.Usuario;

public class UsuarioEvent {

	private Usuario usuario;

	public UsuarioEvent(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
