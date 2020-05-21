package br.com.lucas.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class MessageElementTagProcessor extends AbstractElementTagProcessor {

	private static final String TAG_NAME = "message";
	private static final Integer PRECEDENCIA = 1000;

	private static final String TH_BLOCK = "th:block";
	private static final String TH_INCLUDE = "th:include";

	private static final String MENSAGEM_SUCESSO_PATH = "/messages/mensagem-sucesso";
	private static final String MENSAGEM_ERRO_PATH = "/messages/mensagem-erro";

	public MessageElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCIA);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {

		IModelFactory modelFactory = context.getModelFactory();
		IModel model = modelFactory.createModel();

		model.add(modelFactory.createStandaloneElementTag(TH_BLOCK, TH_INCLUDE, MENSAGEM_SUCESSO_PATH));
		model.add(modelFactory.createStandaloneElementTag(TH_BLOCK, TH_INCLUDE, MENSAGEM_ERRO_PATH));

		structureHandler.replaceWith(model, true);
	}

}
