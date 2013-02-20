/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import model.Person;
import controller.Controls;

/**
 * WeaveGUI contains main JFrame for whole application. It provides
 * methods to alter its head and body panels.
 * @author Matt Adams
 * @version 1.0
 */
public class WeaveGUI {
    private static final Dimension FRAME_DIM = new Dimension(600, 600);
    private Controls my_controller;
    private JFrame my_frame;
    private JPanel my_header;
    private JPanel my_body;
    /**
     * Constructs main frame and sets controller reference.
     * @param controller Reference to controller.
     */
    public WeaveGUI(Controls controller) {
        my_controller = controller;
        my_frame = new JFrame("Just BeWeave");
    }
    /**
     * Creates view displayed at application launch.
     */
    public void createView() {
        my_header = new HeaderPanel(my_controller);
        my_body = new DefaultBody(); 
        my_frame.add(my_header, BorderLayout.NORTH);
        my_frame.add(my_body, BorderLayout.CENTER);
        my_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        my_frame.setPreferredSize(FRAME_DIM);
        my_frame.pack();
        my_frame.setLocationRelativeTo(null);
        my_frame.setVisible(true);
    }
    /**
     * Sets body of this frame to the provided JPanel.
     * @param panel JPanel to be displayed.
     */
    public void setBody(JPanel panel) {
        my_frame.remove(my_body);
        my_body = panel;
        my_frame.add(my_body, BorderLayout.CENTER);
        my_frame.pack();
    }
    /**
     * Sets header of this frame to the provided JPanel
     * @param control Control reference.
     * @param person Person object for name display.
     */
    public void setHeader(Controls control, Person person) {      
            my_frame.remove(my_header);
            my_header = new HeaderPanel(control, person);
            my_frame.add(my_header, BorderLayout.NORTH);
            my_frame.pack();     
    }
    /**
     * Sets header of this frame to default header JPanel.
     * @param control Control reference.
     */
    public void setDefaultHeader(Controls control) {
        my_frame.remove(my_header);
        my_header = new HeaderPanel(control);
        my_frame.add(my_header, BorderLayout.NORTH);
        my_frame.pack();        
    }
    /**
     * Displays warning message for a bad login attempt.
     */
    public void badLogin() {
        JOptionPane.showMessageDialog(my_frame, "Invalid Username and Password", "Input Error", 
                                      JOptionPane.WARNING_MESSAGE);
    }
    /**
     * Displays warning message for repository error, then closes the program.
     * @param error Error message to be displayed.
     */
    public void showError(String error) {
        JOptionPane.showMessageDialog(my_frame, error, "Repository Error", 
                                      JOptionPane.WARNING_MESSAGE);
    }
  }


