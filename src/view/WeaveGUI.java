/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import model.Person;
import controller.Controls;

/**
 *
 */
public class WeaveGUI {
    private static final Dimension FRAME_DIM = new Dimension(600, 600);
    private Controls my_controller;
    private JFrame my_frame;
    private JPanel my_header;
    private JPanel my_body;
    
    public WeaveGUI(Controls controller) {
        my_controller = controller;
        my_frame = new JFrame("Just BeWeave");
    }
    
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
    
    public void setBody(JPanel panel) {
        my_frame.remove(my_body);
        my_body = panel;
        my_frame.add(my_body, BorderLayout.CENTER);
        my_frame.pack();
    }
    
    public void setHeader(Controls control, Person person) {      
            my_frame.remove(my_header);
            my_header = new HeaderPanel(control, person);
            my_frame.add(my_header, BorderLayout.NORTH);
            my_frame.pack();     
    }
    public void setDefaultHeader(Controls control) {
        my_frame.remove(my_header);
        my_header = new HeaderPanel(control);
        my_frame.add(my_header, BorderLayout.NORTH);
        my_frame.pack();        
    }
    public void badLogin() {
        JOptionPane.showMessageDialog(my_frame, "Invalid Username and Password", "Input Error", 
                                      JOptionPane.WARNING_MESSAGE);
    }
    public void showException(String error) {
        JOptionPane.showMessageDialog(my_frame, error, "Repository Error", 
                                      JOptionPane.WARNING_MESSAGE);      
    }
  }


