package br.com.lucas.brewer.config.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.com.lucas.brewer.config.JDBCConfig;
import br.com.lucas.brewer.config.MailConfig;
import br.com.lucas.brewer.config.ServiceConfig;
import br.com.lucas.brewer.config.WebConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { JDBCConfig.class, ServiceConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class, MailConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encoding = new CharacterEncodingFilter();
		encoding.setEncoding("UTF-8");
		encoding.setForceEncoding(true);

		return new Filter[] { encoding };
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) { //Configuração para receber fotos no controller (FotoController.class)
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
}
