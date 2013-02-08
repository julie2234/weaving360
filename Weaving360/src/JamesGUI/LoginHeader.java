package JamesGUI;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Header panel for user to log in from.
 * 
 * @author James Marquardt
 *
 */
public class LoginHeader extends JPanel {
	
	/**
	 * A button to submit login information.
	 */
	JButton my_login_button;
	
	/**
	 * Constructor for login panel.
	 * 
	 * @param the_listener Mouse listener to update header.
	 */
	public LoginHeader(ButtonClickListener the_listener) {
		
		setBackground(Color.WHITE);

		add(new JLabel("This is the login header"));
		
		my_login_button = new JButton("Submit");
		my_login_button.addMouseListener(the_listener);
		
		add(my_login_button);
		
		setVisible(true);
		
	}

}
