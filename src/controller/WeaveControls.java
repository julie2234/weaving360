/*
 * Mr. JJ TCSS 360: Weave App
 */

package controller;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

import com.sun.awt.AWTUtilities;

import repository.CategoryRepository;
import repository.EntryRepository;
import repository.PersonRepository;
import view.DefaultBody;
import view.EntrantHomeBody;
import view.InputEntryBody;
import view.JudgeEntriesPanel;
import view.JudgeEntryViewPanel;
import view.JudgeHomePanel;
import view.OrganizerHomePanel;
import view.OrganizerListPeoplePanel;
import view.RegisterBody;
import view.ViewEntryBody;
import view.WeaveGUI;
import weavedraft.WeaveDraft;
import model.Category;
import model.Person;
import model.Entry;
import model.Role;

/**
 * Class controls the weaveGUI display and makes requests on repositories to
 * save/load data.
 * 
 * @author Matt Adams
 * @author James Marquardt (major edits)
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
        // my_bodies = new Stack<JPanel>();
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
            selectUserHome();
        } else {
            _view.setDefaultHeader(this);
            _view.badLogin();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectUserHome() {
        if (_person != null) {
            switch (_person.getRole()) {
                case Entrant:
                    entrantHome();
                    break;
                case Attendee:
                    entrantHome();
                    break;
                case Judge:
                    judgeHome();
                    break;
                case Organizer:
                    organizerHome();
                    break;
            }
        } else {
            _view.setBody(new DefaultBody());
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
    public void inputEntry(Entry entry, boolean isEditing) {
        try {
            _view.setBody(new InputEntryBody(this, entry, _person, _categoryRepo.getAll(), isEditing));
        } catch (Exception e) {
            showUnhandledException(e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelFromDialog(JDialog dialog) {
        dialog.dispose();
        _view.getFrame().getRootPane().getGlassPane().setVisible(false);
        _view.getFrame().setEnabled(true);
        _view.getFrame().requestFocus();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void submitEntryFromDraft(Entry entry, byte[] image, 
                                     /*WeaveDraft weavedraft,*/ JDialog dialog) {
        dialog.dispose();
        _view.getFrame().setEnabled(true);
        
        entry.setImage(image);
        /*entry.setDraft(weavedraft)*/;
        try {
            _entryRepo.add(entry);
        } catch (IOException e) {
            e.printStackTrace();
            showUnhandledException(e);
        }
        _view.getFrame().getRootPane().getGlassPane().setVisible(false);
        _view.getFrame().requestFocus();
        _view.setBody(new ViewEntryBody(this, entry));
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IOException
     */
    @Override
    public void submitEntry(Entry entry, int the_tieupSize, int the_gridSize) {
        if (entry.isComplete()) {
            if (isFirstTimeInCategory(entry)) {
                try {
                    JDialog dialog = new JDialog();
                    WeaveDraft weavedraft =
                            new WeaveDraft(the_gridSize, the_tieupSize, entry, this, dialog);
                    dialog.add(weavedraft);
                    dialog.setUndecorated(true);
                    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true); 
                    dialog.pack();
                    dialog.setLocationRelativeTo(_view.getFrame());
                    dialog.setMinimumSize(dialog.getSize());
                    _view.getFrame().getRootPane().getGlassPane().setVisible(true);
                    _view.getFrame().setEnabled(false);
              
                    // _entryRepo.add(entry);
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
            } else {
                _view.showError("You already have an entry in the " + entry.getCategoryName() +
                                " category. " +
                                "You cannot have more than one entry per category");
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
    public void editEntry(Entry entry) {
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
                _view.showError("You already have an entry in the " + entry.getCategoryName() +
                                " category. " +
                                "You cannot have more than one entry per category");
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
        entrantHome();
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
    public void changeRole(Role role) {

        _person.setRole(role);
        try {
            _personRepo.update(_person);
        } catch (Exception e) {
            showUnhandledException(e);
        }

        selectUserHome();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void entrantHome() {
        List<Entry> personEntries = new ArrayList<Entry>();
        try {
            personEntries = _entryRepo.getByPersonEMail(_person.getEMail());
        } catch (Exception e) {
            showUnhandledException(e);
        }
        try {
            _view.setBody(new EntrantHomeBody(this, _person, personEntries,
                                              personEntries.size() < 3));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restart() {
        _person = null;
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
            if (entry.getCategoryName().equals(existingEntry.getCategoryName()) &&
                !entry.equals(existingEntry)) {
                firstTimeInCategory = false;
            }
        }
        return firstTimeInCategory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mainHome() {
        _view.setBody(new DefaultBody());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void judgeHome() {
        try {
            _view.setBody(new JudgeHomePanel(this, _person, _categoryRepo));
        } catch (Exception e) {
            showUnhandledException(e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void organizerHome() {
        try {
            _view.setBody(new OrganizerHomePanel(this));
        } catch (Exception e) {
            showUnhandledException(e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void judgeByCategoryView(Category category) {

        try {
            _view.setBody(new JudgeEntriesPanel(this, _entryRepo.getByCategory(category
                    .getName()), category.getName(), _personRepo));
        } catch (Exception e) {
            // showUnhandledException(e);
            e.printStackTrace();
        }

    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void judgeEntryView(Entry entry) {
        
        _view.setBody(new JudgeEntryViewPanel(this, entry));
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void organizerViewAll(Role role) {
        try {
            List<Person> allPeople = _personRepo.getAll();
            List<Person> peopleByRole = new ArrayList<Person>();
            if (role.equals(Role.Attendee)) {
                for (Person p : allPeople) {
                    if (p.getRole().equals(Role.Attendee) || p.getRole().equals(Role.Judge) ||
                        p.getRole().equals(Role.Entrant))
                        peopleByRole.add(p);
                }
            } else {
                for (Person p : allPeople) {
                    if (p.getRole().equals(role))
                        peopleByRole.add(p);
                }
            }
            _view.setBody(new OrganizerListPeoplePanel(this, peopleByRole, role));
        } catch (Exception e) {
            // showUnhandledException(e);
            e.printStackTrace();
        }
    }

    @Override
    public void viewEntry(Entry entry) {
        _view.setBody(new ViewEntryBody(this, entry));
    }
}
