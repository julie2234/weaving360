package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Entry;
import controller.Controls;

/**
 * Panel for viewing an entry, containing option for editing the entry.
 * 
 * @author Julie Impola
 *
 */
public class ViewEntryBody extends JPanel {

	private static final long serialVersionUID = -2333946566654518734L;
	
	private Controls _controller;
	private Entry _entry;
	private JLabel _panelTitle;
	private JLabel _entryTitle;
	private JLabel _materials;
	private JLabel _techniques;
	private JLabel _description;
	private JLabel _category;
	private JButton _editButton;
	private JButton _deleteButton;
	private JLabel _draft;
	
	/**
	 * Constructs the panel.
	 * 
	 * @param the_controller The controller that created this panel.
	 * @param the_entry The entry that is being viewed.
	 */
	public ViewEntryBody(Controls controller, final Entry entry) {
		//setLayout(new GridLayout(0, 2));
		setOpaque(false);
		_controller = controller;
		_entry = entry;
		try {
            makeElements();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        addElements();
	}
	
	/**
	 * Initializes the GUI elements.
	 * @throws IOException 
	 */
	private void makeElements() throws IOException {
		_panelTitle = new JLabel("Entry Summary");
		_panelTitle.setFont(new Font("SanSerif", Font.PLAIN, 20));
		_entryTitle = new JLabel("Title: " + _entry.getTitle());
		_materials = new JLabel("Materials: " + _entry.getMaterials());
		_techniques = new JLabel("Techniques: " + _entry.getTechniques());
		_description = new JLabel("Description: " + _entry.getDescription());
		_category = new JLabel("Category: " + _entry.getCategoryName());
		_editButton = new JButton("Edit");
		_editButton.addActionListener(new ActionListener() {
	        @Override
            public void actionPerformed(final ActionEvent the_event) {
	          _controller.inputEntry(_entry, true);
	        }
	      });		
		_deleteButton = new JButton("Delete");
		_deleteButton.addActionListener(new ActionListener() {
	        @Override
            public void actionPerformed(final ActionEvent the_event) {
	          _controller.removeEntry(_entry);
	        }
	      });
		
	      InputStream in = new ByteArrayInputStream(_entry.getImage());
	        final BufferedImage image;
	            image = ImageIO.read(in);
	        ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
	        
	        _draft = new JLabel(icon);
	        final JPanel thispanel = this;
	        _draft.addMouseListener(new MouseAdapter() {

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
	                dialog.pack();
	                dialog.setMinimumSize(dialog.getSize());
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true); 
	            }

	        });
	}
	
	/**
	 * Adds the GUI elements to the panel.
	 * @throws IOException 
	 */	
	private void addElements() {
	  this.setBorder(BorderFactory.createEmptyBorder(5, 30, 0, 0));
	  this.setLayout(new BorderLayout());
	  JPanel left = new JPanel();
	  left.setOpaque(false);
	  left.setLayout(new GridLayout(0, 1));
	  left.add(_panelTitle);
	  left.add(_entryTitle);
	  left.add(_materials);
	  left.add(_techniques);
	  left.add(_description);
	  left.add(_category);
	  this.add(_draft, BorderLayout.CENTER);
		JPanel buttons = new JPanel();
		buttons.setOpaque(false);
		buttons.add(_editButton);
		buttons.add(_deleteButton);
		this.add(buttons, BorderLayout.SOUTH);
		this.add(left, BorderLayout.WEST);
	}
}
