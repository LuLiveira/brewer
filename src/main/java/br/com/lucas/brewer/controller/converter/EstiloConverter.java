package br.com.lucas.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;

import br.com.lucas.brewer.model.Estilo;

/**
 * 
 * @author Lucas Oliveira
 *
 */

@javax.persistence.Converter
public class EstiloConverter implements Converter<String, Estilo> {

	@Override
	public Estilo convert(String id) {
		Estilo estilo = new Estilo();
		estilo.setId(Long.valueOf(id));
		return estilo;
	}
}
