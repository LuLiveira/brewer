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
	private static final String URL_VALIDACAO_CADASTRO = "redirect:/usuarios/valida";
	private static final String USUARIOS_PREFIX = "usuarios";
	private static final String USUARIO = "usuario";
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping(USUARIOS_PREFIX+"/cadastro")
	public ModelAndView carregarCadastro(Usuario usuario) {
		return new ModelAndView( USUARIO+"/cadastro-usuario");
	}
	
	@PostMapping(USUARIOS_PREFIX+"/cadastro")
	public ModelAndView cadastrarUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return carregarCadastro(usuario);
		}

		try {
			long id = this.usuarioService.cadastrarNovo(usuario);
			attributes.addAttribute("id", id);
			return new ModelAndView(URL_VALIDACAO_CADASTRO);
		} catch (UsuarioExistsException e) {
			result.addError(new ObjectError(USUARIO, e.getMessage()));
			return carregarCadastro(usuario);
		} catch (UsuarioInvalidoException e) {
			result.addError(new ObjectError(USUARIO, e.getMessage()));
			attributes.addAttribute("id", Integer.valueOf(e.getCause().getMessage()));
			return new ModelAndView(URL_VALIDACAO_CADASTRO);
		}
	}
	
	@GetMapping(USUARIOS_PREFIX+"/valida")
	public ModelAndView carregarValidacao(Usuario usuario) {
		return new ModelAndView( USUARIO+"/valida-usuario");
	}
	
	@PostMapping(USUARIOS_PREFIX+"/valida")
	public ModelAndView validarUsuario(Usuario usuario, BindingResult result, RedirectAttributes attributes) {

		try {
			usuarioService.validaUsuario(usuario);
			attributes.addFlashAttribute("mensagem", "Usuário ativado com sucesso! ");
			return new ModelAndView(URL_VALIDACAO_CADASTRO);
		} catch (UsuarioInvalidoException | IllegalArgumentException e) {
			result.addError(new ObjectError(USUARIO, e.getMessage()));
			return carregarValidacao(usuario);
		}
		
	}
}
