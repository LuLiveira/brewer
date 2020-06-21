package br.com.lucas.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorsController {
	
	@GetMapping("/404")
	public String pageNotFound() {
		return "404";
	}

}
