package view;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Entry;
import controller.Controls;

/**
 * Displays details of the entry a judge has selected.
 * 
 * @author James Marquardt
 */
public class JudgeEntryViewPanel extends JPanel{

    private static final long serialVersionUID = -3448210431968692190L;
    private Controls _controller;
    private Entry _entry;
    private JLabel _panelTitle;
    private JLabel _entryTitle;
    private JLabel _materials;
    private JLabel _techniques;
    private JLabel _description;
    private JLabel _category;

    /**
     * Constructs the panel.
     * 
     * @param the_controller The controller that created this panel.
     * @param the_entry The entry that is being viewed.
     */
    public JudgeEntryViewPanel(Controls controller, final Entry entry) {
        setLayout(new GridLayout(0, 1));
        setOpaque(false);
        _controller = controller;
        _entry = entry;
        makeElements();
        addElements();
    }
    
    /**
     * Initializes the GUI elements.
     */
    private void makeElements() {
        _panelTitle = new JLabel("Entry Summary");
        _panelTitle.setFont(new Font("SanSerif", Font.PLAIN, 20));
        _entryTitle = new JLabel("Title: " + _entry.getTitle());
        _materials = new JLabel("Materials: " + _entry.getMaterials());
        _techniques = new JLabel("Techniques: " + _entry.getTechniques());
        _description = new JLabel("Description: " + _entry.getDescription());
        _category = new JLabel("Category: " + _entry.getCategoryName());   
        
    }
    
    /**
     * Adds the GUI elements to the panel.
     */ 
    private void addElements() {
      this.setBorder(BorderFactory.createEmptyBorder(5, 30, 0, 0));
        this.add(_panelTitle);
        this.add(_entryTitle);
        this.add(_materials);
        this.add(_techniques);
        this.add(_description);
        this.add(_category);

    }

}
