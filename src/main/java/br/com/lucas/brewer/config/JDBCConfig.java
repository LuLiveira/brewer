package br.com.lucas.brewer.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 
 * @author Lucas
 *
 */

@Configuration
@ComponentScan("br.com.lucas.brewer")
@PropertySource("classpath:database.properties")
public class JDBCConfig {

	@Autowired
	Environment environment;

	private final String URL = "database.url";
	private final String USER = "database.username";
	private final String PASSWORD = "database.password";
	private final String DRIVER = "database.driver";

	@Bean
	DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(environment.getProperty(URL));
		dataSource.setUsername(environment.getProperty(USER));
		dataSource.setPassword(environment.getProperty(PASSWORD));
		dataSource.setDriverClassName(environment.getProperty(DRIVER));

		return dataSource;
	}
}
