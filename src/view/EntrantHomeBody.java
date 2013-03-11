package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Controls;

import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import model.Entry;
import model.Person;
import model.Role;

/**
 * Panel for entrant's "home" page, containing options for viewing current
 * entries or submitting a new entry.
 * 
 * @author
 * 
 */
public class EntrantHomeBody extends JPanel {

	private static final long serialVersionUID = 3100197849452034428L;

	private static long DEF_BUTTON_WIDTH = 150;
	
	private static long DEF_BUTTON_HEIGHT = 50;
	
	private Dimension _defaultButtonDim;
	
	public EntrantHomeBody(final Controls controller, Person person,
		final List<Entry> entries, boolean allowNewEntry) throws IOException {

		_defaultButtonDim = new Dimension();
		_defaultButtonDim.setSize(DEF_BUTTON_WIDTH, DEF_BUTTON_HEIGHT);
		
		setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		
		if (entries.size() > 0) {
		    
		    
		    JLabel panelTitle = new JLabel("Contest Entries for "
                    + person.getFirstName());
            panelTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            panelTitle.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 0));
            this.add(panelTitle);
            
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(0, 2));
            panel.setAlignmentX(LEFT_ALIGNMENT);
            panel.setOpaque(false);
		    
	            for(int i = 0; i < entries.size(); i++) {
	                JPanel square = new JPanel();
	                square.setOpaque(false);
	                JLabel entrybutton = new JLabel(entries.get(i).getTitle());
	               
	                entrybutton.setForeground(Color.blue);
	                final Entry entrylink = entries.get(i);
	                
	                final Font originalfont = entrybutton.getFont();
	                
	                entrybutton.addMouseListener(new MouseListener() {

	                    @Override
	                    public void mouseClicked(MouseEvent arg0) {
	                        controller.viewEntry(entrylink);
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
	                
	                square.add(entrybutton);

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
	                
	                square.add(test);
	                panel.add(square);
	            }
	            this.add(panel);
		    
		    
		    
			

			/*for (int i = 0; i < entries.size(); i++) {
				final Entry entry = entries.get(i);
				int entryNumber = i + 1;

				JPanel panel = new JPanel();
				panel.setAlignmentX(LEFT_ALIGNMENT);
				panel.setOpaque(false);
				panel.add(
						new JLabel("Entry #" + entryNumber + ": "
								+ entry.getTitle()), BorderLayout.CENTER);

				JButton ent = new JButton("Edit Entry #" + entryNumber);
				JButton rem = new JButton("Remove Entry #" + entryNumber);

				ent.addActionListener(new ActionListener() {
					@Override
                    public void actionPerformed(final ActionEvent the_event) {
						controller.inputEntry(entry);
					}
				});

				rem.addActionListener(new ActionListener() {
					@Override
                    public void actionPerformed(final ActionEvent the_event2) {
						controller.removeEntry(entry);
					}
				});

				panel.add(ent, BorderLayout.EAST);
				panel.add(rem, BorderLayout.EAST);
				this.add(panel);
			}*/
		} else {
			JLabel noentrylabel1 = new JLabel(
					"Thanks for registering as an attendee.");
			JLabel noentrylabel2 = new JLabel(
					"If you want to enter a contest, click the button below.");
				
			this.add(noentrylabel1);
			this.add(noentrylabel2);
		

		}       
		if (allowNewEntry) {
			JButton submitButton = new JButton("Submit A New Entry");
			submitButton.setMaximumSize(_defaultButtonDim);
			
			submitButton.addActionListener(new ActionListener() {
				@Override
                public void actionPerformed(final ActionEvent the_event) {
					controller.inputEntry(null, false);
				}
			});
			this.add(submitButton);
		
		} else {
			this.add(new JLabel("You have submitted the maximum number of entries."));
		}
		
		JButton judgesetbutton = new JButton("Register as a Judge");
		judgesetbutton.setMaximumSize(_defaultButtonDim);
		
		judgesetbutton.addActionListener(new ActionListener() {
			
			@Override
            public void actionPerformed(final ActionEvent the_event) {
				
				Object[] options = {"OK", "Cancel"};
				
				int pane = JOptionPane.showOptionDialog(null, "Are you sure you want to register " +
						"as a judge?\nAll entries will be deleted.", "Warning",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
						null, options, options[0]);
				
				if(pane == 0) {
					
					for(Entry e : entries) {		
						controller.removeEntry(e);
					}
					
					controller.changeRole(Role.Judge);
					
				}
			}
		});
		
		this.add(judgesetbutton);
	}
}