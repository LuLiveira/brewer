package br.com.lucas.brewer.service.exception;

public class NomeEstiloJaCadastradoExcetion extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NomeEstiloJaCadastradoExcetion(String s) {
		super(s);
	}
}
