package repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public abstract class AbstractRepository {
	
	private String _dataFolder;
	
	public AbstractRepository(String configFileName, String propertySetting) throws FileNotFoundException, IOException {
		Properties settings = new Properties();
		settings.load(new FileInputStream(configFileName));		
		_dataFolder = settings.getProperty(propertySetting); 
	}
	
	protected String getDataFolder() {
		return _dataFolder;
	}
}
