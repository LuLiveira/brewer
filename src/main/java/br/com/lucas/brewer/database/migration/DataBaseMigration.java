package br.com.lucas.brewer.database.migration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Lucas Oliveira
 *
 */
public class DataBaseMigration {
	
	private static final String ARQUIVO_DATABASE_PROPERTIES = "database.properties";
	private static final Logger LOG = LoggerFactory.getLogger(DataBaseMigration.class);
	private static Properties properties;
	private static final String DIRETORIO = System.getProperty("user.dir").concat("\\src\\main\\resources\\");
	private static final String DATABASE_PROPERTIES = DIRETORIO.concat(DataBaseMigration.ARQUIVO_DATABASE_PROPERTIES);
	
	public static void main(String[] args) {
		
		
			properties = carregaDadosDo(DATABASE_PROPERTIES);

		
		
		Flyway flyway = Flyway.configure().dataSource(
												properties.getProperty("database.url"), 
												properties.getProperty("database.username"), 
												properties.getProperty("database.password"))
											.load();
		
		flyway.migrate();
	}

	private static Properties carregaDadosDo(String databaseProperties){
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(databaseProperties);
			properties = new Properties();
			properties.load(fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("\n" + e.getMessage() + "\n NÃO FOI POSSÍVEL REALIZAR A MIGRATION.");
		}
		
		return properties;
	}

}
