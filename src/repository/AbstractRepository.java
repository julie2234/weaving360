package repository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import model.Person;

public abstract class AbstractRepository {

	private String _dataFolder;

	public AbstractRepository(String configFileName, String propertySetting)
			throws FileNotFoundException, IOException {

		Properties settings = new Properties();
		settings.load(new FileInputStream(System.getProperty("user.dir") + "\\"
				+ configFileName));
		_dataFolder = System.getProperty("user.dir") + "\\"
				+ settings.getProperty(propertySetting);
	}

	protected String getDataFolder() {
		return _dataFolder;
	}

	protected void saveObject(Person person, String fileName)
			throws IOException {
		File folder = new File(_dataFolder);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		OutputStream file = new FileOutputStream(getFullPath(fileName));
		OutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);
		output.writeObject(person);
		output.close();
	}

	public void deleteFile(String fileName) {
		File file = new File(getFullPath(fileName));
		if (file.exists()) {
			file.delete();
		}
	}

	private String getFullPath(String fileName) {
		return getDataFolder() + "\\" + fileName;
	}
}
