package br.com.lucas.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CidadeController {

	@RequestMapping("cidades/cadastro")
	public String novo() {
		return "cidade/cadastro-cidade";
	}
}
