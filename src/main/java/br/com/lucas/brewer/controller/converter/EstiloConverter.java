package br.com.lucas.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

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
		if(!StringUtils.isEmpty(id)) {
			Estilo estilo = new Estilo();
			estilo.setId(Long.valueOf(id));
			return estilo;
		}
		return null;
	}
}