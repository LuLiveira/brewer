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
		return ModelAndViewFactory.instaceOf("cerveja/websocket");
	}
	
	@GetMapping(value = "/2")
	public ModelAndView webSocket2() {
		return ModelAndViewFactory.instaceOf("cerveja/websocket2");
	}
}
