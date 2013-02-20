/*
 * Mr. JJ
 * TCSS 360: Weave App 
 */
package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
import model.Role;

/**
 * Class controls the weaveGUI display and makes requests on repositories to
 * save/load data.
 * 
 * @author Matt Adams
 * @version 1.0
 */
public class WeaveControls implements Controls {
	/** Person object of this controller. */
	private Person _person;
	/** Main frame of GUI display. */
	private WeaveGUI _view;
	/** Data storage for Person objects. */
	private PersonRepository _personRepo;
	/** Data storage for Entry objects. */
	private EntryRepository _entryRepo;
	/** Stores collection of Entries based on category. */
	private CategoryRepository _categoryRepo;

	/**
	 * Constructs controller. It instantiates a Person, WeaveGUI,
	 * PersonRepository, EntryRepository, and CategoryRepository for use in the
	 * application.
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
			_view.showError("There was an error: " + e.getMessage());
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
			home();
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
	public void inputEntry(Entry entry){
		try
		{				
			_view.setBody(new InputEntryBody(this, entry, _person,
					_categoryRepo.getAll()));				
		}
		catch (Exception e) {
			showUnhandledException(e);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IOException
	 */
	@Override
	public void submitEntry(Entry entry) {
		if (entry.isComplete()) {
			if (isFirstTimeInCategory(entry)) {
				try {
					_entryRepo.add(entry);
				} catch (Exception e) {
					showUnhandledException(e);
				}

				if (_person.getRole().equals(Role.Attendee)) {
					_person.setRole(Role.Entrant);

					try {
						_personRepo.update(_person);
					} catch (Exception e) {
						showUnhandledException(e);
					}
				}
				_view.setBody(new ViewEntryBody(this, entry));
			} else {
				_view.showError("You already have an entry in the "
						+ entry.getCategoryName() + " category. "
						+ "You cannot have more than one entry per category");
			}
		} else {
			_view.showError("Please fill out all fields.");
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IOException
	 */
	@Override
	public void editEntry(Entry entry){
		if (entry.isComplete()) {
			if (isFirstTimeInCategory(entry)) {
				try {
					_entryRepo.update(entry);
				} catch (Exception e) {
					showUnhandledException(e);
				}

				if (_person.getRole().equals(Role.Attendee)) {
					_person.setRole(Role.Entrant);

					try {
						_personRepo.update(_person);
					} catch (Exception e) {
						showUnhandledException(e);
					}
				}
				_view.setBody(new ViewEntryBody(this, entry));
			} else {
				_view.showError("You already have an entry in the "
						+ entry.getCategoryName() + " category. "
						+ "You cannot have more than one entry per category");
			}
		} else {
			_view.showError("Please fill out all fields.");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IOException
	 */
	@Override
	public void removeEntry(Entry entry) {
		try {
			_entryRepo.remove(entry);			
			
			List<Entry> personEntries = _entryRepo.getByPersonEMail(_person.getEMail());
			if (personEntries.size() == 0 && _person.getRole().equals(Role.Entrant)) {
				_person.setRole(Role.Attendee);
				_personRepo.update(_person);
			}			
		} catch (Exception e) {
			showUnhandledException(e);
		}
		home();
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
		List<Entry> personEntries = new ArrayList<Entry>();
		try {
			personEntries = _entryRepo.getByPersonEMail(_person.getEMail());
		} catch (Exception e) {
			showUnhandledException(e);		}
		_view.setBody(new EntrantHomeBody(this, _person, personEntries, personEntries.size() < 3));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void restart() {
		_view.setBody(new DefaultBody());
		_view.setDefaultHeader(this);
	}

	private void showUnhandledException(Throwable throwable) {
		_view.showError("There was an error: " + throwable.getMessage());
		System.exit(0);
	}

	private boolean isFirstTimeInCategory(Entry entry) {
		List<Entry> existingEntries = new ArrayList<Entry>();
		try {
			existingEntries = _entryRepo.getByPersonEMail(_person.getEMail());
		} catch (Exception e) {
			showUnhandledException(e);
		}
		boolean firstTimeInCategory = true;

		for (Entry existingEntry : existingEntries) {
			if (entry.getCategoryName().equals(existingEntry.getCategoryName())
					&& !entry.equals(existingEntry)) {
				firstTimeInCategory = false;
			}
		}
		return firstTimeInCategory;
	}
	
}
