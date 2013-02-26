package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import repository.CategoryRepository;

import controller.Controls;

import model.Category;
import model.Entry;
import model.Person;

public class JudgeHomePanel extends JPanel{

	private static final long serialVersionUID = -648591165518944631L;
	
	private  List<Category> categories;

	public JudgeHomePanel(final Controls controller, final Person person, 
			CategoryRepository _categoryRepo) throws ClassNotFoundException, IOException {
	
		categories = _categoryRepo.getAll();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(CENTER_ALIGNMENT);

		this.add(new JLabel("You are in Judge Mode."));
		this.add(new JLabel("Select a category of entries to review."));
		this.add(new JLabel(" "));
		
		if (categories.size() > 0) {

			for (int i = 0; i < categories.size(); i++) {

				JPanel panel = new JPanel();
				panel.setAlignmentX(LEFT_ALIGNMENT);
				panel.setOpaque(false);

				JLabel catbutton = new JLabel(categories.get(i).getName() + " Submissions");
				catbutton.setForeground(Color.blue);
				
				catbutton.addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent arg0) {
						JOptionPane.showMessageDialog(null, "Test", "Warning",
								JOptionPane.DEFAULT_OPTION, null);
						
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// Do nothing	
					}
					@Override
					public void mouseExited(MouseEvent arg0) {
						// Do nothing	
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
				/*
				rem.addActionListener(new ActionListener() {
					public void actionPerformed(final ActionEvent the_event2) {
						controller.removeEntry(entry);
					}
				});

				panel.add(ent, BorderLayout.EAST);
				panel.add(rem, BorderLayout.EAST);
			
				this.add(panel);
				*/
				this.add(catbutton);
				this.add(new JLabel(" "));
			}
			
		} else {
			JLabel noentrylabel = new JLabel(
					"No categories have been specified for this contest.");
			
			this.add(noentrylabel);
		
		}
		
	}

}
