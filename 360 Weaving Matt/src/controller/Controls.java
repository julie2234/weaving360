/**
 * 
 */
package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import repository.CategoryRepository;
import repository.EntryRepository;
import repository.PersonRepository;
import view.EntrantHomeBody;
import view.InputEntryBody;
import view.RegisterBody;
import view.ViewEntryBody;
import view.WeaveGUI;
import model.Person;
import model.Entry;

/**
 *
 */
public class Controls implements ControlInterface {
    Person  _person;
    WeaveGUI _view;
    PersonRepository _personRepo;
    EntryRepository _entryRepo;
    CategoryRepository _categoryRepo;
    
    
    public Controls() throws FileNotFoundException, IOException {
       _person = new Person();
       _view = new WeaveGUI(this);
       _personRepo = new PersonRepository();
       _entryRepo = new EntryRepository();
       _categoryRepo = new CategoryRepository();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        _view.createView();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void login(String the_username, String the_password) {
    		try {
				_person = _personRepo.getByLogin(the_username, the_password);
			} catch (ClassNotFoundException e) {
				System.out.println("Class not found");
			} catch (IOException e) {
				System.out.println("Invalid file");
			}
    		
    		_view.setBody(new RegisterBody(this));
    		_view.setHeader(this, _person);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void register() {
        _view.setBody(new EntrantHomeBody(this));

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void inputEntry(Entry entry) {
    	_view.setBody(new InputEntryBody(this, entry));
    }
    /**
     * {@inheritDoc}
     * @throws IOException 
     */
    @Override
    public void submitEntry(Entry entry) throws IOException {
    	_entryRepo.add(entry);
    	_view.setBody(new ViewEntryBody(this, entry));
    }
    
    public void editEntry(Entry entry) throws IOException {
    	_entryRepo.update(entry);
    	_view.setBody(new ViewEntryBody(this, entry));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editAccountInfo() {
        // TODO Auto-generated method stub
    }

}
