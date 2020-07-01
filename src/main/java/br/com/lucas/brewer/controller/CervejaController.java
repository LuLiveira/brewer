package br.com.lucas.brewer.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lucas.brewer.dao.CervejaDAO;
import br.com.lucas.brewer.dao.EstiloDAO;
import br.com.lucas.brewer.model.Cerveja;
import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.model.enums.Origem;
import br.com.lucas.brewer.model.enums.Sabor;
import br.com.lucas.brewer.model.factory.ModelAndViewFactory;
import br.com.lucas.brewer.repository.EstiloRepository;
import br.com.lucas.brewer.service.CervejaService;
import br.com.lucas.brewer.service.exception.CervejaDuplicadaException;

/**
 * 
 * @author Lucas Oliveira
 *
 */

@Controller
@RequestMapping("/cervejas")
public class CervejaController {

	private final EstiloDAO estiloDAO;
	private final CervejaDAO cervejaDAO;
	
	private final CervejaService cervejaService;
	
	private final String CADASTRO_ENDPOINT 			= "/cadastro";
	private final String CADASTRO_ESTILO_ENDPOINT 	= CADASTRO_ENDPOINT + "/estilo";
	
	public CervejaController(CervejaService cervejaService, EstiloDAO estiloDAO, CervejaDAO cervejaDAO) {
		this.cervejaService = cervejaService;
		this.estiloDAO = estiloDAO;
		this.cervejaDAO = cervejaDAO;
	}

	@GetMapping(CADASTRO_ENDPOINT)
	public ModelAndView carregarCadastro(Cerveja cerveja) {
		ModelAndView mv = ModelAndViewFactory.instaceOf("cerveja/cadastro-cerveja");
		recuperaValoresParaOSelect(mv);
		return mv;
	}

	@PostMapping(CADASTRO_ENDPOINT)
	public ModelAndView cadastrarCerveja(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return carregarCadastro(cerveja);
		}

		try {
			this.cervejaService.cadastrarNova(cerveja);
			attributes.addFlashAttribute("mensagem", "Cerveja cadastrada com sucesso! ");
			return ModelAndViewFactory.instaceOf("redirect:/cervejas/cadastro");
		} catch (CervejaDuplicadaException e) {
			result.addError(new ObjectError("cerveja", e.getMessage()));
			return carregarCadastro(cerveja);
		}

	}

	@PostMapping(value = CADASTRO_ESTILO_ENDPOINT, consumes = { APPLICATION_JSON_VALUE })
	public ResponseEntity<?> cadastrarEstilo(@RequestBody @Valid Estilo estilo, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());

		estilo = this.cervejaService.cadastrarNovo(estilo);

		return ResponseEntity.ok(estilo);
	}
	
	@GetMapping
	public ModelAndView pesquisar() {
		ModelAndView mv = ModelAndViewFactory.instaceOf("cerveja/pesquisa-cerveja");
		buscarCervejaParaPesquisa(mv);
		return mv;
	}

	private void recuperaValoresParaOSelect(ModelAndView mv) {
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estiloDAO.selectAll());
		mv.addObject("origens", Origem.values());
	}
	
	private void buscarCervejaParaPesquisa(ModelAndView mv) {
		recuperaValoresParaOSelect(mv);
		mv.addObject("cervejas", cervejaDAO.selectAll());
		
	}
}
