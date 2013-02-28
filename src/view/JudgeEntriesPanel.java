package view;

import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
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
	
	public JudgeEntriesPanel(Controls controller, List<Entry> entries, String catname,
			PersonRepository personrepo) throws ClassNotFoundException, IOException {
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		
		if(entries.size() > 0) {
		
			Object[][] data = new Object[entries.size()][4];
		
			//Populate array for table data.
			for(int i = 0; i < entries.size(); i++) {

				data[i][3] = personrepo.getByEMail(entries.get(i).getEmail()).getFirstName() + 
					" " + personrepo.getByEMail(entries.get(i).getEmail()).getLastName();
				data[i][2] = entries.get(i).getDescription();
				data[i][1] = entries.get(i).getDraft();
				data[i][0] = entries.get(i).getTitle();
					
				
			}
		
			//Array for column titles.
			String[] column = {"Title", "Draft", "Description", "Contestant"};
		
		
			_table = new JTable(data, column);
		
			this.add(new JLabel("Judges by category view for " + 
				catname+ "s."));
		
			_table.setEnabled(false);
			_table.setAutoResizeMode(5);
			_table.getTableHeader().setEnabled(false);
				
			this.add(_table.getTableHeader());
			this.add(_table);

		}
		
		//No entries for the category.
		else if(entries.size() == 0){
			
			this.add(new JLabel("No entries have been submitted in the " + catname +
					" category."));
			
		}
	}

}
