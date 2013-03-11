package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private static final String[] WEAVE_IMAGES = {"Backgrounds/red_weave_pic.jpg", "Backgrounds/chair-weave-pic.jpg", 
	    "Backgrounds/Cool-weave-pic.jpg", "Backgrounds/english_daisy.jpg", 
	    "Backgrounds/sand_dollar.jpg", "Backgrounds/Small-rug-pic.jpg"};
	private FadePanel my_fadePanel;
	
	public DefaultBody() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 25, 15, 25));
		setBackground(Color.GRAY);

		JLabel nameLabel = new JLabel("Welcome to Just BeWeave");
		JLabel locationLabel = new JLabel();
		JLabel helperLabel = new JLabel("Please register if you "
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
		addWeaveImage();
	}

	private void addLabel(JLabel label) {
		Border paddingBorder = BorderFactory.createEmptyBorder(5, 0, 0, 0);
		label.setBorder(paddingBorder);
		this.add(label);
	}
	private void addWeaveImage() {
	    Random rand = new Random(); 
	    int indexR = rand.nextInt(6); 
	    my_fadePanel = new FadePanel(createBackgroundImage(WEAVE_IMAGES[indexR]), 0);
	    //BackgroundPanel weaveBack = new BackgroundPanel(createBackgroundImage(WEAVE_IMAGES[indexR]), 0);
	    this.add(my_fadePanel);
	    my_fadePanel.startFadeTimer(WEAVE_IMAGES);
	}
	public FadePanel getFadePanel() {
	    return my_fadePanel;
	}
    /**
     * Creates an Image from a file to be used as a background on a panel.
     * 
     * @return Returns Image to be used as a background for a panel.
     */
    private Image createBackgroundImage(String the_filename) {
        /*Image variable_image = null;
        try {
            final File file = new File(the_filename);
            variable_image = ImageIO.read(file);
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }*/
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(the_filename));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        return img;
    }
}
