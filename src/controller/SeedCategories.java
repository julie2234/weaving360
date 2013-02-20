package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Category;
import repository.CategoryRepository;

public class SeedCategories {

	static CategoryRepository _repo; 
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		_repo = new CategoryRepository();
		
		addCategory("Jacquard");
		addCategory("Basket");
		addCategory("Leno or Gauze");
		addCategory("Pile");
		addCategory("Plain");
		addCategory("Satin");
		addCategory("Twill");
	}
	
	private static void addCategory(String name) throws IOException {
		Category category = new Category();
		category.setName(name);		
		_repo.add(category);
	}

}
