package br.com.lucas.brewer.service.exception;

public class UsuarioInvalidoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UsuarioInvalidoException(String s, Throwable id) {
		super(s, id);
	}
	
	public UsuarioInvalidoException(String s) {
		super(s);
	}
}
