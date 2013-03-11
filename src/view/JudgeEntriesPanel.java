package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	private JScrollPane _scroll;
	private JPanel _panel;
	
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
		
	    this.setLayout(new BorderLayout());
	    this.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		JLabel title = new JLabel(catname + " Entries");
		title.setFont(new Font("SansSerif", Font.PLAIN, 20));
	    this.add(title, BorderLayout.NORTH);
	    
		//Panel on which entries will be put.
		_panel = new JPanel();
		_panel.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		if(entries.size() > 0) {
		
		    GridBagConstraints c = new GridBagConstraints();
		    c.fill = GridBagConstraints.HORIZONTAL;
		    _panel.setLayout(new GridBagLayout());
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
		        
		        
		        
		        _panel.add(entrybutton, c);
		        
		        c.gridx = 1;
		        c.gridy = i;
		       
		        InputStream in = new ByteArrayInputStream(entries.get(i).getImage());
	            final BufferedImage image = ImageIO.read(in);
		        
	            ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
	            
		        JLabel test = new JLabel(icon);
		        
		        final JPanel thispanel = this;
		        
		        test.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent arg0) {

                        JDialog dialog = new JDialog();
                        ImageIcon bigicon = new ImageIcon(image.getScaledInstance(600, 600, java.awt.Image.SCALE_SMOOTH));
                        JLabel imagelabel = new JLabel(bigicon);
                        dialog.add(imagelabel);

                        WindowListener exitListener = new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                thispanel.setEnabled(true);
                                e.getWindow().dispose();
                            }
                        };
                        
                        dialog.addWindowListener(exitListener);
                        dialog.setVisible(true); 
                        dialog.pack();
                        dialog.setMinimumSize(dialog.getSize());
                    }

                });
		        
		        _panel.add(test, c);

		    }
		    
		    //Scrollpanel to wrap _panel in.
		    _scroll = new JScrollPane(_panel);
		    _scroll.setPreferredSize(new Dimension(400, 300));
		    _scroll.getViewport().setOpaque(false);
		    _scroll.setOpaque(false);
		    _panel.setOpaque(false);
		    _scroll.setBorder(BorderFactory.createEmptyBorder());

		    this.add(_scroll);

		}
		
		//No entries for the category.
		else if(entries.size() == 0){
			this.add(new JLabel("No entries have been submitted in the " + catname +
					" category."));
			
		}
	}

}
