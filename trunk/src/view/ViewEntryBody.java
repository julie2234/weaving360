package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Entry;
import controller.Controls;

/**
 * Panel for viewing an entry, containing option for editing the entry.
 * 
 * @author Julie Impola
 *
 */
public class ViewEntryBody extends JPanel {

	private static final long serialVersionUID = -2333946566654518734L;
	
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

	/**
	 * Constructs the panel.
	 * 
	 * @param the_controller The controller that created this panel.
	 * @param the_entry The entry that is being viewed.
	 */
	public ViewEntryBody(Controls controller, final Entry entry) {
		setLayout(new GridLayout(0, 1));
		_controller = controller;
		_entry = entry;
		makeElements();
		addElements();
	}
	
	/**
	 * Initializes the GUI elements.
	 */
	private void makeElements() {
		_panelTitle = new JLabel("Entry Summary");
		_panelTitle.setFont(new Font("SanSerif", Font.PLAIN, 20));
		_entryTitle = new JLabel("Title: " + _entry.getTitle());
		_materials = new JLabel("Materials: " + _entry.getMaterials());
		_techniques = new JLabel("Techniques: " + _entry.getTechniques());
		_description = new JLabel("Description: " + _entry.getDescription());
		_category = new JLabel("Category: " + _entry.getCategoryName());
		_editButton = new JButton("Edit");
		_editButton.addActionListener(new ActionListener() {
	        public void actionPerformed(final ActionEvent the_event) {
	          _controller.inputEntry(_entry);
	        }
	      });		
		_homeButton = new JButton("Home");
		_homeButton.addActionListener(new ActionListener() {
	        public void actionPerformed(final ActionEvent the_event) {
	          _controller.home();
	        }
	      });
	}
	
	/**
	 * Adds the GUI elements to the panel.
	 */	
	private void addElements() {
		this.add(_panelTitle);
		this.add(_entryTitle);
		this.add(_materials);
		this.add(_techniques);
		this.add(_description);
		this.add(_category);
		JPanel buttons = new JPanel();
		buttons.add(_editButton);
		buttons.add(_homeButton);
		this.add(buttons);
	}
}
