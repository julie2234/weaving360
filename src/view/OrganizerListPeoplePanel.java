package view;

import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Person;
import model.Role;

import controller.Controls;

/**
 * Creates a JPanel to list all people registered as a specific role.
 * 
 * @author Julie Impola
 *
 */
public class OrganizerListPeoplePanel extends JPanel {

	private static final long serialVersionUID = 5752502013523728319L;
	private JTable _table;

	public OrganizerListPeoplePanel(Controls controller, List<Person> people, Role role) 
			throws ClassNotFoundException, IOException {
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		if(people.size() > 0) {
			String[] column = {"Last Name", "First Name", "Email", "Phone Number"};
			Object[][] data = new Object[people.size()][column.length];
			for(int i = 0; i < people.size(); i++) {
				data[i][0] = people.get(i).getLastName();
				data[i][1] = people.get(i).getFirstName();
				data[i][2] = people.get(i).getEMail();
				data[i][3] = people.get(i).getPhoneNumber();
			}
			DefaultTableModel model = new DefaultTableModel(data, column);
	        _table = new JTable(model);
			this.add(new JLabel("All people currently registered as " + role + "s."));
			_table.setEnabled(false);
			_table.setAutoResizeMode(5);
			_table.getTableHeader().setEnabled(false);
			this.add(_table.getTableHeader());
			this.add(_table);
		}
		else if(people.size() == 0) {  //No people in that role.
			this.add(new JLabel("There are no registered " + role + "s yet."));
		}
	}

}

