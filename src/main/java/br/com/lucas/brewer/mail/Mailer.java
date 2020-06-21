package br.com.lucas.brewer.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class Mailer {

	private JavaMailSender sender;
	
	public Mailer(JavaMailSender sender) {
		this.sender = sender;
	}
	
	@Async
	public void enviar(String... args) {
		StringBuilder body = new StringBuilder();
		body.append("O seu código de verificação é: ")
				.append(args[1])
				.append("\n Valide acessando: ")
				.append("http://localhost:8080/brewer/usuarios/valida?id=" + args[0]);
		
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("brewer@brewer.com.br");
		message.setTo(args[2]);
		message.setSubject("Código de verificação");
		message.setText(body.toString());
		
		sender.send(message);
	}
}
