package repository.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import repository.CategoryRepository;
import model.Category;
import model.Person;
import model.Role;

public class CategoryRepositoryTests {
	
	Category _category;
	CategoryRepository _catrepo;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
		String dataFolder = System.getProperty("user.dir") + File.separator
				+ "Data.Tests" + File.separator + "Category";
		File folder = new File(dataFolder);
		
		if (folder.exists()) {
			for (String file : folder.list()) {
				File deleteFile = new File(dataFolder + File.separator + file);
				deleteFile.delete();
			}
			folder.delete();
		}
		
		Person j1 = new Person();
		Person j2 = new Person();
		
		j1.setEMail("judge@one.com");
		j1.setFirstName("Uno");
		j1.setLastName("One");
		j1.setPassword("i'm judge one");
		j1.setPhoneNumber("1");
		j1.setRole(Role.Judge);
		
		j2.setEMail("judge@two.com");
		j2.setFirstName("Dos");
		j2.setLastName("Two");
		j2.setPassword("i'm judge two");
		j2.setPhoneNumber("2");
		j2.setRole(Role.Judge);

		ArrayList<Person> judgelist = new ArrayList<Person>();
		judgelist.add(j1);
		judgelist.add(j2);
		
		_category = new Category();
		_category.setName("Hugo Weaving");
		_category.setJudges(judgelist);

		_catrepo = new CategoryRepository("tests.cfg");	
		
	}
	
	@Test
	public void add() throws ClassNotFoundException, IOException {
		Category loadedCategory = _catrepo.getByName(_category.getName());
		assertNull(loadedCategory);
		
		_catrepo.add(_category);

		loadedCategory = _catrepo.getByName(_category.getName());
		//loadedCategory = _category;
		
		assertEquals(_category, loadedCategory);
		assertEquals(_category.getName(), loadedCategory.getName());
		assertEquals(_category.getJudges().size(), loadedCategory.getJudges().size());
	}
	
	@Test
	public void update() throws ClassNotFoundException, IOException {
		add();
		
		_category.getJudges().remove(0);				
		_category.setName("Spell Weaving");
		_catrepo.update(_category);
		
		Category loadedCategory = _catrepo.getByName(_category.getName());

		assertEquals(_category, loadedCategory);
		assertEquals(_category.getName(), loadedCategory.getName());
		assertEquals(1, _category.getJudges().size());
		
		assertFalse(loadedCategory.getName().equals("Nerdy Weaving"));
		
	}
	
	@Test
	public void remove() throws ClassNotFoundException, IOException {
		add();
		
		_catrepo.remove(_category);
		
		assertNull(_catrepo.getByName(_category.getName()));
		
	}
	
	@Test
	public void caseInsensitiveUsername() throws ClassNotFoundException, IOException {
		add();
		Category loadedCategory = _catrepo.getByName("hUgO wEaViNg");
		assertNotNull(loadedCategory);
	}
	
	@Test
	public void getAll() throws ClassNotFoundException, IOException {
		addFourCats();

		List<Category> allCats = _catrepo.getAll();
		assertTrue(allCats.size() == 4);
		assertFalse(allCats.get(0).equals(allCats.get(1)));
		assertFalse(allCats.get(1).equals(allCats.get(2)));
		assertFalse(allCats.get(2).equals(allCats.get(3)));
	}
	
	private void addFourCats() throws ClassNotFoundException, IOException{
		
		Category cat1 = new Category();
		cat1.setName("Category 1");
		_catrepo.add(cat1);
		
		Category cat2 = new Category();
		cat2.setName("Category 2");
		_catrepo.add(cat2);
		
		Category cat3 = new Category();
		cat3.setName("Category 3");
		_catrepo.add(cat3);
		
		Category cat4 = new Category();
		cat4.setName("Categor 4");
		_catrepo.add(cat4);
		
	}
}
