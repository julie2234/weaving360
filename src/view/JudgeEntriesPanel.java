package view;

import java.io.IOException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import repository.PersonRepository;

import model.Entry;

import controller.Controls;

public class JudgeEntriesPanel extends JPanel {

	private static final long serialVersionUID = -7856598394510666055L;
	
	private JTable table;
	
	public JudgeEntriesPanel(Controls controller, List<Entry> entries, String catname,
			PersonRepository personrepo) throws ClassNotFoundException, IOException {
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Object[][] data = new Object[entries.size()][4];
		
		for(int i = 0; i < entries.size(); i++) {
		
			for(Entry e : entries) {
			 
				data[i][3] = personrepo.getByEMail(e.getEmail()).getFirstName() + 
						" " + personrepo.getByEMail(e.getEmail()).getLastName();
				data[i][2] = e.getDescription();
				data[i][1] = e.getDraft();
				data[i][0] = e.getTitle();
					
			}
		}
		
		
		
		String[] column = {"Title", "Draft", "Description", "Contestant"};
		
		table = new JTable(data, column);
		
		this.add(new JLabel("Judges by category view for " + 
				catname+ "s."));
		
		table.setEnabled(false);
		
		table.setAutoResizeMode(5);
		//System.out.println(table.getAutoResizeMode());
		table.getTableHeader().setEnabled(false);
		
		this.add(table.getTableHeader());
		this.add(table);
		
		
		
	}

}
