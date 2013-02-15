package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Entry;

import controller.Controls;

/**
 * Panel for filling out and submitting an entry.
 * 
 * @author
 *
 */
public class InputEntryBody extends JPanel {
	private Controls _controller;
	private Entry _entry;
	private JLabel _panelTitle;
	private JLabel _entryTitleLabel;
	private JTextField _entryTitleField;
	private JLabel _materialsLabel;
	private JTextField _materialsField;
	private JLabel _techniquesLabel;
	private JTextField _techniquesField;
	private JLabel _descriptionLabel;
	private JTextField _descriptionField;
	private JLabel _categoryLabel;
	private JComboBox _categoryDropdown;
	private JButton _submitButton;
	
	public InputEntryBody(Controls controller, Entry entry) {
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, 1));
		_controller = controller;
		_entry = entry;
		makeElements();
		addElements();
	}
	
	private void makeElements() {
		_panelTitle = new JLabel("Submit an Entry");
		_entryTitleLabel = new JLabel("Title");
		_materialsLabel = new JLabel("Materials");
		_techniquesLabel = new JLabel("Techniques");
		_descriptionLabel = new JLabel("Description");
		_categoryLabel = new JLabel("Select a category:");
		_categoryDropdown = new JComboBox();
		_categoryDropdown.addItem("Category1");
		_categoryDropdown.addItem("Category2");
		_categoryDropdown.addItem("Category3");
		_submitButton = new JButton("Submit");
		if (_entry == null) {
			makeNewEntry();
		} else {
			editEntry();
		}	
	}
	
	private void makeNewEntry() {
		_entryTitleField = new JTextField(20);
		_materialsField = new JTextField(20);
		_techniquesField = new JTextField(20);
		_descriptionField = new JTextField(20);
		_submitButton.addActionListener(new ActionListener() {
	        public void actionPerformed(final ActionEvent the_event) {
	        	Entry entry = new Entry();
	        	entry.setTitle(_entryTitleField.getText());
	        	entry.setMaterials(_materialsField.getText());
	        	entry.setTechniques(_techniquesField.getText());
	        	entry.setDescription(_descriptionField.getText());
	        	entry.setCategory((String) _categoryDropdown.getSelectedItem());
	        	entry.setDateSubmitted(new Date());
	        	try {
					_controller.submitEntry(entry);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	      });
	}
	
	private void editEntry() {
		_entryTitleField = new JTextField(_entry.getTitle(), 20);
		_materialsField = new JTextField(_entry.getMaterials(),20);
		_techniquesField = new JTextField(_entry.getTechniques(),20);
		_descriptionField = new JTextField(_entry.getDescription(),20);
		_categoryDropdown.setSelectedItem(_entry.getCategory());
		_submitButton.addActionListener(new ActionListener() {
	        public void actionPerformed(final ActionEvent the_event) {
	        	_entry.setTitle(_entryTitleField.getText());
	        	_entry.setMaterials(_materialsField.getText());
	        	_entry.setTechniques(_techniquesField.getText());
	        	_entry.setDescription(_descriptionField.getText());
	        	_entry.setCategory((String) _categoryDropdown.getSelectedItem());
	        	try {
					_controller.editEntry(_entry);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	      });	
	}
	
	private void addElements() {
		this.add(_panelTitle);
		this.add(_entryTitleLabel);
		this.add(_entryTitleField);
		this.add(_materialsLabel);
		this.add(_materialsField);
		this.add(_techniquesLabel);
		this.add(_techniquesField);
		this.add(_descriptionLabel);
		this.add(_descriptionField);
		this.add(_categoryLabel);
		this.add(_categoryDropdown);
		this.add(_submitButton);
	}
}