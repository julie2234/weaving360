package JamesGUI;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Header panel for after user has logged in.
 * 
 * @author James Marquardt
 *
 */
public class LoggedInHeader extends JPanel {
	
	/**
	 * User's name.
	 */
	private String my_name;
	
	/**
	 * Constructor for header.
	 */
	public LoggedInHeader() {
		
		add(new JLabel("Logged in as "));

	}
	
	/**
	 * Setter for the user's name
	 */
	public void setName(String the_name) {
		
		my_name = the_name;
		
	}
	
	public void addNameLabel() {
		
		add(new JLabel(my_name));
		
	}

}
