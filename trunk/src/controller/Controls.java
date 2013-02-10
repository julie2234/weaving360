/**
 * 
 */
package controller;

import view.WeaveGUI;
import model.Person;

/**
 *
 */
public class Controls implements ControlInterface {
    Person  my_person;
    WeaveGUI my_view;
    
    
    public Controls() {
       my_person = new Person();
       my_view = new WeaveGUI(this, my_person);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        my_view.createView();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void login() {
        // TODO Auto-generated method stub

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void register() {
        // TODO Auto-generated method stub

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void inputEntry() {
        // TODO Auto-generated method stub

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void submitEntry() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editAccountInfo() {
        // TODO Auto-generated method stub
    }

}
