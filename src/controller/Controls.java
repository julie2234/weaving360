/**
 * 
 */
package controller;
import javax.swing.JDialog;

import model.Category;
import model.Entry;
import model.Person;
import model.Role;

/**
 * Controls provides functionality to manage the current GUI display,
 * and to update the state of data in a repository system.
 */
public interface Controls {
    /** Initializes the default display for the application. */
    void start();
    /** Go to main home page. */
    void mainHome();
    /** Go to user's home page */
    void selectUserHome();
    /** Sets the body panel to an EntrantHomeBody. */
    void entrantHome();
    /** Sets the body panel to a JudgeHomeBody. */
    void judgeHome();
    /** Sets body panel to judge's view of entries by category.*/
    void judgeByCategoryView(Category cat);
    /** Sets body panel to a judge's view of a single entry. */
    void judgeEntryView(Entry entry);
    /** Sets the body panel to an OrganizerHomeBody. */
    void organizerHome();
    /** Sets body panel to organizer's view of a list of people.*/
    void organizerViewAll(Role role);
    /** Resets the display after a user signs out. */
    void restart();
    /** Retrieves user information after successful login. */
    void login(String username, String password);
    /** Sets the body panel to a RegisterBody. */
    void beginRegistration();
    /** Adds a newly registered user to the person repository. */
    void register(Person the_person);
    /** Sets the body panel to an InputEntryBody. */
    void inputEntry(Entry entry);
    /** Return to main view from dialog */
    void cancelFromDialog(JDialog dialog);
    /** Adds new entry to the entry repository */
    void submitEntryFromDraft(Entry entry, byte[] image, 
                                     /*WeaveDraft weavedraft,*/ JDialog dialog);
    /** Adds a new entry to the entry repository. */
    void submitEntry(Entry entry, int the_tieupSize, int the_gridSize);
    /** Updates and saves changes to current entry. */
    void editEntry(Entry entry);
    /** Removes the specified entry. */
    void removeEntry(Entry entry);
    /** Updates and saves changes to user's account information.*/
    void editAccountInfo();
    /** Changes the role of the current user.*/
    void changeRole(Role role);
    /*
     * viewContestants, viewCategories, etc.    
     */
}
