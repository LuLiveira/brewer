package br.com.lucas.brewer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author Lucas Oliveira
 *
 */

@RestController
@RequestMapping("/fotos")
public class FotoController {

	@PostMapping
	public String upload(@RequestParam("files[]") MultipartFile[] files) {
		System.out.println(files.length);
		return "Ok";
	}
}
