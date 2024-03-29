/**
 * 
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Person;
import controller.Controls;

/**
 * WeaveGUI contains main JFrame for whole application. It provides methods to
 * alter its head and body panels.
 * 
 * @author Matt Adams
 * @version 1.0
 */
public class WeaveGUI {
    private static final String MAIN_BACKGROUND = "Backgrounds/weaveBackground.jpg";
    private static final String FOOTER = "Backgrounds/weaveFooterBg.png";
    private static final String HEADER = "Backgrounds/weaveNavigationBg.png";
    private static final String BODY = "Backgrounds/weaveMainContentBg.png";
    private static final String HOME = "Backgrounds/weaveHeaderImage.png";
    private static final Dimension FRAME_DIM = new Dimension(700, 650);
    private Controls my_controller;
    private JFrame my_frame;
    private JPanel my_header;
    private JPanel my_body;
    private JButton my_mainHome;
    private BackgroundPanel my_footer;
    private BackgroundPanel my_main_back;
    private BackgroundPanel my_body_back;

    /**
     * Constructs main frame and sets controller reference.
     * 
     * @param controller Reference to controller.
     */
    public WeaveGUI(Controls controller) {
        my_controller = controller;
        my_main_back = new BackgroundPanel(createBackgroundImage(MAIN_BACKGROUND), 0);
        my_frame = new JFrame("Just BeWeave");
        buildButtons();
    }

    /**
     * Creates view displayed at application launch.
     */
    public void createView() {
        JPanel mainBody = setupBodyBack(); // sets up gridBagLayout
        my_header = new HeaderPanel(my_controller);
        my_body = new DefaultBody();
        my_body_back.add(my_body);
        my_main_back.add(my_header, BorderLayout.NORTH);
        my_main_back.add(mainBody, BorderLayout.CENTER);
        my_frame.add(my_main_back);
        my_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        my_frame.setPreferredSize(FRAME_DIM);
        my_frame.pack();
        my_frame.setLocationRelativeTo(null);
        my_frame.setVisible(true);
        my_frame.setMinimumSize(my_frame.getSize());
        //Adds Dimming affect
        my_frame.getRootPane().setGlassPane(new JComponent() {
            /** Serialized ID*/
            private static final long serialVersionUID = -1638652874019126124L;

            public void paintComponent(Graphics g) {
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        });
    }

    /**
     * Sets body of this frame to the provided JPanel.
     * 
     * @param panel JPanel to be displayed.
     */
    public void setBody(JPanel panel) {
        if (my_body_back.getComponent(0) instanceof DefaultBody && !(panel instanceof DefaultBody)) {
            DefaultBody deBody = (DefaultBody) my_body_back.getComponent(0);
            deBody.getFadePanel().endFadeTimer();
        }
        my_body_back.removeAll();
        my_body = panel;
        my_body_back.add(my_body);
        my_frame.pack();
    }

    /**
     * Sets header of this frame to the provided JPanel
     * 
     * @param control Control reference.
     * @param person Person object for name display.
     */
    public void setHeader(Controls control, Person person) {
        my_main_back.remove(my_header);
        my_header = new HeaderPanel(control, person);
        my_main_back.add(my_header, BorderLayout.NORTH);
        my_frame.pack();
    }

    /**
     * Sets header of this frame to default header JPanel.
     * 
     * @param control Control reference.
     */
    public void setDefaultHeader(Controls control) {
        my_main_back.remove(my_header);
        my_header = new HeaderPanel(control);
        my_main_back.add(my_header, BorderLayout.NORTH);
        my_frame.pack();
    }

    /**
     * Displays warning message for a bad login attempt.
     */
    public void badLogin() {
        JOptionPane.showMessageDialog(my_frame, "Invalid Username and Password",
                                      "Input Error", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Displays warning message for repository error, then closes the program.
     * 
     * @param error Error message to be displayed.
     */
    public void showError(String error) {
        JOptionPane.showMessageDialog(my_frame, error, "Repository Error",
                                      JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Creates an Image from a file to be used as a background on a panel.
     * 
     * @return Returns Image to be used as a background for a panel.
     */
    private Image createBackgroundImage(String the_filename) {
        Image variable_image = null;
        try {
            final File file = new File(the_filename);
            variable_image = ImageIO.read(file);
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(my_frame, e.getMessage());
        }
        return variable_image;
    }

    private JPanel setupBodyBack() {
        my_body_back = new BackgroundPanel(createBackgroundImage(BODY), 0);
        my_body_back.setOpaque(false);
        BackgroundPanel body_header = new BackgroundPanel(createBackgroundImage(HEADER), 0);
        my_footer = new BackgroundPanel(createBackgroundImage(FOOTER), 0);
        body_header.setOpaque(false);
        my_footer.setOpaque(false);
        body_header.setPreferredSize(new Dimension(400, 20));
        my_footer.setPreferredSize(new Dimension(400, 20));
        my_body_back.setPreferredSize(new Dimension(500, 320));
        JPanel mainBody = new JPanel();
        mainBody.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 15;
        mainBody.add(body_header, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 1;
        mainBody.add(my_body_back, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.ipady = 75;
        mainBody.add(my_footer, c);
        setupFooter();
        return mainBody;
    }

    private void setupFooter() {
        my_footer.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JPanel homePanel = new JPanel();
        BackgroundPanel homeBack = new BackgroundPanel(createBackgroundImage(HOME), 0);
        homePanel.add(my_mainHome);
        homeBack.add(homePanel);     
        my_footer.add(homeBack, c);
        //homeBack.setVisible(true);
    }

    private void buildButtons() {
        my_mainHome = new JButton("Home");
        my_mainHome.setForeground(Color.WHITE);
        my_mainHome.setMargin(new Insets(4, 8, 4, 8));
        my_mainHome.setContentAreaFilled(false);
        my_mainHome.setBorderPainted(false);
        my_mainHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent the_event) {
                my_controller.selectUserHome();
            }
        });
    }
    public BackgroundPanel getMainBack() {
        return my_main_back;
    }
    public JFrame getFrame() {
        return my_frame;
    }
}
