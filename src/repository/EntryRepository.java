package repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Entry;

public class EntryRepository extends AbstractRepository<Entry> {

	private final static String PROPERTY_NAME = "EntryFolder";

	/**
	 * Constructs an entry repository with the default configuration file name
	 * of weaving.cfg for adding, removing, updating, and retrieving entry
	 * objects.
	 * 
	 * @throws FileNotFoundException
	 *             The exception thrown if the default configuration file is not
	 *             found.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the configuration file.
	 */
	public EntryRepository() throws FileNotFoundException, IOException {
		super(PROPERTY_NAME);
	}

	/**
	 * Constructs an entry repository for adding, removing, updating, and
	 * retrieving entry objects.
	 * 
	 * @throws FileNotFoundException
	 *             The exception thrown if the default configuration file is not
	 *             found.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the configuration file.
	 */
	public EntryRepository(String configFileName) throws FileNotFoundException,
			IOException {
		super(configFileName, PROPERTY_NAME);
	}

	/**
	 * Adds an entry to the repository.
	 * 
	 * @param entry
	 *            The Entry object to add to the repository.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while saving
	 *             the object.
	 */
	public void add(Entry entry) throws IOException {
		saveObject(entry, entry.getID());
	}

	/**
	 * Updates an entry to the repository.
	 * 
	 * @param entry
	 *            The entry object to update to the repository.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while
	 *             updating the object.
	 */
	public void update(Entry entry) throws IOException {
		saveObject(entry, entry.getID());
	}

	/**
	 * Removes an entry from the repository.
	 * 
	 * @param entry
	 *            The entry object to remove from the repository.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while
	 *             removing the object.
	 */
	public void remove(Entry entry) throws IOException {
		deleteFile(entry.getID());
	}

	/**
	 * Gets all entries specified by a person's e-mail.
	 * 
	 * @param eMailAddress
	 *            The person's e-mail address.
	 * @return A list of Entries for the specified person.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the objects.
	 * @throws ClassNotFoundException
	 *             The exception thrown if the Entry class cannot be found.
	 */
	public List<Entry> getByPersonEMail(String eMailAddress)
			throws IOException, ClassNotFoundException {
		List<Entry> filteredList = new ArrayList<Entry>();
		for (Entry entry : getAll()) {
			if (entry.getEmail().equals(eMailAddress)) {
				filteredList.add(entry);
			}
		}
		return filteredList;
	}

	/**
	 * Gets all entries specified by a category name.
	 * 
	 * @param categoryName
	 *            The category name.
	 * @return A list of Entries for the specified category.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the objects.
	 * @throws ClassNotFoundException
	 *             The exception thrown if the Entry class cannot be found.
	 */
	public List<Entry> getByCategory(String categoryName) throws IOException,
			ClassNotFoundException {
		List<Entry> filteredList = new ArrayList<Entry>();
		for (Entry entry : getAll()) {
			if (entry.getCategoryName().equals(categoryName)) {
				filteredList.add(entry);
			}
		}
		return filteredList;
	}
}