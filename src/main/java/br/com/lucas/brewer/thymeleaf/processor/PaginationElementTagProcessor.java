package br.com.lucas.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class PaginationElementTagProcessor extends AbstractElementTagProcessor {

	private static final String TAG_NAME = "paginacao";
	private static final Integer PRECEDENCIA = 1000;

	private static final String TH_BLOCK = "th:block";
	private static final String TH_REPLACE = "th:replace";

	private static final String TAG = "layout/fragments/paginacao :: paginacao (%s)";

	public PaginationElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCIA);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {

		IModelFactory modelFactory = context.getModelFactory();
		
		IAttribute page  = tag.getAttribute("page");
		
		IModel model = modelFactory.createModel();

		model.add(modelFactory.createStandaloneElementTag(TH_BLOCK, TH_REPLACE, String.format(TAG, page.getValue())));

		structureHandler.replaceWith(model, true);
	}
}
