/**
 * 
 */
package controller;
import java.io.IOException;

import model.Entry;

/**
 *
 */
public interface ControlInterface {
    void start();
    /**
     * Starts view rebuild based on user.
     * 
     * @param the_username The username provided in header panel.
     * @param the_password The passowrd provided in header panel.
     */
    void login(String the_username, String the_password);
    void register();
    void inputEntry(Entry entry);
    void submitEntry(Entry entry) throws IOException;
    void editEntry(Entry entry) throws IOException;
    void editAccountInfo();
    /*
     * viewContestants, viewCategories, etc.    
     */
}
