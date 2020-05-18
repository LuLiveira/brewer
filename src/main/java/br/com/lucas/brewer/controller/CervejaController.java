package br.com.lucas.brewer.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lucas.brewer.model.Cerveja;
import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.model.enums.Origem;
import br.com.lucas.brewer.model.enums.Sabor;
import br.com.lucas.brewer.repository.EstiloRepository;
import br.com.lucas.brewer.service.CervejaService;

/**
 * 
 * @author Lucas Oliveira
 *
 */

@Controller
@RequestMapping("/cervejas")
public class CervejaController {

	@Autowired
	private EstiloRepository estiloRepository;

	@Autowired
	private CervejaService cervejaService;

	@RequestMapping("/cadastro")
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cerveja/cadastro-cerveja");
		getValuesForSelect(mv);
		return mv;
	}

	@RequestMapping(value = "/cadastro", method = POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return novo(cerveja);
		}

		this.cervejaService.salvar(cerveja);

		attributes.addFlashAttribute("mensagem", "Cerveja cadastrada com sucesso! ");
		return new ModelAndView("redirect:/cervejas/cadastro");
	}

	@RequestMapping(value = "/cadastro/estilo", method = POST, consumes = { APPLICATION_JSON_VALUE })
	public ResponseEntity<?> cadastrarEstilo(@RequestBody @Valid Estilo estilo, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());

		estilo = this.cervejaService.salvar(estilo);

		return ResponseEntity.ok(estilo);
	}

	private void getValuesForSelect(ModelAndView mv) {
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estiloRepository.findAll());
		mv.addObject("origens", Origem.values());
	}
}
