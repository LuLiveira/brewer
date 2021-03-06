package br.com.lucas.brewer.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import br.com.lucas.brewer.mail.Mailer;

@Configuration
@ComponentScan(basePackageClasses = Mailer.class)
@PropertySource("classpath:mail.properties")
public class MailConfig {

	private static final String SMTP 		= "mail.smtp";
	private static final String PORT 		= "mail.port";
	private static final String USERNAME 	= "mail.username";
	private static final String PASSWORD 	= "mail.password";

	@Bean
	public JavaMailSender mailSender(Environment environment) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(environment.getProperty(SMTP));
		mailSender.setPort(Integer.valueOf(environment.getProperty(PORT)));
		mailSender.setUsername(environment.getProperty(USERNAME));
		mailSender.setPassword(environment.getProperty(PASSWORD));
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.debug", false);
		props.put("mail.smtp.connectiontimeout", 10000);
		
		mailSender.setJavaMailProperties(props);
		
		return mailSender;
	}

}
