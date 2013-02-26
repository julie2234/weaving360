/**
 * 
 */
package controller;
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
    /** Sets the body panel to an EntrantHomeBody. */
    void entrantHome();
    /** Sets the body panel to a JudgeHomeBody. */
    void judgeHome();
    /** Sets the body panel to an OrganizerHomeBody. */
    void organizerHome();
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
    /** Adds a new entry to the entry repository. */
    void submitEntry(Entry entry);
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
