package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

import model.Person;
import model.Role;
import controller.Controls;

/**
 * Panel for filling out and submitting a registration form.
 * 
 * @author Matt Adams
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RegisterBody extends JPanel {
    /** First name label.*/
    private static final String FIRST = "First Name";
    /** Last name label.*/
    private static final String LAST = "Last Name";
    /** Phone number label.*/
    private static final String PHONE = "Phone Number";
    /** Email label.*/
    private static final String EMAIL = "Email";
    /** Confirm email label.*/
    private static final String C_EMAIL = "Confirm Email";
    /** Password label.*/
    private static final String PW = "Password";
    /** Confirm Password label.*/
    private static final String C_PW = "Confirm Password";
    /** Array containing all labels for registration.*/
    private static final String LABELS[] = {FIRST, LAST, PHONE, EMAIL, C_EMAIL, PW, C_PW};
    /** Controller reference.*/
    private Controls my_control;
    /** Registration title panel.*/
    private JLabel my_panelTitle;
    /** Maps all labels to a corresponding text field.*/
    private Map<String, JTextComponent> my_textMap;
    /** Maps all labels to a corresponding warning label.*/
    private Map<String, JLabel> my_warnMap;
    /** Button for submitting registration data.*/
    private JButton my_regButton;
	/**
	 * Constructs body panel used for user registration.
	 * @param the_control Reference to application controller.
	 */
	public RegisterBody(final Controls the_control) {
		my_control = the_control;
		my_panelTitle = new JLabel("New User Registration");
		my_panelTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		my_panelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		my_regButton = makeRegButton();
	    setBackground(Color.GRAY);
	    setLayout(new GridBagLayout());
	    createTextMap();
	    setupGridBag();
	}
	/**
	 * Creates two maps: A map from labels to text fields, and a map
	 * from labels to warning labels.
	 */
	private void createTextMap() {
	    my_textMap = new HashMap<String, JTextComponent>();
	    my_warnMap = new HashMap<String, JLabel>();
	    for (int i = 0; i < LABELS.length; i++) {
	        if (i < LABELS.length - 2) {
	            my_textMap.put(LABELS[i], new JTextField());
	        } else {
	            my_textMap.put(LABELS[i], new JPasswordField());
	        }
	        my_warnMap.put(LABELS[i], new JLabel());
	        my_warnMap.get(LABELS[i]).setForeground(Color.RED);
	    }
	    try {
	        MaskFormatter mask = new MaskFormatter("(###)-###-####");
	        mask.setPlaceholderCharacter('_');
	        my_textMap.put(PHONE, new JFormattedTextField(mask));
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	}
	/**
	 * Constructs the main grid panel that displays all the information
	 * for registration.
	 * @return Returns registration JPanel.
	 */
	private JPanel createGridPanel() {
	    JPanel gridPanel = new JPanel();
        GridLayout grid = new GridLayout(LABELS.length, 3);
        grid.setVgap(10);
        grid.setHgap(3);
        gridPanel.setLayout(grid);
        gridPanel.setPreferredSize(gridPanel.getPreferredSize());
        for (int i = 0; i < LABELS.length; i++) {
            JLabel label = new JLabel(LABELS[i]);
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            gridPanel.add(label);
            gridPanel.add(my_textMap.get(LABELS[i]));
            gridPanel.add(my_warnMap.get(LABELS[i]));
        }
        gridPanel.setBackground(Color.WHITE);
        return gridPanel;
	}
	/**
	 * Creates and returns the submit registration button.
	 * @return Returns submit registration button.
	 */
	private JButton makeRegButton() {
	    JButton result = new JButton("Submit");
	    result.setBackground(new Color(128, 128, 128));
	    result.setForeground(Color.WHITE);
	    result.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
                                                            BorderFactory.createEmptyBorder(0, 14, 0, 14)));
	    result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent the_event) {
                if (validateUser()) {
                    registrationComplete();
                    my_control.register(createNewUser());
                }
            }
          });
	    return result;
	}
	/**
	 * Alters the display to show user registration was successful.
	 */
	private void registrationComplete() {
	    my_panelTitle.setText("Registration Complete");
	    for (int i = 0; i < LABELS.length; i++) {
	        my_textMap.get(LABELS[i]).setBackground(Color.LIGHT_GRAY);
	        my_textMap.get(LABELS[i]).setFocusable(false);
	    }
	    my_regButton.setText("Continue");
	    my_regButton.removeAll();
	    my_regButton.addActionListener(new ActionListener() {
	        @Override
            public void actionPerformed(final ActionEvent the_event) {
	            my_control.entrantHome();
	        }
	    });
	}
	/**
	 * Creates and returns a new Person based on the registration date submitted.
	 * @return Returns new Person containing recent registration data.
	 */
	private Person createNewUser() {
	    Person user = new Person();
	    user.setFirstName(my_textMap.get(FIRST).getText());
	    user.setLastName(my_textMap.get(LAST).getText());
	    user.setPhoneNumber(my_textMap.get(PHONE).getText());
	    user.setEMail(my_textMap.get(EMAIL).getText());
	    user.setPassword(new String(((JPasswordField) my_textMap.get(PW)).getPassword()));
	    user.setRole(Role.Attendee);
	    return user;
	}
	/**
	 * Handles the logic that checks if a user has input data correctly into
	 * the registration text fields.
	 * @return Return true if data was input correctly.
	 */
	private boolean validateUser() {
	    boolean result = true;
	    for (int i = 0; i < LABELS.length; i++) {
	        if (my_textMap.get(LABELS[i]).getText().isEmpty()) {
	            result = false;
	            my_textMap.get(LABELS[i]).setBackground(Color.YELLOW);
	            my_warnMap.get(LABELS[i]).setText("Must enter value.");
	        } else if (my_textMap.get(LABELS[i]).getBackground() == Color.YELLOW) {
	            my_textMap.get(LABELS[i]).setBackground(Color.WHITE);
	            my_warnMap.get(LABELS[i]).setText(null);
	        }
	    }
	    if (!((JFormattedTextField) my_textMap.get(PHONE)).isEditValid()) {
	        my_textMap.get(PHONE).setBackground(Color.YELLOW);
	        my_warnMap.get(PHONE).setText("Not valid phone number.");
	        result = false;
	    } else if (my_textMap.get(PHONE).getBackground() == Color.YELLOW) {
	        my_textMap.get(PHONE).setBackground(Color.WHITE);
	        my_warnMap.get(PHONE).setText(null);
	    }
	    final String email = my_textMap.get(EMAIL).getText();
	    final String c_email = my_textMap.get(C_EMAIL).getText();
	    final String pw = new String(((JPasswordField) my_textMap.get(PW)).getPassword());
	    final String c_pw = new String(((JPasswordField) my_textMap.get(C_PW)).getPassword());
	    if (!email.equals(c_email)) {
	        my_warnMap.get(EMAIL).setText("Email mismatch.");
	        my_textMap.get(EMAIL).setBackground(Color.YELLOW);
	        my_textMap.get(C_EMAIL).setBackground(Color.YELLOW);
	        my_textMap.get(EMAIL).setText(null);
	        my_textMap.get(C_EMAIL).setText(null);
	        result = false;
	    }
	    if (!pw.equals(c_pw)) {
            my_warnMap.get(PW).setText("Password mismatch.");
            my_textMap.get(PW).setBackground(Color.YELLOW);
            my_textMap.get(C_PW).setBackground(Color.YELLOW);
	        my_textMap.get(PW).setText(null);
            my_textMap.get(C_PW).setText(null);
            result = false;
	    }
	    return result;
	}
	/**
	 * Sets up overall panel using a GridBagLayout. The main components are 
	 * a title panel, registration grid panel, and submit button.
	 */
	private void setupGridBag() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 0;
        //c.ipady = 40;
        this.add(my_panelTitle, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 150;      //make this component tall
        c.ipadx = 450;      //make this component wide
        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        this.add(createGridPanel(), c);
        c.fill = GridBagConstraints.CENTER;
        c.ipady = 0;       //reset to default
        c.ipadx = 0;
        c.gridwidth = 1;
        c.gridx = 1;       //aligned with title
        c.gridy = 2;
        this.add(my_regButton, c);
        //c.ipady = 100;
        //this.add(new JLabel(), c);    
	}
}