package repository.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.Person;

import org.junit.Before;
import org.junit.Test;

import repository.PersonRepository;


public class PersonRepositoryTests {

	Person _person;	
	PersonRepository _repo;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException
	{
		String dataFolder = System.getProperty("user.dir") + "\\" + "Data.Tests\\Person";
		File folder = new File(dataFolder);
		if (folder.exists()) {
			for (String file : folder.list())
			{
				File deleteFile = new File(dataFolder + "\\" + file);
				deleteFile.delete();
			}
			folder.delete();
		}
		
		_person = new Person();
		_person.setEMail("michael@jordan.com");
		_person.setFirstName("Michael");
		_person.setLastName("Jordan");
		
		_repo = new PersonRepository("tests.cfg");
	}
	
	@Test
	public void add() throws ClassNotFoundException, IOException {
		Person loadedPerson = _repo.getByEMail(_person.getEMail());
		assertNull(loadedPerson);
		
		_repo.add(_person);
		loadedPerson = _repo.getByEMail(_person.getEMail());
		
		assertEquals(_person, loadedPerson);
		assertEquals(_person.getEMail(), loadedPerson.getEMail());
		assertEquals(_person.getFirstName(), loadedPerson.getFirstName());
		assertEquals(_person.getLastName(), loadedPerson.getLastName());
	}
	
	@Test
	public void remove() throws ClassNotFoundException, IOException {
		add();
		_repo.remove(_person);
		Person loadedPerson = _repo.getByEMail(_person.getEMail());
		assertNull(loadedPerson);
	}
	
	@Test
	public void update() throws ClassNotFoundException, IOException {
		add();
		
		_person.setFirstName("Magic");
		_person.setLastName("Johnson");
		_repo.update(_person);
		
		Person loadedPerson = _repo.getByEMail(_person.getEMail());
		
		assertEquals(_person, loadedPerson);
		assertEquals(_person.getEMail(), loadedPerson.getEMail());
		assertEquals(_person.getFirstName(), loadedPerson.getFirstName());
		assertEquals(_person.getLastName(), loadedPerson.getLastName());
		assertFalse(loadedPerson.getFirstName().equals("Michael"));
		assertFalse(loadedPerson.getLastName().equals("Jordan"));
	}

}