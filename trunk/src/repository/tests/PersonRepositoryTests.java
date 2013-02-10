package repository.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.Person;
import model.Role;

import org.junit.Before;
import org.junit.Test;

import repository.PersonRepository;


public class PersonRepositoryTests {

	Person _attendee;	
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
		
		_attendee = new Person();
		_attendee.setEMail("michael@jordan.com");
		_attendee.setFirstName("Michael");
		_attendee.setLastName("Jordan");
		_attendee.setPassword("super secret Password!");
		_attendee.setPhoneNumber("1-205-123-4556");
		_attendee.setRole(Role.Attendee);
		
		_repo = new PersonRepository("tests.cfg");
	}
	
	@Test
	public void add() throws ClassNotFoundException, IOException {
		Person loadedPerson = _repo.getByEMail(_attendee.getEMail());
		assertNull(loadedPerson);
		
		_repo.add(_attendee);
		loadedPerson = _repo.getByEMail(_attendee.getEMail());
		
		assertEquals(_attendee, loadedPerson);
		assertEquals(_attendee.getEMail(), loadedPerson.getEMail());
		assertEquals(_attendee.getFirstName(), loadedPerson.getFirstName());
		assertEquals(_attendee.getLastName(), loadedPerson.getLastName());
		assertEquals(_attendee.getPassword(), loadedPerson.getPassword());
		assertEquals(_attendee.getPhoneNumber(), loadedPerson.getPhoneNumber());
		assertEquals(_attendee.getRole(), loadedPerson.getRole());
	}
	
	@Test
	public void remove() throws ClassNotFoundException, IOException {
		add();
		_repo.remove(_attendee);
		Person loadedPerson = _repo.getByEMail(_attendee.getEMail());
		assertNull(loadedPerson);
	}
	
	@Test
	public void update() throws ClassNotFoundException, IOException {
		add();
		
		_attendee.setFirstName("Magic");
		_attendee.setLastName("Johnson");
		_attendee.setPassword("hi1234");
		_attendee.setPhoneNumber("1-235-235-5325);");
		_attendee.setRole(Role.Judge);
		_repo.update(_attendee);
		
		Person loadedPerson = _repo.getByEMail(_attendee.getEMail());
		
		assertEquals(_attendee, loadedPerson);
		assertEquals(_attendee.getEMail(), loadedPerson.getEMail());
		assertEquals(_attendee.getFirstName(), loadedPerson.getFirstName());
		assertEquals(_attendee.getLastName(), loadedPerson.getLastName());
		assertEquals(_attendee.getPassword(), loadedPerson.getPassword());
		assertEquals(_attendee.getPhoneNumber(), loadedPerson.getPhoneNumber());
		assertEquals(_attendee.getRole(), loadedPerson.getRole());
		
		assertFalse(loadedPerson.getFirstName().equals("Michael"));
		assertFalse(loadedPerson.getLastName().equals("Jordan"));
		assertFalse(loadedPerson.getPassword().equals("super secret Password!"));
		assertFalse(loadedPerson.getPhoneNumber().equals("1-205-123-4556"));
		assertFalse(loadedPerson.getRole().equals(Role.Attendee));
	}
	
	@Test
	public void badPassword() throws ClassNotFoundException, IOException {
		add();
		Person loadedPerson = _repo.getByLogin("michael@jordan.com", "hi1234");
		assertNull(loadedPerson);
	}
	
	@Test
	public void noPassword() throws ClassNotFoundException, IOException {
		add();
		Person loadedPerson = _repo.getByLogin("michael@jordan.com", "");
		assertNull(loadedPerson);
	}
	
	@Test
	public void caseSensitivePassword() throws ClassNotFoundException, IOException {
		add();
		Person loadedPerson = _repo.getByLogin("michael@jordan.com", "super secret password!");
		assertNull(loadedPerson);
	}
	
	@Test
	public void successfulLogin() throws ClassNotFoundException, IOException {
		add();
		Person loadedPerson = _repo.getByLogin("michael@jordan.com", "super secret Password!");
		assertNotNull(loadedPerson);
	}

}