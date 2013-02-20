package repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Person;
import model.Role;

public class PersonRepository extends AbstractRepository<Person> {

	private final static String PROPERTY_NAME = "PersonFolder";

	/**
	 * Constructs a person repository with the default configuration file name
	 * of weaving.cfg for adding, removing, updating, and retrieving person
	 * objects.
	 * 
	 * @throws FileNotFoundException
	 *             The exception thrown if the default configuration file is not
	 *             found.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the configuration file.
	 */
	public PersonRepository() throws FileNotFoundException, IOException {
		super(PROPERTY_NAME);
	}

	/**
	 * Constructs a person repository for adding, removing, updating, and
	 * retrieving person objects.
	 * 
	 * @throws FileNotFoundException
	 *             The exception thrown if the default configuration file is not
	 *             found.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the configuration file.
	 */
	public PersonRepository(String configFileName)
			throws FileNotFoundException, IOException {
		super(configFileName, PROPERTY_NAME);
	}

	/**
	 * Adds a person to the repository.
	 * 
	 * @param person
	 *            The Person object to add to the repository.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while saving
	 *             the object.
	 */
	public void add(Person person) throws IOException {
	        saveObject(person, person.getEMail());
	}

	/**
	 * Updates a person to the repository.
	 * 
	 * @param person
	 *            The person object to update to the repository.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while
	 *             updating the object.
	 */
	public void update(Person person) throws IOException {
		saveObject(person, person.getEMail());
	}

	/**
	 * Removes a person from the repository.
	 * 
	 * @param person
	 *            The person object to remove from the repository.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while
	 *             removing the object.
	 */
	public void remove(Person person) throws IOException {
		deleteFile(person.getEMail());
	}

	/**
	 * Gets a person based on their e-mail address.
	 * 
	 * @param eMail
	 *            The e-mail address of the person.
	 * @return The Person object that has the specified e-mail address.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the object.
	 * @throws ClassNotFoundException
	 *             The exception thrown if the Person class cannot be found.
	 */
	public Person getByEMail(String eMail) throws IOException,
			ClassNotFoundException {
		return loadObject(eMail);
	}

	/**
	 * Gets a person based on the supplied e-mail address and password.
	 * 
	 * @param eMail
	 *            The e-mail address of the person.
	 * @param password
	 *            The password for the person.
	 * @return The Person object that has the specified e-mail address.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the object.
	 * @throws ClassNotFoundException
	 *             The exception thrown if the Person class cannot be found.
	 */
	public Person getByLogin(String eMail, String password)
			throws ClassNotFoundException, IOException {
		Person person = getByEMail(eMail);
		//if (person != null && !person.getPassword().equals(password)) {
		//	person = null;
		//}
		return person;
	}

	/**
	 * Gets all people who are in a specified role.
	 * 
	 * @param role
	 *            The role the query.
	 * @return A list of People objects that are in the specified role.
	 * @throws ClassNotFoundException
	 *             The exception thrown if the Person class cannot be found.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the objects.
	 */
	public List<Person> getByRole(Role role) throws ClassNotFoundException,
			IOException {
		List<Person> filteredList = new ArrayList<Person>();
		for (Person person : getAll()) {
			if (person.getRole().equals(role)) {
				filteredList.add(person);
			}
		}
		return filteredList;
	}

}