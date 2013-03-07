package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import repository.PersonRepository;

import model.Entry;

import controller.Controls;

/**
 * Creates a JPanel to display all entries in a specific category.
 * 
 * @author James Marquardt
 *
 */
public class JudgeEntriesPanel extends JPanel {

	private static final long serialVersionUID = -7856598394510666055L;
	
	private JTable _table;
	
	/**
	 * Creates the view for the judge to view entries.
	 * 
	 * @param controller
	 * @param entries List of entries associated with category.
	 * @param catname The name of the category being viewed.
	 * @param personrepo To retrieve Person associated with entry.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public JudgeEntriesPanel(final Controls controller, List<Entry> entries, String catname,
			PersonRepository personrepo) throws ClassNotFoundException, IOException {
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		
		if(entries.size() > 0) {
		
			Object[][] data = new Object[entries.size()][5];
		
			//Populate array for table data.
			for(int i = 0; i < entries.size(); i++) {
				
				data[i][0] = entries.get(i).getTitle();
				data[i][1] = entries.get(i).getDraft();
				data[i][2] = entries.get(i).getDescription();
				data[i][3] = personrepo.getByEMail(entries.get(i).getEmail()).getFirstName() + 
					" " + personrepo.getByEMail(entries.get(i).getEmail()).getLastName();
				if(entries.get(i).getAward() != 0){ 
					data[i][4] = entries.get(i).getAward();
				} else {
					data[i][4] = null;
				}
			}
		
			//Array for column titles.
			String[] column = {"Title", "Draft", "Description", "Contestant", "Award"};
		
			DefaultTableModel model = new DefaultTableModel(data, column);
	        _table = new JTable(model);
	        
	        _table.isCellEditable(1, 4);
			//_table = new JTable(data, column);
	        
			this.add(new JLabel("Judges by category view for " + 
				catname+ "s."));
		
			_table.setEnabled(false);
			_table.setAutoResizeMode(5);
			_table.getTableHeader().setEnabled(false);
				
			this.add(_table.getTableHeader());
			this.add(_table);
			
			setAwardPanel(entries);

		}
		
		//No entries for the category.
		else if(entries.size() == 0){
			
			this.add(new JLabel("No entries have been submitted in the " + catname +
					" category."));
			
		}
	}
	
	private void setAwardPanel(List<Entry> entries) {
		
		JPanel awardspanel = new JPanel();
		awardspanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JComboBox dropdown1 = new JComboBox();
		JComboBox dropdown2 = new JComboBox();
		JComboBox dropdown3 = new JComboBox();
		
		for(Entry e : entries){
			dropdown1.addItem(e.getTitle());
			dropdown2.addItem(e.getTitle());
			dropdown3.addItem(e.getTitle());
		}
			
		JLabel title = new JLabel("Set awards");
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		awardspanel.add(title, c);
		
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		awardspanel.add(new JLabel("1st Place"), c);
		
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		awardspanel.add(dropdown1, c);

		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		awardspanel.add(new JLabel("2nd Place"), c);
		
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		awardspanel.add(dropdown2, c);
	
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		awardspanel.add(new JLabel("3rd Place"), c);
		
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 3;
		awardspanel.add(dropdown3, c);

		
		
		this.add(awardspanel);
		
	}

}
