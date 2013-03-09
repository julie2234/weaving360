package view;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
		
		    GridBagConstraints c = new GridBagConstraints();
		    c.fill = GridBagConstraints.HORIZONTAL;
		    this.setLayout(new GridBagLayout());
		    c.ipady = 10;
		    c.ipadx = 20;
		    
		    BufferedImage bufImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
	        paint(bufImage.createGraphics());
	        
	        BufferedImage image = null;
	        
	        try {
                image = new Robot().createScreenCapture(new Rectangle(1000, 1000, 50, 50));
            } catch (HeadlessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (AWTException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

	        ImageIcon icon = new ImageIcon(image);
		    
		    for(int i = 0; i < entries.size(); i++) {

		        c.gridx = 0;
		        c.gridy = i;
		    
		        this.add(new JLabel(entries.get(i).getTitle()),c);
		        
		        c.gridx = 1;
		        c.gridy = i;
		        
		        JLabel test = new JLabel(icon);
		        
		        this.add(test, c);

		    }

		}
		
		//No entries for the category.
		else if(entries.size() == 0){
			
			this.add(new JLabel("No entries have been submitted in the " + catname +
					" category."));
			
		}
	}

}
