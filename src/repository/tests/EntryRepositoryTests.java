package repository.tests;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import model.Category;
import model.Entry;
import model.Person;
import org.junit.Before;
import org.junit.Test;

import repository.CategoryRepository;
import repository.EntryRepository;

public class EntryRepositoryTests {
	Entry _entry;
	EntryRepository _entryRepo;
	CategoryRepository _categoryRepo;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
		String dataFolder = System.getProperty("user.dir") + File.separator
				+ "Data.Tests" + File.separator + "Entry";
		File folder = new File(dataFolder);
		if (folder.exists()) {
			for (String file : folder.list()) {
				File deleteFile = new File(dataFolder + File.separator + file);
				deleteFile.delete();
			}
			folder.delete();
		}
		_categoryRepo = new CategoryRepository("tests.cfg");
		Category jacquard = new Category();
		jacquard.setName("Jacquard");
		_categoryRepo.add(jacquard);
		
		_entry = new Entry();
		_entry.setCategory(jacquard);
		_entry.setEmail("john@gmail.com");
		_entry.setDateSubmitted(new Date());
		_entry.setDescription("I love weaving.");
		_entry.setMaterials("material1, material2");
		_entry.setTechniques("technique1");
		_entry.setTitle("Super Cool Weave");
		_entryRepo = new EntryRepository("tests.cfg");
	}

	@Test
	public void add() throws ClassNotFoundException, IOException {
		_entryRepo.add(_entry);
		List<Entry> loadedEntries = _entryRepo.getByPersonEMail(_entry.getEmail());
		Entry loadedEntry = loadedEntries.get(0);
		
		assertEquals(1, loadedEntries.size());
		assertEquals(_entry.getEmail(), loadedEntry.getEmail());
		assertEquals(_entry.getCategory(), loadedEntry.getCategory());
		assertEquals(_entry.getDateSubmitted(), loadedEntry.getDateSubmitted());
		assertEquals(_entry.getDescription(), loadedEntry.getDescription());
		assertEquals(_entry.getMaterials(), loadedEntry.getMaterials());
		assertEquals(_entry.getTechniques(), loadedEntry.getTechniques());
		assertEquals(_entry.getTitle(), loadedEntry.getTitle());
	}

	@Test
	public void remove() throws ClassNotFoundException, IOException {
		add();
		_entryRepo.remove(_entry);
		List<Entry> loadedEntries = _entryRepo.getByPersonEMail(_entry.getEmail());
		assertEquals(0, loadedEntries.size());
	}

	@Test
	public void update() throws ClassNotFoundException, IOException {
		add();
		Category rib = new Category();
		rib.setName("Rib");
		_categoryRepo.add(rib);
		_entry.setCategory(rib);
		_entry.setDescription("I love weaving so much.");
		_entry.setMaterials("material2");
		_entry.setTechniques("technique3");
		_entry.setTitle("Coolest Weave Ever");
		int numOfEntriesBeforeUpdate = _entryRepo.getAll().size();
		_entryRepo.update(_entry);
		int numOfEntriesAfterUpdate = _entryRepo.getAll().size();

		List<Entry> loadedEntries = _entryRepo.getByPersonEMail(_entry.getEmail());
		Entry loadedEntry = loadedEntries.get(0);
		assertEquals(numOfEntriesBeforeUpdate, numOfEntriesAfterUpdate);
		assertEquals(1, loadedEntries.size());
		assertEquals(_entry.getEmail(), loadedEntry.getEmail());
		assertEquals(_entry.getCategory(), loadedEntry.getCategory());
		assertEquals(_entry.getDateSubmitted(), loadedEntry.getDateSubmitted());
		assertEquals(_entry.getDescription(), loadedEntry.getDescription());
		assertEquals(_entry.getMaterials(), loadedEntry.getMaterials());
		assertEquals(_entry.getTechniques(), loadedEntry.getTechniques());
		assertEquals(_entry.getTitle(), loadedEntry.getTitle());
		assertFalse(loadedEntry.getCategory().equals("Jacquard"));
		assertFalse(loadedEntry.getDescription().equals("I love weaving."));
		assertFalse(loadedEntry.getMaterials().equals("material1, material2"));
		assertFalse(loadedEntry.getTechniques().equals("technique1"));
		assertFalse(loadedEntry.getTitle().equals("Super Cool Weave"));
	}

	@Test
	public void nonexistentEmail() throws ClassNotFoundException, IOException {
		add();
		List<Entry> loadedEntries = _entryRepo.getByPersonEMail("johnn@gmail.com");
		assertEquals(0, loadedEntries.size());
	}
	
	@Test
	public void emailWithNoEntries() throws ClassNotFoundException, IOException {
		add();
		Person person = new Person();
		person.setEMail("jane@gmail.com");
		List<Entry> loadedEntries = _entryRepo.getByPersonEMail("jane@gmail.com");
		assertEquals(0, loadedEntries.size());
	}
	
	@Test
	public void getByCategory() throws ClassNotFoundException, IOException {
		add5Entries();
		List<Entry> loadedEntries = _entryRepo.getByCategory("Jacquard");
		assertEquals(1, loadedEntries.size());
		assertEquals(loadedEntries.get(0).getEmail(), "john@gmail.com");

		
		loadedEntries = _entryRepo.getByCategory("Satin");
		assertEquals(4, loadedEntries.size());
		assertEquals(loadedEntries.get(0).getEmail(), "jake@gmail.com");
		assertEquals(loadedEntries.get(1).getEmail(), "jan@gmail.com");
		assertEquals(loadedEntries.get(2).getEmail(), "julie@gmail.com");
		assertEquals(loadedEntries.get(3).getEmail(), "justin@gmail.com");
		
	}
	
	@Test
	public void nonexistentCategory() throws ClassNotFoundException, IOException {
		add();
		List<Entry> loadedEntries = _entryRepo.getByCategory("Basket");
		assertEquals(0, loadedEntries.size());
	}
	
	@Test
	public void categoryWithNoEntries() throws ClassNotFoundException, IOException {
		add();
		Category twill = new Category();
		twill.setName("Twill");
		_categoryRepo.add(twill);
		List<Entry> loadedEntries = _entryRepo.getByCategory("Twill");
		assertEquals(0, loadedEntries.size());
	}
	
	@Test
	public void getAll() throws ClassNotFoundException, IOException {
		add5Entries();
		List<Entry> allEntries = _entryRepo.getAll();
		assertTrue(allEntries.size() == 5);
		assertEquals(allEntries.get(0).getEmail(), "jake@gmail.com");
		assertEquals(allEntries.get(1).getEmail(), "jan@gmail.com");
		assertEquals(allEntries.get(2).getEmail(), "john@gmail.com");
		assertEquals(allEntries.get(3).getEmail(), "julie@gmail.com");
		assertEquals(allEntries.get(4).getEmail(), "justin@gmail.com");
	}

	private void add5Entries() throws ClassNotFoundException,
			IOException {
		add();
		Category satin = new Category();
		satin.setName("Satin");
		_categoryRepo.add(satin);
		
		Entry entry2 = new Entry();
		entry2.setCategory(satin);
		entry2.setEmail("jake@gmail.com");
		entry2.setDateSubmitted(new Date());
		entry2.setDescription("I love weaving more than you.");
		entry2.setMaterials("material1");
		entry2.setTechniques("technique1");
		entry2.setTitle("Cool Weave");
		_entryRepo.add(entry2);
		
		Entry entry3 = new Entry();
		entry3.setCategory(satin);
		entry3.setEmail("julie@gmail.com");
		entry3.setDateSubmitted(new Date());
		entry3.setDescription("I like weaving.");
		entry3.setMaterials("material2, material3");
		entry3.setTechniques("technique1, technique2");
		entry3.setTitle("Coolest Weave");
		_entryRepo.add(entry3);
		
		Entry entry4 = new Entry();
		entry4.setCategory(satin);
		entry4.setEmail("justin@gmail.com");
		entry4.setDateSubmitted(new Date());
		entry4.setDescription("I like to weave.");
		entry4.setMaterials("material3");
		entry4.setTechniques("technique2");
		entry4.setTitle("Weave");
		_entryRepo.add(entry4);
		
		Entry entry5 = new Entry();
		entry5.setCategory(satin);
		entry5.setEmail("jan@gmail.com");
		entry5.setDateSubmitted(new Date());
		entry5.setDescription("Weaving is fun.");
		entry5.setMaterials("material2");
		entry5.setTechniques("technique1");
		entry5.setTitle("Nice Weave");
		_entryRepo.add(entry5);
	}

}

