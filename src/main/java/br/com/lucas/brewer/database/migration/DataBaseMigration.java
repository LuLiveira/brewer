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
	
	public static void main(String[] args) {
		
		try {
			properties = carregaDadosDoArquivoDatabaseProperties(DIRETORIO.concat(DataBaseMigration.ARQUIVO_DATABASE_PROPERTIES));
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("\n" + e.getMessage() + "\n NÃO FOI POSSÍVEL REALIZAR A MIGRATION.");
		}
		
		Flyway flyway = Flyway.configure().dataSource(
												properties.getProperty("database.url"), 
												properties.getProperty("database.username"), 
												properties.getProperty("database.password"))
											.load();
		
		flyway.migrate();
	}

	private static Properties carregaDadosDoArquivoDatabaseProperties(String nomeArquivo) throws IOException {
		FileInputStream fis = null;

		fis = new FileInputStream(nomeArquivo);
		properties = new Properties();
		properties.load(fis);
		fis.close();
		
		return properties;
	}

}
