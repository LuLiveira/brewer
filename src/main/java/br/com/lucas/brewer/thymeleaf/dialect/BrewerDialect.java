package br.com.lucas.brewer.thymeleaf.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.com.lucas.brewer.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.com.lucas.brewer.thymeleaf.processor.MessageElementTagProcessor;

public class BrewerDialect extends AbstractProcessorDialect {

	private static final String NOME_DIALECT = "Lucas Brewer";
	private static final String BREWER_PREFIX = "brewer";

	public BrewerDialect() {
		super(NOME_DIALECT, BREWER_PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processors = new HashSet<IProcessor>();
		processors.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processors.add(new MessageElementTagProcessor(dialectPrefix));
		return processors;
	}

}
