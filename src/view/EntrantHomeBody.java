package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controls;

/**
 * Panel for entrant's "home" page, containing options for viewing current entries or submitting a new entry.
 * 
 * @author
 *
 */
public class EntrantHomeBody extends JPanel {
	private Controls _controller;
	private JLabel _panelTitle;
	private JLabel _entry1;
	private JLabel _entry2;
	private JLabel _entry3;
	private JButton _submitButton;
	
	public EntrantHomeBody(Controls controller) {
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, 1));
		_controller = controller;
		_panelTitle = new JLabel("Entrant Home Page");
		_entry1 = new JLabel("Some sort of link to view Entry 1");
		_entry2 = new JLabel("Some sort of link to view Entry 2");
		_entry3 = new JLabel("Some sort of link to view Entry 3");
		_submitButton = new JButton("Submit A New Entry");
		_submitButton.addActionListener(new ActionListener() {
	        public void actionPerformed(final ActionEvent the_event) {
	          _controller.inputEntry(null);
	        }
	      });
		this.add(_panelTitle);
		this.add(_entry1);
		this.add(_entry2);
		this.add(_entry3);
		this.add(_submitButton);
	}
}
