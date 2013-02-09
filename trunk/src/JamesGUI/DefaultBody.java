package JamesGUI;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * First JPanel displayed on run.
 * 
 * @author James Marquardt
 *
 */
public class DefaultBody extends JPanel{
	
	/**
	 * Label to display welcome message.
	 */
	JLabel my_welcome_label;
	
	/**
	 * Constructor for panel. Adds label.
	 */
	public DefaultBody() {
		
		setBackground(Color.BLUE);
		
		my_welcome_label = new JLabel("Welcome to Just BeWeave");
		
		this.add(my_welcome_label);
	}

}
