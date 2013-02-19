/**
 * 
 */
package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.swing.text.JTextComponent;

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
public class WeaveControls implements Controls {
    private Person  _person;
    private WeaveGUI _view;
    private PersonRepository _personRepo;
    private EntryRepository _entryRepo;
    private CategoryRepository _categoryRepo;
    
    
    public WeaveControls() throws FileNotFoundException, IOException {
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
    public void login(String username, String password) {
    	
    	try {
			_person = _personRepo.getByLogin(username, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	_view.setHeader(this, _person);
    	_view.setBody(new RegisterBody(this));

    }
    public Person getPerson() {
        return _person;
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

}
