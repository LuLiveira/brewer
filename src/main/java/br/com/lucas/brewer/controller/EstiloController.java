package br.com.lucas.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EstiloController {

	@RequestMapping("estilos/cadastro")
	public String novo() {
		return "cerveja/estilo/cadastro-estilo";
	}
}
