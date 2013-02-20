/*
 * Mr. JJ
 * TCSS 360: Weave App 
 */
package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import repository.CategoryRepository;
import repository.EntryRepository;
import repository.PersonRepository;
import view.DefaultBody;
import view.EntrantHomeBody;
import view.InputEntryBody;
import view.RegisterBody;
import view.ViewEntryBody;
import view.WeaveGUI;
import model.Person;
import model.Entry;

/**
 * Class controls the weaveGUI display and makes requests on repositories to save/load data.
 * @author Matt Adams
 * @version 1.0
 */
public class WeaveControls implements Controls {
    /** Person object of this controller.*/
    private Person  _person;
    /** Main frame of GUI display.*/
    private WeaveGUI _view;
    /** Data storage for Person objects.*/
    private PersonRepository _personRepo;
    /** Data storage for Entry objects.*/
    private EntryRepository _entryRepo;
    /** Stores collection of Entries based on category.*/
    private CategoryRepository _categoryRepo;
    /**
     * Constructs controller. It instantiates a Person, WeaveGUI, PersonRepository, 
     * EntryRepository, and CategoryRepository for use in the application.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public WeaveControls() throws FileNotFoundException, IOException {
       _person = new Person();
       _view = new WeaveGUI(this);
       try {
           _personRepo = new PersonRepository();
           _entryRepo = new EntryRepository();
           _categoryRepo = new CategoryRepository();
       } catch (Exception e) {
           _view.showException("There was an error: " + e.getMessage());
       }
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
    public void login(String username, String password) { 	
    	try {
			_person = _personRepo.getByLogin(username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	if (_person != null) {
    	    _view.setHeader(this, _person);
    	    _view.setBody(new EntrantHomeBody(this, _person, _entryRepo));
    	} else {
    	    _view.setDefaultHeader(this);
    	    _view.badLogin();
    	}
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void register(Person the_person) {
        _person = the_person;
        try {
            _personRepo.add(_person);
        } catch (IOException e) {
            e.printStackTrace();
        }
        _view.setHeader(this, _person);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void inputEntry(Entry entry) {
    	_view.setBody(new InputEntryBody(this, entry, _person));
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
    /**
     * {@inheritDoc}
     */
    @Override
    public void beginRegistration() {
        _view.setBody(new RegisterBody(this));     
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void home() {
        _view.setBody(new EntrantHomeBody(this, _person, _entryRepo));
        
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void restart() {
        _view.setBody(new DefaultBody());
        _view.setDefaultHeader(this);
    }

}
