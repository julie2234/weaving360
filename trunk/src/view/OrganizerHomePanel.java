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
import javax.swing.JLabel;
import javax.swing.JPanel;
import repository.CategoryRepository;
import repository.PersonRepository;

import controller.Controls;

import model.Category;
import model.Person;
import model.Role;

public class OrganizerHomePanel extends JPanel{

	public OrganizerHomePanel(final Controls controller) 
			throws ClassNotFoundException, IOException {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		this.add(new JLabel("Welcome, Organizer."));
		this.add(new JLabel(" "));
		
		final Role[] roles = {Role.Attendee, Role.Entrant, Role.Judge};
		for (int i = 0; i < roles.length; i++) {
			JPanel panel = new JPanel();
			panel.setAlignmentX(LEFT_ALIGNMENT);
			panel.setOpaque(false);
				
			JLabel viewButton = new JLabel("View all " + roles[i] + "s");
				viewButton.setForeground(Color.blue);
				final Role role = roles[i];
				final Font originalfont = viewButton.getFont();
				
				viewButton.addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent arg0) {
						controller.organizerViewAll(role);
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
				
				this.add(viewButton);
				this.add(new JLabel(" "));
			}
		
	}

}
