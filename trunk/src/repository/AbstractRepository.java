package repository;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import model.Person;

public abstract class AbstractRepository<T> {

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

	@SuppressWarnings("unchecked")
	protected T getObject(String fileName) throws IOException,
			ClassNotFoundException {
		String filePath = getDataFolder() + "\\" + fileName;
		File file = new File(filePath);

		T loadedObject = null;
		if (file.exists()) {
			InputStream inputStream = new FileInputStream(filePath);
			InputStream buffer = new BufferedInputStream(inputStream);
			ObjectInput input = new ObjectInputStream(buffer);
			loadedObject = (T) input.readObject();
			input.close();
		}
		return loadedObject;
	}

	protected void deleteFile(String fileName) {
		File file = new File(getFullPath(fileName));
		if (file.exists()) {
			file.delete();
		}
	}

	private String getFullPath(String fileName) {
		return getDataFolder() + "\\" + fileName;
	}
}
