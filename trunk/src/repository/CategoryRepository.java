package repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Category;
import model.Person;

public class CategoryRepository extends AbstractRepository<Category> {

	private final static String PROPERTY_NAME = "CategoryFolder";

	private PersonRepository _personRepository;

	public CategoryRepository() throws FileNotFoundException, IOException {
		super(PROPERTY_NAME);
		_personRepository = new PersonRepository();
	}

	public CategoryRepository(String configFileName)
			throws FileNotFoundException, IOException {
		super(configFileName, PROPERTY_NAME);
		_personRepository = new PersonRepository();
	}

	public void add(Category category) throws IOException {
		saveObject(category, category.getName());
	}

	public void update(Category category) throws IOException {
		saveObject(category, category.getName());
	}

	public void remove(Category category) throws IOException {
		deleteFile(category.getName());
	}

	@Override
	public List<Category> getAll() throws ClassNotFoundException, IOException {
		List<Category> allCategories = super.getAll();
		for (Category category : allCategories) {
			reloadCachedJudges(category);
		}
		return allCategories;
	}

	public Category getByName(String name) throws IOException,
			ClassNotFoundException {
		Category category = loadObject(name);
		reloadCachedJudges(category);
		return category;
	}

	private void reloadCachedJudges(Category category)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		if (category != null) {
			List<Person> freshJudges = new ArrayList<Person>();
			for (Person person : category.getJudges()) {
				freshJudges
						.add(_personRepository.getByEMail(person.getEMail()));
			}
			category.setJudges(freshJudges);
		}
	}

}