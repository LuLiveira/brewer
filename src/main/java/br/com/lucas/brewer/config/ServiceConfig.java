package br.com.lucas.brewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.lucas.brewer.service.CervejaService;
import br.com.lucas.brewer.storage.FotoStorageLocal;
import br.com.lucas.brewer.storage.local.FotoStorageLocalImpl;

@Configuration
@ComponentScan(basePackageClasses = CervejaService.class)
public class ServiceConfig {
	
	@Bean
	public FotoStorageLocal fotoStorage() {
		return new FotoStorageLocalImpl();
	}
}
