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

/**
 * An abstract repository that can load, save, delete, and get all objects to
 * and from the file system.
 * 
 * @author Ralph Feltis
 * @version 20130218
 * @param <T>
 *            The generic type that will be used by this repository.
 */
public abstract class AbstractRepository<T> {

	private final static String DEFAULT_CONFIG_FILENAME = "weaving.cfg";

	private String _dataFolder;

	/**
	 * Constructs an abstract repository.
	 * 
	 * @param configFileName
	 *            The filename of the configuration file.
	 * @param propertySetting
	 *            The key of the key/value pair in the configuration file that
	 *            corresponds to the repository.
	 * @throws FileNotFoundException
	 *             The exception thrown if the configuration file cannot be
	 *             found.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the configuration file.
	 */
	public AbstractRepository(String configFileName, String propertySetting)
			throws FileNotFoundException, IOException {
		setDataFolder(configFileName, propertySetting);
	}

	/**
	 * Constructs an abstract repository using the default configuration file
	 * name of weaving.cfg.
	 * 
	 * @param propertySetting
	 *            The key of the key/value pair in the configuration file that
	 *            corresponds to the repository.
	 * @throws FileNotFoundException
	 *             The exception thrown if the configuration file cannot be
	 *             found.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the configuration file.
	 */
	public AbstractRepository(String propertySetting)
			throws FileNotFoundException, IOException {
		setDataFolder(DEFAULT_CONFIG_FILENAME, propertySetting);
	}

	/**
	 * Get every object found in a folder.
	 * 
	 * @return A list of objects.
	 * @throws ClassNotFoundException
	 *             The exception thrown if the generic class cannot be found.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the object file.
	 */
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

	/**
	 * Saves an object to the file system using serialization.
	 * 
	 * @param entity
	 *            The object to save.
	 * @param fileName
	 *            The filename for the object.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while saving
	 *             the object file.
	 */
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

	/**
	 * Loads an object from the file system based on serialization.
	 * 
	 * @param fileName
	 *            The filename of the object.
	 * @return The instantiated object.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the object file.
	 * @throws ClassNotFoundException
	 *             The exception thrown if the generic class cannot be found.
	 */
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

	/**
	 * Deletes a file in the file system.
	 * 
	 * @param fileName
	 *            The filename of the file to delete.
	 */
	protected void deleteFile(String fileName) {
		File file = getDataFile(fileName);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * Sets the data folder for this repository.
	 * 
	 * @param configFileName
	 *            The filename of the configuration file.
	 * @param propertySetting
	 *            The key of the key/value pair in the configuration file that
	 *            corresponds to the repository.
	 * @throws FileNotFoundException
	 *             The exception thrown if the configuration file cannot be
	 *             found.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the configuration file.
	 */
	private final void setDataFolder(String configFileName,
			String propertySetting) throws FileNotFoundException, IOException {
		Properties settings = new Properties();
		settings.load(new FileInputStream(System.getProperty("user.dir")
				+ File.separator + configFileName));
		_dataFolder = System.getProperty("user.dir")
				+ File.separator
				+ settings.getProperty(propertySetting)
						.replace("//", File.separator)
						.replace("\\", File.separator);
	}

	/**
	 * Get a File object for this repository's data folder.
	 * 
	 * @return The File object for this repository's data folder.
	 */
	private File getDataFolder() {
		return new File(_dataFolder);
	}

	/**
	 * Get a File object for a specified file name.
	 * 
	 * @param fileName
	 *            The filename to load.
	 * @return The File object for the requested filename.
	 */
	private File getDataFile(String fileName) {
		return new File(_dataFolder + File.separator + fileName.toLowerCase());
	}

}