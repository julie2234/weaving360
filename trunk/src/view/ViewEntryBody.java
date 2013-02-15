package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Entry;

import controller.Controls;

/**
 * Panel for viewing an entry, containing option for editing the entry.
 * 
 * @author
 *
 */
public class ViewEntryBody extends JPanel {
	private Controls _controller;
	private Entry _entry;
	private JLabel _panelTitle;
	private JLabel _entryTitle;
	private JLabel _materials;
	private JLabel _techniques;
	private JLabel _description;
	private JLabel _category;
	private JButton _editButton;
	private JButton _homeButton;
	
	public ViewEntryBody(Controls controller, final Entry entry) {
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, 1));
		_controller = controller;
		_entry = entry;
		makeElements();
		addElements();
	}
	
	private void makeElements() {
		_panelTitle = new JLabel("Entry Summary");
		_entryTitle = new JLabel("Title: " + _entry.getTitle());
		_materials = new JLabel("Materials: " + _entry.getMaterials());
		_techniques = new JLabel("Techniques: " + _entry.getTechniques());
		_description = new JLabel("Description: " + _entry.getDescription());
		_category = new JLabel("Category: " + _entry.getCategory());
		_editButton = new JButton("Edit");
		_editButton.addActionListener(new ActionListener() {
	        public void actionPerformed(final ActionEvent the_event) {
	          _controller.inputEntry(_entry);
	        }
	      });		
		_homeButton = new JButton("Home");
		_homeButton.addActionListener(new ActionListener() {
	        public void actionPerformed(final ActionEvent the_event) {
	          _controller.register();
	        }
	      });
	}
	
	private void addElements() {
		this.add(_panelTitle);
		this.add(_entryTitle);
		this.add(_materials);
		this.add(_techniques);
		this.add(_description);
		this.add(_category);
		this.add(_editButton);
		this.add(_homeButton);
	}
}
