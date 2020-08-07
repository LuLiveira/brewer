package br.com.lucas.brewer.service.event.usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.lucas.brewer.mail.Mailer;

@Component
public class UsuarioListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioListener.class);

	private Mailer mailer;

	public UsuarioListener(Mailer mailer) {
		this.mailer = mailer;
	}

	@EventListener
	public void usuarioCriado(UsuarioEvent event) {
		LOGGER.info("Enviando email para >>> " + event.getUsuario().getEmail());
		mailer.enviar(String.valueOf(event.getUsuario().getId()), event.getUsuario().getUuid(),
				event.getUsuario().getEmail());
	}
}
