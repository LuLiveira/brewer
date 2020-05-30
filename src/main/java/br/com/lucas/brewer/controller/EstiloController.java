package br.com.lucas.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.service.EstiloService;
import br.com.lucas.brewer.service.exception.NomeEstiloJaCadastradoExcetion;

@Controller
@RequestMapping("/estilos")
public class EstiloController {

	@Autowired
	private EstiloService estiloService;

	@RequestMapping("/cadastro")
	public ModelAndView carregarCadastro(Estilo estilo) {
		ModelAndView mv = new ModelAndView("cerveja/estilo/cadastro-estilo");
		return mv;
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.POST)
	public ModelAndView cadastrarEstilo(@Valid Estilo estilo, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors())
			return carregarCadastro(estilo);

		try {
			this.estiloService.salvar(estilo);
		} catch (NomeEstiloJaCadastradoExcetion e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return carregarCadastro(estilo);
		}

		attributes.addFlashAttribute("mensagem", "Estilo cadastrado com sucesso! ");
		return new ModelAndView("redirect:/estilos/cadastro");
	}
}
