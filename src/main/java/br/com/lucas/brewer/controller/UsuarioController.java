package br.com.lucas.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsuarioController {

	@RequestMapping("usuarios/cadastro")
	public String carregarCadastroUsuario() {
		return "usuario/cadastro-usuario";
	}
}
