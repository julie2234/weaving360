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
    void login();
    void register();
    void inputEntry(Entry entry);
    void submitEntry(Entry entry) throws IOException;
    void editEntry(Entry entry) throws IOException;
    void editAccountInfo();
    /*
     * viewContestants, viewCategories, etc.    
     */
}
