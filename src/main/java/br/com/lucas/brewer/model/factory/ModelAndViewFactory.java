package br.com.lucas.brewer.model.factory;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public abstract  class ModelAndViewFactory {

	public static ModelAndView instaceOf(String modelAndView) {
		return !StringUtils.isEmpty(modelAndView) ? new ModelAndView(modelAndView) : new ModelAndView();
	}
}
