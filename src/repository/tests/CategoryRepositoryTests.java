package repository.tests;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
		
		assertEquals(_category, loadedCategory);
		assertEquals(_category.getName(), loadedCategory.getName());
		assertEquals(_category.getJudges(), loadedCategory.getJudges());
	}
	
	@Test
	public void update() throws ClassNotFoundException, IOException {
		add();

		Person j1a = new Person();
		Person j2a = new Person();
		
		j1a.setRole(Role.Judge);
		j2a.setRole(Role.Judge);
		
		ArrayList<Person> judgelist = new ArrayList<Person>();
		judgelist.add(j1a);
		judgelist.add(j2a);
		
		_category.setName("Spell Weaving");
		_category.setJudges(judgelist);
		_catrepo.update(_category);

		Category loadedCategory = _catrepo.getByName(_category.getName());

		assertEquals(_category, loadedCategory);
		assertEquals(_category.getName(), loadedCategory.getName());
		assertEquals(_category.getJudges(), loadedCategory.getJudges());
		
		assertFalse(loadedCategory.getName().equals("Nerdy Weaving"));
		assertFalse(loadedCategory.getJudges().equals(new ArrayList<Person>()));

	}
	
	@Test
	public void caseInsensitiveUsername() throws ClassNotFoundException, IOException {
		add();
		Category loadedCategory = _catrepo.getByName("hUgO wEaViNg");
		assertNotNull(loadedCategory);
	}

}
