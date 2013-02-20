package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controls;

import java.awt.Font;
import java.io.IOException;
import java.util.List;

import repository.EntryRepository;

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

		setBackground(Color.GRAY);
		setLayout(new GridLayout(0, 2));

		JLabel panelTitle = new JLabel(person.getFirstName()
				+ "'s Contest Entries");
		panelTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));

		this.add(panelTitle);
		this.add(new JLabel(" "));

		if (entries.size() > 0) {
			for (int i = 0; i < entries.size(); i++) {
				final Entry entry = entries.get(i);
				int entryNumber = i+1;
				JButton ent = new JButton("Entry #" + entryNumber + ": "
						+ entry.getTitle());
				JButton rem = new JButton("Remove #" + entryNumber);

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

				this.add(ent);
				this.add(rem);
			}
		} else {
			JLabel noentrylabel = new JLabel(
					"You have not submitted any entries.");
			this.add(noentrylabel);
		}

		if (allowNewEntry) {
			JButton submitButton = new JButton("Submit A New Entry");

			submitButton.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent the_event) {
					controller.inputEntry(null);
				}
			});

			this.add(new JLabel(" "));
			this.add(submitButton);
		} else {
			this.add(new JLabel("You cannot submit another entry "
					+ "because you have already submitted the maximum "
					+ "number of entries."));
		}
	}
}