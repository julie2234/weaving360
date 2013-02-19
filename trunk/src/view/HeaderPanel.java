package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Person;

import controller.Controls;

/**
 * Creates the header panels to login and display logged in user.
 * 
 * @author James Marquardt
 * @version 13 February 2013
 */
public class HeaderPanel extends JPanel {
    /**
     * Panel dimension.
     */
	private static final Dimension PANEL_DIM = new Dimension(100, 150);
	/**
	 * Field to enter password.
	 */
	JPasswordField my_password_field;
	/**
	 * Field to enter username.
	 */
	JTextField my_username_input;
	/**
	 * Button to submit login information.
	 */
	JButton my_submit_button;
	/**
	 * Button for new user registration
	 */
	JButton my_reg_button;
	/**
	 * Label to show what username field is for.
	 */
	JLabel my_username_label;
	/**
	 * Label to show what password field is for.
	 */
	JLabel my_password_label;
	/**
	 * Application Title
	 */
	JLabel my_title;
	/**
	 * New User label.
	 */
	JLabel my_new_user;
	
	/**
	 * Creates a header panel when not yet logged in.
	 * 
	 * @param the_controller That created this header.
	 */
	public HeaderPanel(final Controls the_controller) {
		
		setLayout(new GridBagLayout());
	    setPreferredSize(PANEL_DIM);
		setBackground(Color.BLUE);
		
		my_password_field = new JPasswordField();
		//add(my_password_field);
		
		my_username_input = new JTextField();
		my_username_input.setColumns(10);
		
		my_submit_button = new JButton("Login");
		my_submit_button.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent the_event) {
		      //Sends username and password to controller login on button press.
		        the_controller.login(my_username_input.getText(), 
		                             my_password_field.getPassword().toString());
		    }
		});
        my_reg_button = new JButton("Register");
        my_reg_button.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent the_event) {
                the_controller.register();
            }
        });     		
		my_username_label = new JLabel("Username");
		my_password_label = new JLabel("Password");
		my_new_user = new JLabel("New User?");
		my_title = new JLabel("Just BeWeave");
		my_title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		setupGridBag();
		
	}
    private void setupGridBag() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        //c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 150;
        this.add(my_title, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;
        c.gridx = 1;
        c.gridy = 0;
        this.add(my_username_label, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        this.add(my_username_input, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;       
        c.gridy = 1;
        this.add(my_password_label, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        this.add(my_password_field, c);  
        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridy = 2;
        this.add(my_submit_button, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,0);
        c.gridx = 1;
        c.gridy = 3;
        this.add(my_new_user, c);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridy = 3;
        this.add(my_reg_button, c);
    }	
	/**
	 * Creates a header panel when a user has logged in.
	 * 
	 * @param the_controller The controller that created this header.
	 * @param the_person The person that is logged in.
	 */
	public HeaderPanel(Controls the_controller, Person the_person) {
		
		setLayout(null);
		
		my_username_label = new JLabel("Welcome " + the_person.getFirstName() +
				the_person.getLastName());
		my_username_label.setBounds(221, 14, 48, 14);
		add(my_username_label);
		
	}

}