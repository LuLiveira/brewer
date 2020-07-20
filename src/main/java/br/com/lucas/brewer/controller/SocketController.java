package br.com.lucas.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.lucas.brewer.model.factory.ModelAndViewFactory;

@Controller
@RequestMapping("/websocket")
public class SocketController {

	@GetMapping
	public ModelAndView webSocket() {
		ModelAndView model = ModelAndViewFactory.instaceOf("cerveja/websocket");
		return model;
	}
	
	@GetMapping(value = "/2")
	public ModelAndView webSocket2() {
		ModelAndView model = ModelAndViewFactory.instaceOf("cerveja/websocket2");
		return model;
	}
}
