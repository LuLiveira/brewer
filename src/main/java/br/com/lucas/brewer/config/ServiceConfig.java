package br.com.lucas.brewer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.lucas.brewer.service.CervejaService;

@Configuration
@ComponentScan(basePackageClasses = CervejaService.class)
public class ServiceConfig {

	
}
