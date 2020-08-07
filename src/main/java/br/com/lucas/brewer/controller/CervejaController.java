package br.com.lucas.brewer.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.lucas.brewer.controller.page.PageWrapper;
import br.com.lucas.brewer.dao.CervejaDAO;
import br.com.lucas.brewer.dao.EstiloDAO;
import br.com.lucas.brewer.model.Cerveja;
import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.model.dto.CervejaDTO;
import br.com.lucas.brewer.model.enums.Origem;
import br.com.lucas.brewer.model.enums.Sabor;
import br.com.lucas.brewer.model.factory.ModelAndViewFactory;
import br.com.lucas.brewer.repository.filter.CervejaFilter;
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

	private static final String CADASTRO_ENDPOINT = "/cadastro";
	private static final String CADASTRO_ESTILO_ENDPOINT = CADASTRO_ENDPOINT + "/estilo";

	public CervejaController(CervejaService cervejaService, EstiloDAO estiloDAO, CervejaDAO cervejaDAO) {
		this.cervejaService = cervejaService;
		this.estiloDAO = estiloDAO;
		this.cervejaDAO = cervejaDAO;
	}

	@GetMapping(CADASTRO_ENDPOINT)
	public ModelAndView carregarCadastro(CervejaDTO cervejaDTO) {
		ModelAndView mv = ModelAndViewFactory.instaceOf("cerveja/cadastro-cerveja");
		recuperaValoresParaOSelect(mv);
		return mv;
	}

	@PostMapping(CADASTRO_ENDPOINT)
	public ModelAndView cadastrarCerveja(@Valid CervejaDTO cervejaDTO, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return carregarCadastro(cervejaDTO);
		}
		
		Cerveja cerveja = cervejaDTO.fromDTOToEntity();
		
		try {
			this.cervejaService.cadastrarNova(cerveja);
			attributes.addFlashAttribute("mensagem", "Cerveja cadastrada com sucesso! ");
			return ModelAndViewFactory.instaceOf("redirect:/cervejas/cadastro");
		} catch (CervejaDuplicadaException e) {
			result.addError(new ObjectError("cerveja", e.getMessage()));
			return carregarCadastro(cervejaDTO);
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
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, 
			BindingResult result,
			@PageableDefault(size = 2) Pageable page,  
			HttpServletRequest request
		) {
		
		ModelAndView mv = ModelAndViewFactory.instaceOf("cerveja/pesquisa-cerveja");
		recuperaValoresParaOSelect(mv);
		mv.addObject("cervejas", new PageWrapper<>(cervejaDAO.selectByFilter(cervejaFilter, page), request));
		return mv;
	}

	@DeleteMapping(value = "/cadastro/foto/remover", consumes = { APPLICATION_JSON_VALUE })
	public ResponseEntity<?> removerFotoCerveja(@RequestBody String nomeFotoJson) {
		cervejaService.removerImagemTemporariaDaCerveja(nomeFotoJson);
		return ResponseEntity.ok().build();
	}

	private void recuperaValoresParaOSelect(ModelAndView mv) {
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estiloDAO.selectAll());
		mv.addObject("origens", Origem.values());
	}
}
