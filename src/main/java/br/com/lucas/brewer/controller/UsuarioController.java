package br.com.lucas.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lucas.brewer.model.Usuario;
import br.com.lucas.brewer.service.UsuarioService;
import br.com.lucas.brewer.service.exception.UsuarioExistsException;
import br.com.lucas.brewer.service.exception.UsuarioInvalidoException;

@Controller
public class UsuarioController {

	private UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping("usuarios/cadastro")
	public ModelAndView carregarCadastro(Usuario usuario) {
		ModelAndView mv = new ModelAndView( "usuario/cadastro-usuario");
		return mv;
	}
	
	@PostMapping("usuarios/cadastro")
	public ModelAndView cadastrarUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return carregarCadastro(usuario);
		}

		try {
			long id = this.usuarioService.cadastrarNovo(usuario);
			attributes.addAttribute("id", id);
			return new ModelAndView("redirect:/usuarios/valida");
		} catch (UsuarioExistsException | UsuarioInvalidoException e) {
			
			if(e instanceof UsuarioInvalidoException) {
				result.addError(new ObjectError("usuario", e.getMessage()));
				attributes.addAttribute("id", Integer.valueOf(e.getCause().getMessage()));
				return new ModelAndView("redirect:/usuarios/valida");
			}
			
			result.addError(new ObjectError("usuario", e.getMessage()));
			return carregarCadastro(usuario);
		}
	}
	
	@GetMapping("usuarios/valida")
	public ModelAndView carregarValidacao(Usuario usuario) {
		ModelAndView mv = new ModelAndView( "usuario/valida-usuario");
		return mv;
	}
	
	@PostMapping("usuarios/valida")
	public ModelAndView validarUsuario(Usuario usuario, BindingResult result, RedirectAttributes attributes) {

		try {
			usuarioService.validaUsuario(usuario);
			attributes.addFlashAttribute("mensagem", "Usuário ativado com sucesso! ");
			return new ModelAndView("redirect:/usuarios/valida");
		} catch (UsuarioInvalidoException | IllegalArgumentException e) {
			result.addError(new ObjectError("usuario", e.getMessage()));
			return carregarValidacao(usuario);
		}
		
	}
}
