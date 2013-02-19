package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

import model.Person;

import controller.Controls;

/**
 * Panel for filling out and submitting registration.
 * 
 * @author 
 *
 */
public class RegisterBody extends JPanel {
    
    private static final String FIRST = "First Name";
    private static final String LAST = "Last Name";
    private static final String PHONE = "Phone Number";
    private static final String EMAIL = "Email";
    private static final String C_EMAIL = "Confirm Email";
    private static final String PW = "Password";
    private static final String C_PW = "Confirm Password";
    private static final String LABELS[] = {FIRST, LAST, PHONE, EMAIL, C_EMAIL, PW, C_PW};
	private Controls my_control;
	private JLabel my_panelTitle;
	private Map<String, JTextComponent> my_textMap;
	private Map<String, JLabel> my_warnMap;
	private JButton my_regButton;
	
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
        return gridPanel;
	}
	private JButton makeRegButton() {
	    JButton result = new JButton("Register");
	    result.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent the_event) {
                if (validateUser()) {
                    createNewUser();
                    my_control.register();
                }
            }
          });
	    return result;
	}
	private void createNewUser() {
	    Person user = my_control.getPerson();
	    user.setFirstName(my_textMap.get(FIRST).getText());
	    user.setLastName(my_textMap.get(LAST).getText());
	    user.setPhoneNumber(my_textMap.get(PHONE).getText());
	    user.setEMail(my_textMap.get(EMAIL).getText());
	    user.setPassword(((JPasswordField) my_textMap.get(PW)).getPassword().toString());
	}
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
	private void setupGridBag() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 0;
        c.ipady = 40;
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
        c.ipady = 100;
        this.add(new JLabel(), c);    
	}
}