package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import repository.PersonRepository;

import model.Entry;

import controller.Controls;

/**
 * Creates a JPanel to display all entries in a specific category.
 * 
 * @author James Marquardt
 */
public class JudgeEntriesPanel extends JPanel {

	private static final long serialVersionUID = -7856598394510666055L;

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
   
		    for(int i = 0; i < entries.size(); i++) {

		        c.gridx = 0;
		        c.gridy = i;
		        
		        JLabel entrybutton = new JLabel(entries.get(i).getTitle());
		       
                entrybutton.setForeground(Color.blue);
                final Entry entrylink = entries.get(i);
                
                final Font originalfont = entrybutton.getFont();
		        
		        entrybutton.addMouseListener(new MouseListener() {

                    @Override
                    public void mouseClicked(MouseEvent arg0) {
                        controller.judgeEntryView(entrylink);
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
		        
		        this.add(entrybutton, c);
		        
		        c.gridx = 1;
		        c.gridy = i;
		       
		        InputStream in = new ByteArrayInputStream(entries.get(i).getImage());
	            BufferedImage image = ImageIO.read(in);
		        
	            ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
	            
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
