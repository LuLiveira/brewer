package br.com.lucas.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.lucas.brewer.model.factory.ModelAndViewFactory;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	private static final String CADASTRO = "/cadastro";
	private static final String CLIENTE_CADASTRO_VIEW = "cliente/cadastro-cliente";
	
	
	@RequestMapping(CADASTRO)
	public ModelAndView carregarCadastroCliente() {
		return ModelAndViewFactory.instaceOf(CLIENTE_CADASTRO_VIEW);
	}
	
}
