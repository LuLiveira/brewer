package br.com.lucas.brewer.service.exception;

public class CervejaDuplicadaException extends Exception {
	private static final long serialVersionUID = 1L;

	public CervejaDuplicadaException(String s) {
		super(s);
	}
}
