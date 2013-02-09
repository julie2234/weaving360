/**
 * 
 */
package model;

import view.PersonObserver;

/**
 * @author Jamada
 *
 */
public interface Person {
    /** Add person to repository.  */
    void addPerson();
    /**  */
    void removePerson();
    /**  */
    void getByLogin();
    /**  */
    void getByEmail();
    /**  */
    void getByRole();
    /**  */
    void getAll();
    /**  */
    void setObserver(PersonObserver o);  
}
