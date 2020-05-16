package br.com.lucas.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lucas.brewer.model.Cerveja;

@Controller
public class CervejasController {

	@RequestMapping("/cervejas/cadastro")
	public String novo(Cerveja cerveja) {
		return "cerveja/cadastro-cerveja";
	}

	@RequestMapping(value = "/cervejas/cadastro", method = RequestMethod.POST)
	public String cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return novo(cerveja);
		}

		attributes.addFlashAttribute("mensagem", "Cerveja cadastrada com sucesso! ");
		return "redirect:/cervejas/cadastro";
	}
}
