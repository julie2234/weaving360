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
	
	private static final int COLUMNS = 6;
	
	private JTable table;
	
	public JudgeEntriesPanel(Controls controller, EntryRepository entryRepo, 
			Category category) throws ClassNotFoundException, IOException {
		
		table = new JTable();
		
		String catname = category.getName();
		
		this.add(new JLabel("Judges by category view for " + 
				catname+ "s."));
		
		//System.out.println(entryRepo.getByCategory(catname));
		
		
	}

}
