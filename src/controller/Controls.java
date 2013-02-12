/**
 * 
 */
package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

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
    //PersonRepository _personRepository;
    //EntryRepository _entryRepository;
    //CategoryRepository _categoryRepository;
    
    
    public Controls() {
       _person = new Person();
       _view = new WeaveGUI(this);
       //_personRepository = new PersonRepository();
       //_entryRepository = new EntryRepository();
       //_categoryRepository = new CategoryRepository();
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
    public void login() {
    	_view.setBody(new RegisterBody(this));

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
     */
    @Override
    public void submitEntry(Entry entry) {
    	//add entry to _entryRepository
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
