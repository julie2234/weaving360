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

	/**
	 * Constructs a category repository with the default configuration file name
	 * of weaving.cfg for adding, removing, updating, and retrieving category
	 * objects.
	 * 
	 * @throws FileNotFoundException
	 *             The exception thrown if the default configuration file is not
	 *             found.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the configuration file.
	 */
	public CategoryRepository() throws FileNotFoundException, IOException {
		super(PROPERTY_NAME);
		_personRepository = new PersonRepository();
	}

	/**
	 * Constructs a category repository for adding, removing, updating, and
	 * retrieving category objects.
	 * 
	 * @throws FileNotFoundException
	 *             The exception thrown if the default configuration file is not
	 *             found.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while reading
	 *             the configuration file.
	 */
	public CategoryRepository(String configFileName)
			throws FileNotFoundException, IOException {
		super(configFileName, PROPERTY_NAME);
		_personRepository = new PersonRepository(configFileName);
	}

	/**
	 * Adds a category to the repository.
	 * 
	 * @param category
	 *            The Category object to add to the repository.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while saving
	 *             the object.
	 */
	public void add(Category category) throws IOException {
		saveObject(category, category.getName());
	}

	/**
	 * Updates a category to the repository.
	 * 
	 * @param category
	 *            The category object to update to the repository.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while
	 *             updating the object.
	 */
	public void update(Category category) throws IOException {
		saveObject(category, category.getName());
	}

	/**
	 * Removes a category from the repository.
	 * 
	 * @param category
	 *            The category object to remove from the repository.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while
	 *             removing the object.
	 */
	public void remove(Category category) throws IOException {
		deleteFile(category.getName());
	}

	/**
	 * Retrieves all categories.
	 */
	@Override
	public List<Category> getAll() throws ClassNotFoundException, IOException {
		List<Category> allCategories = super.getAll();
		for (Category category : allCategories) {
			reloadCachedJudges(category);
		}
		return allCategories;
	}

	/**
	 * Get a category based on its name.
	 * 
	 * @param name
	 *            The name of the category
	 * @return The Category object that was loaded from the repository.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while
	 *             retrieving the object.
	 * @throws ClassNotFoundException
	 *             The exception thrown if the Category class cannot be found.
	 */
	public Category getByName(String name) throws IOException,
			ClassNotFoundException {
		Category category = loadObject(name);
		reloadCachedJudges(category);
		return category;
	}

	/**
	 * Reloads the person objects. The person objects that are loaded will be
	 * the person objects as they were when this object was last saved to the
	 * repository.
	 * 
	 * @param category
	 *            The Category object that needs its people reloaded.
	 * @throws IOException
	 *             The IO exception thrown if there is an IO error while
	 *             reloading a person.
	 * @throws ClassNotFoundException
	 *             The exception thrown if the Person class cannot be found.
	 */
	private void reloadCachedJudges(Category category) throws IOException,
			ClassNotFoundException {
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