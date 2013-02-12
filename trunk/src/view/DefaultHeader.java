package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controls;

/**
 * Header panel for user to log in from.
 * 
 * @author James Marquardt
 *
 */
public class DefaultHeader extends JPanel {
	
	/**
	 * A button to submit login information.
	 */
	private JButton my_login_button;
	/**
	 * User's email.
	 */
	private String my_email;
	/**
	 * User's password.
	 */
	private String my_password;
	
	/**
	 * Constructor for login panel.
	 * 
	 */
	public DefaultHeader(final Controls controller) {
		
		setBackground(Color.WHITE);

		add(new JLabel("This is the default header"));
		
		my_login_button = new JButton("Register");
	    my_login_button.addActionListener(new ActionListener() {
	        public void actionPerformed(final ActionEvent the_event) {
	          controller.login();
	        }
	      });
		
		my_email = "";
		my_password = "";
		
		add(my_login_button);
		
		setVisible(true);
		
	}
	
	/**
	 * Getter for email.
	 * 
	 * @return User's email.
	 */
	public String getEmail() {
		
		return my_email;
		
	}
	
	/**
	 * Getter for password.
	 * 
	 * @return User's email.
	 */
	public String getPassWord() {
		
		return my_password;
		
	}

}
