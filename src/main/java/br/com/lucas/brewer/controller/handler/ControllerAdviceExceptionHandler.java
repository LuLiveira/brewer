package br.com.lucas.brewer.controller.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.lucas.brewer.service.exception.NomeEstiloJaCadastradoExcetion;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

	@ExceptionHandler(NomeEstiloJaCadastradoExcetion.class)
	public ResponseEntity<String> handleNomeEstiloJaCadastradoExcetion(NomeEstiloJaCadastradoExcetion e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
