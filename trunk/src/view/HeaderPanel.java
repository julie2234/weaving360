package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.Controls;

/**
 * Creates the header panels to login and display logged in user.
 * 
 * @author James Marquardt
 * @version 13 February 2013
 */
public class HeaderPanel extends JPanel {
	
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
	 * Label to show what username field is for.
	 */
	JLabel my_username_label;
	/**
	 * Label to show what password field is for.
	 */
	JLabel my_password_label;
	
	public HeaderPanel(final Controls the_controller) {
		
		setBackground(Color.BLUE);
		setPreferredSize(new Dimension(100, 100));
		setSize(new Dimension(100, 100));
		
		my_password_field = new JPasswordField();
		my_password_field.setBounds(279, 42, 117, 20);
		my_password_field.setBackground(Color.LIGHT_GRAY);
		my_password_field.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		my_password_field.setAlignmentX(Component.RIGHT_ALIGNMENT);
		add(my_password_field);
		
		my_username_input = new JTextField();
		my_username_input.setBounds(279, 11, 117, 20);
		add(my_username_input);
		my_username_input.setColumns(10);
		
		my_submit_button = new JButton("Submit");
		my_submit_button.setBounds(308, 73, 89, 23);
		add(my_submit_button);
		
		my_username_label = new JLabel("Username");
		my_username_label.setBounds(221, 14, 48, 14);
		add(my_username_label);
		
		my_password_label = new JLabel("Password");
		my_password_label.setBounds(223, 42, 46, 20);
		add(my_password_label);
		
	}
	
	public HeaderPanel()

}
