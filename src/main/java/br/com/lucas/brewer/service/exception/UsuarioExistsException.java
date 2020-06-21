package br.com.lucas.brewer.service.exception;

public class UsuarioExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public UsuarioExistsException(String s) {
		super(s);
	}
}
