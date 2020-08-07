package br.com.lucas.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lucas.brewer.controller.page.PageWrapper;
import br.com.lucas.brewer.dao.EstiloDAO;
import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.model.factory.ModelAndViewFactory;
import br.com.lucas.brewer.repository.filter.EstiloFilter;
import br.com.lucas.brewer.service.EstiloService;
import br.com.lucas.brewer.service.exception.NomeEstiloJaCadastradoExcetion;

@Controller
@RequestMapping("/estilos")
public class EstiloController {

	private EstiloService estiloService;
	private EstiloDAO estiloDAO;

	public EstiloController(EstiloService estiloService, EstiloDAO estiloDAO) {
		this.estiloService = estiloService;
		this.estiloDAO = estiloDAO;
	}

	@RequestMapping("/cadastro")
	public ModelAndView carregarCadastro(Estilo estilo) {
		return new ModelAndView("cerveja/estilo/cadastro-estilo");
	}

	@PostMapping(value = "/cadastro")
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
	
	@GetMapping
	public ModelAndView pesquisar(EstiloFilter estiloFilter, BindingResult result, @PageableDefault(size = 2) Pageable page, HttpServletRequest request) {
	
		ModelAndView mv = ModelAndViewFactory.instaceOf("cerveja/estilo/pesquisa-estilo");
		mv.addObject("estilos", new PageWrapper<>(estiloDAO.selectByFilter(estiloFilter, page), request));
		return mv;
	}
	
}
