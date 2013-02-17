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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.Person;

public abstract class AbstractRepository<T> {

	private final static String DEFAULT_CONFIG_FILENAME = "weaving.cfg";

	private String _dataFolder;

	public AbstractRepository(String configFileName, String propertySetting)
			throws FileNotFoundException, IOException {
		setDataFolder(configFileName, propertySetting);
	}

	public AbstractRepository(String propertySetting)
			throws FileNotFoundException, IOException {
		setDataFolder(DEFAULT_CONFIG_FILENAME, propertySetting);
	}

	public List<T> getAll() throws ClassNotFoundException, IOException {
		List<T> items = new ArrayList<T>();
		for (String fileName : getDataFolder().list()) {
			T obj = loadObject(fileName);
			if (obj != null) {
				items.add(obj);
			}
		}
		return items;
	}

	protected void saveObject(T entity, String fileName) throws IOException {
		File folder = new File(_dataFolder);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		OutputStream file = new FileOutputStream(getDataFile(fileName));
		OutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);
		output.writeObject(entity);
		output.close();
	}

	@SuppressWarnings("unchecked")
	protected T loadObject(String fileName) throws IOException,
			ClassNotFoundException {
		File file = getDataFile(fileName);
		
		T loadedObject = null;
		if (file.exists() && !file.isDirectory()) {
			InputStream inputStream = new FileInputStream(file);
			InputStream buffer = new BufferedInputStream(inputStream);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				loadedObject = (T) input.readObject();
			} catch (Exception ex) {
			}
			input.close();
		}
		return loadedObject;
	}

	protected void deleteFile(String fileName) {
		File file = getDataFile(fileName);
		if (file.exists()) {
			file.delete();
		}
	}

	private final void setDataFolder(String configFileName,
			String propertySetting) throws FileNotFoundException, IOException {
		Properties settings = new Properties();
		settings.load(new FileInputStream(System.getProperty("user.dir")
				+ File.separator + configFileName));
		_dataFolder = System.getProperty("user.dir") + File.separator
				+ settings.getProperty(propertySetting).replace("//",  File.separator)
				.replace("\\", File.separator);
	}

	private File getDataFolder() {
		return new File(_dataFolder);
	}

	private File getDataFile(String fileName) {
		return new File(_dataFolder + File.separator + fileName);
	}

}