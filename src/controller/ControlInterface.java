/**
 * 
 */
package controller;
import model.Entry;

/**
 *
 */
public interface ControlInterface {
    void start();
    void login();
    void register();
    void inputEntry(Entry entry);
    void submitEntry(Entry entry);
    void editAccountInfo();
    /*
     * viewContestants, viewCategories, etc.    
     */
}
