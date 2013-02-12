package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controls;

/**
 * Panel for filling out and submitting registration.
 * 
 * @author 
 *
 */
public class RegisterBody extends JPanel{
	private Controls _controller;
	private JLabel _panelTitle;
	private JLabel _firstNameLabel;
	private JTextField _firstNameField;
	private JLabel _lastNameLabel;
	private JTextField _lastNameField;
	private JLabel _phoneLabel;
	private JTextField _phoneField;
	private JLabel _emailLabel;
	private JTextField _emailField;
	private JLabel _passwordLabel;
	private JTextField _passwordField;
	private JButton _registerButton;
	
	public RegisterBody(Controls controller) {
		setBackground(Color.GRAY);
		_controller = controller;
		_panelTitle = new JLabel("Registration Page");
		_registerButton = new JButton("Register");
		_registerButton.addActionListener(new ActionListener() {
	        public void actionPerformed(final ActionEvent the_event) {
	          _controller.register();
	        }
	      });
		this.add(_panelTitle);
	    this.add(_registerButton);
	}
}