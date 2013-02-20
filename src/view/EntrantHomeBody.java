package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controls;

import java.awt.Font;
import java.util.List;

import model.Entry;
import model.Person;

/**
 * Panel for entrant's "home" page, containing options for viewing current
 * entries or submitting a new entry.
 * 
 * @author
 * 
 */
public class EntrantHomeBody extends JPanel {

	private static final long serialVersionUID = 3100197849452034428L;

	public EntrantHomeBody(final Controls controller, Person person,
			final List<Entry> entries, boolean allowNewEntry) {

		setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(CENTER_ALIGNMENT);

		if (entries.size() > 0) {
			JLabel panelTitle = new JLabel("Contest Entries for "
					+ person.getFirstName());
			panelTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
			panelTitle.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 0));
			this.add(panelTitle);

			for (int i = 0; i < entries.size(); i++) {
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
					public void actionPerformed(final ActionEvent the_event) {
						controller.inputEntry(entry);
					}
				});

				rem.addActionListener(new ActionListener() {
					public void actionPerformed(final ActionEvent the_event2) {
						controller.removeEntry(entry);
					}
				});

				panel.add(ent, BorderLayout.EAST);
				panel.add(rem, BorderLayout.EAST);
				this.add(panel);
			}
		} else {
			JLabel noentrylabel = new JLabel(
					"Thanks for registering as an attendee. If you want to "
							+ "enter a contest, click the button below.");
			this.add(noentrylabel);
		}

		if (allowNewEntry) {
			JButton submitButton = new JButton("Submit A New Entry");

			submitButton.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent the_event) {
					controller.inputEntry(null);
				}
			});
			this.add(submitButton);
		} else {
			this.add(new JLabel("You cannot submit another entry "
					+ "because you have already submitted the maximum "
					+ "number of entries."));
		}
	}
}