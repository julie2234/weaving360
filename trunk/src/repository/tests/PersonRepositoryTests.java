package repository.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import model.Person;
import model.Role;

import org.junit.Before;
import org.junit.Test;

import repository.PersonRepository;

public class PersonRepositoryTests {

	Person _attendee;
	PersonRepository _repo;

	@Before
	public void setUp() throws FileNotFoundException, IOException {
		String dataFolder = System.getProperty("user.dir") + File.separator
				+ "Data.Tests" + File.separator + "Person";
		File folder = new File(dataFolder);
		if (folder.exists()) {
			for (String file : folder.list()) {
				File deleteFile = new File(dataFolder + File.separator + file);
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
	public void caseInsensitiveUsername() throws ClassNotFoundException, IOException {
		add();
		Person loadedPerson = _repo.getByLogin("MICHAEL@jordan.com", "super secret Password!");
		assertNotNull(loadedPerson);
	}
	
	@Test
	public void badUserGoodPassword() throws ClassNotFoundException, IOException {
		add();
		Person loadedPerson = _repo.getByLogin("steve@jordan.com", "super secret Password!");
		assertNull(loadedPerson);
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
	public void caseSensitivePassword() throws ClassNotFoundException,
			IOException {
		add();
		Person loadedPerson = _repo.getByLogin("michael@jordan.com",
				"super secret password!");
		assertNull(loadedPerson);
	}

	@Test
	public void successfulLogin() throws ClassNotFoundException, IOException {
		add();
		Person loadedPerson = _repo.getByLogin("michael@jordan.com",
				"super secret Password!");
		assertNotNull(loadedPerson);
	}

	@Test
	public void getAll() throws ClassNotFoundException, IOException {
		add4DifferentRoles();

		List<Person> allPeople = _repo.getAll();
		assertTrue(allPeople.size() == 4);
		assertFalse(allPeople.get(0).equals(allPeople.get(1)));
		assertFalse(allPeople.get(1).equals(allPeople.get(2)));
		assertFalse(allPeople.get(2).equals(allPeople.get(3)));
	}

	@Test
	public void getByRole() throws ClassNotFoundException, IOException {
		add4DifferentRoles();

		List<Person> organizers = _repo.getByRole(Role.Organizer);
		assertTrue(organizers.size() == 1);
		assertTrue(organizers.get(0).getEMail().equals("larry@bird.com"));

		List<Person> entrants = _repo.getByRole(Role.Entrant);
		assertTrue(entrants.size() == 1);
		assertTrue(entrants.get(0).getEMail().equals("shawn@kemp.com"));

		List<Person> attendees = _repo.getByRole(Role.Attendee);
		assertTrue(attendees.size() == 1);
		assertTrue(attendees.get(0).getEMail().equals("michael@jordan.com"));

		List<Person> judges = _repo.getByRole(Role.Judge);
		assertTrue(judges.size() == 1);
		assertTrue(judges.get(0).getEMail().equals("gary@payton.com"));
	}

	private void add4DifferentRoles() throws ClassNotFoundException,
			IOException {
		add();

		Person organizer = new Person();
		organizer.setEMail("larry@bird.com");
		organizer.setFirstName("Larry");
		organizer.setLastName("Bird");
		organizer.setPassword("I love the Sonics.");
		organizer.setRole(Role.Organizer);
		organizer.setPhoneNumber("1-242-395-5822");
		_repo.add(organizer);

		Person judge = new Person();
		judge.setEMail("gary@payton.com");
		judge.setFirstName("Gary");
		judge.setLastName("Payton");
		judge.setPassword("yadda yadda");
		judge.setRole(Role.Judge);
		judge.setPhoneNumber("1-222-315-5821");
		_repo.add(judge);

		Person entrant = new Person();
		entrant.setEMail("shawn@kemp.com");
		entrant.setFirstName("Shawn");
		entrant.setLastName("Kemp");
		entrant.setPassword("Cocaine is a hell of a drug");
		entrant.setRole(Role.Entrant);
		entrant.setPhoneNumber("1-112-355-5822");
		_repo.add(entrant);
	}

}