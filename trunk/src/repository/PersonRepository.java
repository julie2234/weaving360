package repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Person;
import model.Role;

public class PersonRepository extends AbstractRepository<Person> {

	public PersonRepository() throws FileNotFoundException, IOException {
		super("weaving.cfg", "PersonFolder");
	}

	public PersonRepository(String configFileName)
			throws FileNotFoundException, IOException {
		super(configFileName, "PersonFolder");
	}

	public void add(Person person) throws IOException {
		saveObject(person, person.getEMail());
	}

	public void update(Person person) throws IOException {
		saveObject(person, person.getEMail());
	}

	public void remove(Person person) throws IOException {
		deleteFile(person.getEMail());
	}

	public Person getByEMail(String eMail) throws IOException,
			ClassNotFoundException {
		return loadObject(eMail);
	}

	public Person getByLogin(String eMail, String password)
			throws ClassNotFoundException, IOException {
		Person person = getByEMail(eMail);
		if (person != null && !person.getPassword().equals(password)) {
			person = null;
		}
		return person;
	}
	
	public List<Person> getByRole(Role role) throws ClassNotFoundException, IOException {
		List<Person> filteredList = new ArrayList<Person>();
		for(Person person : getAll())
		{
			if (person.getRole().equals(role))
			{
				filteredList.add(person);
			}
		}
		return filteredList;
	}

}