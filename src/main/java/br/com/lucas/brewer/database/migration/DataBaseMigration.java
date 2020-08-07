package br.com.lucas.brewer.database.migration;

import java.io.FileInputStream;
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
		
		flyway.repair();
		flyway.migrate();
	}

	private static Properties carregaDadosDo(String databaseProperties){

		try(FileInputStream fis = new FileInputStream(databaseProperties)) {
			properties = new Properties();
			properties.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(String.format(" %s NÃO FOI POSSÍVEL REALIZAR A MIGRATION.", e.getMessage()));
		}
		
		return properties;
	}

}
