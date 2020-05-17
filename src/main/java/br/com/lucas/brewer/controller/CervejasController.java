package br.com.lucas.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lucas.brewer.model.Cerveja;
import br.com.lucas.brewer.repository.CervejaRepository;

@Controller
public class CervejasController {
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@RequestMapping("/cervejas/cadastro")
	public String novo(Cerveja cerveja) {
		cervejaRepository.findAll();
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
