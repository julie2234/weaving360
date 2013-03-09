package view;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * Initial welcome display.
 * 
 * @author
 * 
 */
public class DefaultBody extends JPanel {

	private static final long serialVersionUID = 5158799381621704427L;

	public DefaultBody() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.GRAY);

		JLabel nameLabel = new JLabel("Welcome to Just BeWeave");
		JLabel locationLabel = new JLabel();
		JLabel helperLabel = new JLabel("Plase register if you "
				+ "plan on attending the event or entering a contest.");

		try {
			Properties settings = new Properties();
			settings.load(new FileInputStream(System.getProperty("user.dir")
					+ File.separator + "weaving.cfg"));
			nameLabel.setText(settings.getProperty("EventName"));
			locationLabel.setText(settings.getProperty("EventLocation"));
		} catch (Exception ex) {
		}

		nameLabel.setFont(new Font(UIManager.getFont("TextField.font")
				.getName(), Font.BOLD, 18));
		addLabel(nameLabel);
		addLabel(locationLabel);
		addLabel(helperLabel);
	}

	private void addLabel(JLabel label) {
		Border paddingBorder = BorderFactory.createEmptyBorder(5, 20, 0, 0);
		label.setBorder(paddingBorder);
		this.add(label);
	}
}
