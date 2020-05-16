package br.com.lucas.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClienteController {

	@RequestMapping("/clientes/cadastro")
	public String novo() {
		return "cliente/cadastro-cliente";
	}
	
}
