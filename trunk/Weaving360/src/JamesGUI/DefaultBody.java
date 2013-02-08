package JamesGUI;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DefaultBody extends JPanel{
	
	JLabel my_welcome_label;
	
	public DefaultBody() {
		
		setBackground(Color.BLUE);
		
		my_welcome_label = new JLabel("Welcome to Just BeWeave");
		
		this.add(my_welcome_label);
	}

}
