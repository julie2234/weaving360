package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controls;

import java.awt.Font;
import java.io.IOException;

import repository.CategoryRepository;
import repository.EntryRepository;

import model.Person;

/**
 * Panel for entrant's "home" page, containing options for viewing current entries or submitting a new entry.
 * 
 * @author
 *
 */
public class EntrantHomeBody extends JPanel {
	private Controls _controller;
	private JLabel _panelTitle;
	private JLabel _entry1;
	private JLabel _entry2;
	private JLabel _entry3;
	private JButton _submitButton;
	private Person _person;
	private EntryRepository _entrepo;
	
	public EntrantHomeBody(Controls controller, Person aperson, EntryRepository entrepo) {
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, 1));
		_controller = controller;
		_person = aperson;
		_entrepo = entrepo;
		
		_panelTitle = new JLabel(_person.getFirstName() + "'s Contest Entries");
		_panelTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		
		this.add(_panelTitle);
		
		try {
			System.out.println(_entrepo.getByPersonEMail(_person.getEMail()).size());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			if(_entrepo.getByPersonEMail(_person.getEMail()).size() == 0) {
				this.add(new JLabel("You have not submitted any entries."));
			} else {
				
				JButton ent1 = new JButton("Entry #1");
				
				ent1.addActionListener(new ActionListener() {
				    public void actionPerformed(final ActionEvent the_event) {
				      //Sends username and password to controller login on button press.
				        try {
							_controller.editEntry(_entrepo.getByPersonEMail(_person.getEMail()).get(0));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }});
				
				this.add(ent1);
				
				if (_entrepo.getByPersonEMail(_person.getEMail()).size() > 1){
					
					JButton ent2 = new JButton("Entry #2");
					
					ent1.addActionListener(new ActionListener() {
					    public void actionPerformed(final ActionEvent the_event) {
					      //Sends username and password to controller login on button press.
					        try {
								_controller.editEntry(_entrepo.getByPersonEMail(_person.getEMail()).get(1));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					    }});
					
					this.add(ent2);
					
					if(_entrepo.getByPersonEMail(_person.getEMail()).size() > 2){
						
						JButton ent3 = new JButton("Entry #3");
						
						ent1.addActionListener(new ActionListener() {
						    public void actionPerformed(final ActionEvent the_event) {
						      //Sends username and password to controller login on button press.
						        try {
									_controller.editEntry(_entrepo.getByPersonEMail(_person.getEMail()).get(2));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						    }});
						
						this.add(ent3);
						
					}
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//_entry1 = new JLabel("Some sort of link to view Entry 1");
		//_entry2 = new JLabel("Some sort of link to view Entry 2");
		//_entry3 = new JLabel("Some sort of link to view Entry 3");
		
		_submitButton = new JButton("Submit A New Entry");
		
		_submitButton.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent the_event) {
		      //Sends username and password to controller login on button press.
		        _controller.inputEntry(null);
		    }});

		this.add(new JLabel(" "));
		this.add(_submitButton);
		
	}
}
