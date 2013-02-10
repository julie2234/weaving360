/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Person;

import controller.ControlInterface;

/**
 *
 */
public class WeaveGUI implements PersonObserver {
    private ControlInterface my_control;
    private Person my_person;
    private JFrame my_frame;
    private JPanel my_header;
    private JPanel my_body;
    
    public WeaveGUI(ControlInterface the_control, Person the_person) {
        my_control = the_control;
        my_person = the_person;
        //my_person.setObserver((PersonObserver)this);
        my_frame = new JFrame("Just BeWeave");
    }
    public void createView() {
        my_header = new JPanel();
        my_body = new JPanel();
        my_header.setBackground(Color.BLUE);
        my_header.setPreferredSize(new Dimension(100, 100));
        my_frame.add(my_header, BorderLayout.NORTH);
        my_frame.add(my_body, BorderLayout.CENTER);
        my_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        my_frame.setPreferredSize(new Dimension(600, 600));
        my_frame.pack();
        my_frame.setLocationRelativeTo(null);
        my_frame.setVisible(true);
    }
    /*
     * Method that receives person updates from class PersonInfo. 
     */
    @Override
    public void updatePerson(Person the_person) {
        // TODO Auto-generated method stub
        
    }
}
