package JamesGUI;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginHeader extends JPanel {
	
	JButton my_login_button;
	
	public LoginHeader(ButtonClickListener the_listener) {
		
		setBackground(Color.WHITE);

		add(new JLabel("This is the login header"));
		
		my_login_button = new JButton("Submit");
		my_login_button.addMouseListener(the_listener);
		
		add(my_login_button);
		
		setVisible(true);
		
	}

}
