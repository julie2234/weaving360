package view;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Category;

import controller.Controls;

public class JudgeEntriesPanel extends JPanel {

	private static final long serialVersionUID = -7856598394510666055L;
	
	public JudgeEntriesPanel(Controls controller, Category category) {
		
		this.add(new JLabel("Judges by category view for " + category.getName() + "s."));
		
	}

}
