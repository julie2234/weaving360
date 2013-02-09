package repository;

import java.io.FileNotFoundException;
import java.io.IOException;

import domain.Person;

public class PersonRepository extends AbstractRepository {
	
	public PersonRepository(String configFileName) throws FileNotFoundException, IOException {
		super(configFileName, "PersonDataFolder"); 
	}
	
	public void add(Person person) {
		
	}
	
	public void remove(Person person) {
	
	}
	
	public Person getByEMail(String email) {
		return new Person();
	}
	
/*	public Person GetByLogin(String username, String password) {
		
	}*/
}
