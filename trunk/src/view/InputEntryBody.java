
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import model.Category;
import model.Entry;
import model.Person;
import controller.Controls;

/**
 * Panel for filling out and submitting an entry.
 * 
 * @author Julie Impola
 * @author Ralph Feltis (minor formatting contributions)
 * 
 */
public class InputEntryBody extends JPanel {

    private static final long serialVersionUID = 2455794714250686692L;

    private Controls _controller;
    private Entry _entry;
    private Person _person;
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

    /**
     * Constructs the panel.
     * 
     * @param the_controller The controller that created this panel.
     * @param the_entry The entry that is being edited (or null if it's a new
     *            entry).
     * @param the_person The person that is logged in.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public InputEntryBody(Controls controller, Entry entry, Person person,
                          List<Category> availableCategories) {
        setLayout(new BoxLayout(this, 1));
        setOpaque(false);
        _controller = controller;
        _entry = entry;
        _person = person;

        _entryTitleField = new JTextField(20);
        _materialsField = new JTextField(20);
        _techniquesField = new JTextField(20);
        _descriptionField = new JTextField(20);
        _categoryDropdown = new JComboBox();
        makeElements(availableCategories);
        addElements();
    }

    /**
     * Initializes the GUI elements.
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void makeElements(List<Category> availableCategories) {
        Border paddingBorder = BorderFactory.createEmptyBorder(0, 0, 0, 7);
        Dimension labelDimension = new Dimension(80, 25);
        _panelTitle = new JLabel("Submit an Entry");
        _panelTitle.setFont(new Font("SanSerif", Font.PLAIN, 20));
        _entryTitleLabel = new JLabel("Title");
        _entryTitleLabel.setPreferredSize(labelDimension);
        _entryTitleLabel.setBorder(paddingBorder);
        _entryTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        _materialsLabel = new JLabel("Materials");
        _materialsLabel.setPreferredSize(labelDimension);
        _materialsLabel.setBorder(paddingBorder);
        _materialsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        _techniquesLabel = new JLabel("Techniques");
        _techniquesLabel.setPreferredSize(labelDimension);
        _techniquesLabel.setBorder(paddingBorder);
        _techniquesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        _descriptionLabel = new JLabel("Description");
        _descriptionLabel.setPreferredSize(labelDimension);
        _descriptionLabel.setBorder(paddingBorder);
        _descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        _categoryLabel = new JLabel("Category");
        _categoryLabel.setPreferredSize(labelDimension);
        _categoryLabel.setBorder(paddingBorder);
        _categoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        _categoryDropdown.setPreferredSize(_descriptionField.getPreferredSize());

        for (Category category : availableCategories) {
            _categoryDropdown.addItem(category.getName());
        }
        _submitButton = new JButton("Submit");
        if (_entry == null) {
            makeNewEntry();
        } else {
            editEntry();
        }
    }

    /**
     * Makes an empty form, for the user to fill out a new entry.
     */
    private void makeNewEntry() {
        _submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent the_event) {
                Entry entry = new Entry();
                entry.setEmail(_person.getEMail());
                entry.setTitle(_entryTitleField.getText());
                entry.setMaterials(_materialsField.getText());
                entry.setTechniques(_techniquesField.getText());
                entry.setDescription(_descriptionField.getText());
                entry.setDateSubmitted(new Date());
                entry.setCategoryName((String) _categoryDropdown.getSelectedItem());
                try {
                    entry.setAward(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                _controller.submitEntry(entry);
            }
        });
    }

    /**
     * Makes a pre-filled form, for the user to edit an entry.
     */
    private void editEntry() {
        _entryTitleField.setText(_entry.getTitle());
        _materialsField.setText(_entry.getMaterials());
        _techniquesField.setText(_entry.getTechniques());
        _descriptionField.setText(_entry.getDescription());
        _categoryDropdown.setSelectedItem(_entry.getCategoryName());
        _submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent the_event) {
                _entry.setTitle(_entryTitleField.getText());
                _entry.setMaterials(_materialsField.getText());
                _entry.setTechniques(_techniquesField.getText());
                _entry.setDescription(_descriptionField.getText());
                _entry.setCategoryName((String) _categoryDropdown.getSelectedItem());
                _controller.editEntry(_entry);
            }
        });
    }

    /**
     * Adds the GUI elements to the panel.
     */
    private void addElements() {
        JPanel row1 = new JPanel();
        row1.add(_panelTitle);
        row1.setOpaque(false);
        this.add(row1);
        JPanel row2 = new JPanel();
        row2.add(_entryTitleLabel);
        row2.add(_entryTitleField);
        row2.setOpaque(false);
        this.add(row2);
        JPanel row3 = new JPanel();
        row3.add(_materialsLabel);
        row3.add(_materialsField);
        row3.setOpaque(false);
        this.add(row3);
        JPanel row4 = new JPanel();
        row4.add(_techniquesLabel);
        row4.add(_techniquesField);
        row4.setOpaque(false);
        this.add(row4);
        JPanel row5 = new JPanel();
        row5.add(_descriptionLabel);
        row5.add(_descriptionField);
        row5.setOpaque(false);
        this.add(row5);
        JPanel row6 = new JPanel();
        row6.add(_categoryLabel);
        row6.add(_categoryDropdown);
        row6.setOpaque(false);
        this.add(row6);
        this.add(_submitButton);
    }
}
