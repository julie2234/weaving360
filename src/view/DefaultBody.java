package view;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Initial welcome display.
 * 
 * @author 
 *
 */
public class DefaultBody extends JPanel{

	private JLabel _panelTitle;
	
	public DefaultBody() {
		setBackground(Color.GRAY);
		_panelTitle = new JLabel("Welcome to Just BeWeave");
		this.add(_panelTitle);
	}

}
