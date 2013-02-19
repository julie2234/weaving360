/**
 * 
 */
package controller;
import java.io.IOException;
import model.Entry;
import model.Person;

/**
 *
 */
public interface Controls {
    void start();
    Person getPerson();
    void login(String username, String password);
    void register();
    void inputEntry(Entry entry);
    void submitEntry(Entry entry) throws IOException;
    void editEntry(Entry entry) throws IOException;
    void editAccountInfo();
    /*
     * viewContestants, viewCategories, etc.    
     */
}