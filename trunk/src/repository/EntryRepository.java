package repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Category;
import model.Entry;
import model.Person;

public class EntryRepository extends AbstractRepository<Entry> {

	private final static String PROPERTY_NAME = "EntryFolder";

	public EntryRepository() throws FileNotFoundException, IOException {
		super(PROPERTY_NAME);
	}

	public EntryRepository(String configFileName) throws FileNotFoundException,
			IOException {
		super(configFileName, PROPERTY_NAME);
	}

	public void add(Entry entry) throws IOException {
		saveObject(entry, getFileName(entry));
	}

	public void update(Entry entry) throws IOException {
		saveObject(entry, getFileName(entry));
	}

	public void remove(Entry entry) throws IOException {
		deleteFile(getFileName(entry));
	}

	public List<Entry> getByPersonEMail(String eMailAddress)
			throws IOException, ClassNotFoundException {
		List<Entry> filteredList = new ArrayList<Entry>();
		for (Entry entry : getAll()) {
			if (entry.getPersonEMail().equals(eMailAddress)) {
				filteredList.add(entry);
			}
		}
		return filteredList;
	}

	public List<Entry> getByCategory(String categoryName)
			throws IOException, ClassNotFoundException {
		List<Entry> filteredList = new ArrayList<Entry>();
		for (Entry entry : getAll()) {
			if (entry.getCategoryName().equals(categoryName)) {
				filteredList.add(entry);
			}
		}
		return filteredList;

	}

	private String getFileName(Entry entry) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMDDHHmmss");
		return entry.getPersonEMail() + "."
				+ format.format(entry.getDateSubmitted());
	}
}