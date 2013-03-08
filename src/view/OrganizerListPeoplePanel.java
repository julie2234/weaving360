package view;

import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
			String[] columns = {"Last Name", "First Name", "Email", "Phone Number", "Role"};
			Object[][] data = new Object[people.size()][columns.length];
			for(int i = 0; i < people.size(); i++) {
				data[i][0] = people.get(i).getLastName();
				data[i][1] = people.get(i).getFirstName();
				data[i][2] = people.get(i).getEMail();
				data[i][3] = people.get(i).getPhoneNumber();
				data[i][4] = people.get(i).getRole();
			}
			DefaultTableModel model = new DefaultTableModel(data, columns);
			_table = new JTable(model);
			_table.setAutoCreateRowSorter(true);
			this.add(new JLabel("All people currently registered as " + role + "s."));
			_table.setEnabled(false);
			_table.setAutoResizeMode(4);
			JScrollPane pane = new JScrollPane(_table);
			this.add(pane);
		}
		else if(people.size() == 0) {  //No people in that role.
			this.add(new JLabel("There are no registered " + role + "s yet."));
		}
	}

}

