package view;

import java.io.IOException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import repository.EntryRepository;

import model.Category;
import model.Entry;

import controller.Controls;

public class JudgeEntriesPanel extends JPanel {

	private static final long serialVersionUID = -7856598394510666055L;
	
	private JTable table;
	
	public JudgeEntriesPanel(Controls controller, List<Entry> entries, String catname) throws ClassNotFoundException, IOException {
		
		Object[][] test = {	{"blah", "bleh", "bloh", "blehg"},
							{"meh", "meng", "mong", "blergy"}};
		
		String[] column = {"head1", "head2", "head3", "head4"};
		
		table = new JTable(test, column);
		
		this.add(new JLabel("Judges by category view for " + 
				catname+ "s."));
		
		table.setEnabled(false);
		
		this.add(table.getTableHeader());
		this.add(table);
		
		
		
	}

}
