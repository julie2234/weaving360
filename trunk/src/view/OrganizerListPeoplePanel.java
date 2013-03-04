package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import repository.PersonRepository;

import model.Entry;
import model.Person;
import model.Role;

import controller.Controls;

/**
 * Creates a JPanel to display all people in a specific category.
 * 
 * @author James Marquardt
 *
 */
public class OrganizerListPeoplePanel extends JPanel {

	
	private JTable _table;
	

	public OrganizerListPeoplePanel(Controls controller, List<Person> people, Role role) 
			throws ClassNotFoundException, IOException {
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		

		if(people.size() > 0) {
		
			Object[][] data = new Object[people.size()][5];
		
			//Populate array for table data.
			for(int i = 0; i < people.size(); i++) {
				
				data[i][0] = people.get(i).getFirstName();
				data[i][1] = people.get(i).getLastName();
				data[i][2] = people.get(i).getEMail();
				data[i][3] = people.get(i).getEMail();
				data[i][4] = 1;
				
			}
		
			//Array for column titles.
			String[] column = {"First Name", "Last Name", "Email", "Phone Number", "Award"};
		
			DefaultTableModel model = new DefaultTableModel(data, column);
	        _table = new JTable(model);
	        
	        _table.isCellEditable(1, 4);
			//_table = new JTable(data, column);
	        
			this.add(new JLabel("All people currently registered as " + 
				role + "s."));
		
			_table.setEnabled(false);
			_table.setAutoResizeMode(5);
			_table.getTableHeader().setEnabled(false);
				
			this.add(_table.getTableHeader());
			this.add(_table);

		}
		
		//No people for the category.
		else if(people.size() == 0){
			
			this.add(new JLabel("There are no registered " + role + "s yet."));
			
		}
	}

}

