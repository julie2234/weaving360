package repository.tests;

import static org.junit.Assert.*;

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
		_person = new Person();
		_repo = new PersonRepository("weaving.tests.cfg");
	}
	
	@Test
	public void add() {
		Person loadedPerson = _repo.getByEMail(_person.getEMail());
		assertNull(loadedPerson);
		
		_repo.add(_person);
		loadedPerson = _repo.getByEMail(_person.getEMail());
		
		assertEquals(_person, loadedPerson);
		assertEquals(_person.getEMail(), loadedPerson.getEMail());
		assertEquals(_person.getFirstName(), loadedPerson.getFirstName());
	}
	
	@Test
	public void remove() {
		add();
		_repo.remove(_person);
		Person loadedPerson = _repo.getByEMail(_person.getEMail());
		assertNull(loadedPerson);
	}
	
	public void update() {
		fail("Not yet implemented");
	}

}