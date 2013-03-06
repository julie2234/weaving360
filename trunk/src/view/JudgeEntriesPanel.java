package view;

import java.awt.Color;
import java.awt.Font;
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
			
			JLabel awardlink = new JLabel("Set Awards");
			awardlink.setForeground(Color.blue);
			
			final Font originalfont = awardlink.getFont();
			
			awardlink.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					/*TODO*/
				}

				@SuppressWarnings({ "rawtypes", "unchecked" })
				@Override
				public void mouseEntered(MouseEvent arg0) {
					Map attributes = originalfont.getAttributes();
					attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
					arg0.getComponent().setFont(originalfont.deriveFont(attributes));
					
				}
				@Override
				public void mouseExited(MouseEvent arg0) {
					arg0.getComponent().setFont(originalfont);
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					// Do nothing	
				}
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Do nothing	
				}
			});
			
			add(new JLabel(" "));
			add(awardlink);

		}
		
		//No entries for the category.
		else if(entries.size() == 0){
			
			this.add(new JLabel("No entries have been submitted in the " + catname +
					" category."));
			
		}
	}

}
