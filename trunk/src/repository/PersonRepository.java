package repository;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import model.Person;

public class PersonRepository extends AbstractRepository {

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
		String filePath = getDataFolder() + "\\" + eMail;
		File file = new File(filePath);

		Person person = null;
		if (file.exists()) {
			InputStream inputStream = new FileInputStream(filePath);
			InputStream buffer = new BufferedInputStream(inputStream);
			ObjectInput input = new ObjectInputStream(buffer);
			person = (Person) input.readObject();
			input.close();
		}
		return person;
	}

	public Person getByLogin(String eMail, String password) throws ClassNotFoundException, IOException {
		Person person = getByEMail(eMail);
		if (person != null && !person.getPassword().equals(password)) {
			person = null;
		}
		return person;
	}

}