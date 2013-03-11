package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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

	private static final long serialVersionUID = -2249859744113988544L;

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

	private HeaderPanel() {
		setLayout(new GridBagLayout());
		setPreferredSize(PANEL_DIM);
		setBackground(Color.BLUE);
		my_title = new JLabel("Just BeWeave");
		my_title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
		my_title.setForeground(Color.WHITE);
	}

	/**
	 * Creates a header panel when not yet logged in.
	 * 
	 * @param the_controller
	 *            That created this header.
	 */
	public HeaderPanel(final Controls the_controller) {
		this();
		my_password_field = new JPasswordField();
		// add(my_password_field);

		my_username_input = new JTextField();
		my_username_input.setColumns(10);

		my_submit_button = new JButton("Login");
        my_submit_button.setBackground(new Color(128, 128, 128));
        my_submit_button.setForeground(Color.WHITE);
        my_submit_button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
                                                                BorderFactory.createEmptyBorder(0, 22, 0, 22)));
		my_submit_button.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(final ActionEvent the_event) {
				// Sends username and password to controller login on button
				// press.
				the_controller.login(my_username_input.getText(), new String(
						my_password_field.getPassword()));
			}
		});
		my_reg_button = new JButton("Register");
        my_reg_button.setBackground(new Color(128, 128, 128));
        my_reg_button.setForeground(Color.WHITE);
        my_reg_button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
                                                                BorderFactory.createEmptyBorder(0, 14, 0, 13)));
		my_reg_button.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(final ActionEvent the_event) {
				the_controller.beginRegistration();
			}
		});
		my_username_label = new JLabel("Username");
		my_password_label = new JLabel("Password");
		my_new_user = new JLabel("New User?");
		my_username_label.setForeground(Color.WHITE);
		my_password_label.setForeground(Color.WHITE);
		my_new_user.setForeground(Color.WHITE);
		setupDefaultHead();
	}

	/**
	 * Creates a header panel when a user has logged in.
	 * 
	 * @param the_controller
	 *            The controller that created this header.
	 * @param the_person
	 *            The person that is logged in.
	 */
	public HeaderPanel(final Controls the_controller, Person the_person) {
		this();
		my_username_label = new JLabel("Hello, " + the_person.getFirstName()
				+ " " + the_person.getLastName());
		my_username_label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		my_username_label.setForeground(Color.WHITE);
		JLabel signout_label = new JLabel("Not " + the_person.getFirstName()
				+ "?");
		signout_label.setForeground(Color.WHITE);
		JButton signout = new JButton("Sign Out");
        signout.setBackground(new Color(169, 169, 169));
        signout.setForeground(Color.WHITE);
        signout.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
                                                             BorderFactory.createEmptyBorder(0, 14, 0, 13)));
		signout.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(final ActionEvent the_event) {
				the_controller.restart();
			}
		});
		setupLoginHead(signout, signout_label);
	}

	/**
	 * Sets up default header panel using GridBagLayout.
	 */
	private void setupDefaultHead() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		// c.anchor = GridBagConstraints.WEST;
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
		c.insets = new Insets(10, 0, 0, 0);
		c.gridx = 1;
		c.gridy = 3;
		this.add(my_new_user, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 2;
		c.gridy = 3;
		this.add(my_reg_button, c);
	}

	/**
	 * Sets up logged in header panel using GridBagLayout.
	 * 
	 * @param the_signout
	 *            JButton used to sign a user out.
	 * @param the_signout_label
	 *            JLabel for sign out button.
	 */
	private void setupLoginHead(JButton the_signout, JLabel the_signout_label) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 150;
		this.add(my_title, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 2;
		c.gridy = 1;
		this.add(my_username_label, c);
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 2;
		c.gridy = 2;
		this.add(the_signout_label, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 2;
		c.gridy = 3;
		this.add(the_signout, c);
	}
}