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
	private JButton _submitButton;
	private Person _person;
	private EntryRepository _entrepo;
	private boolean _canSubmit;
	
	public EntrantHomeBody(Controls controller, Person aperson, EntryRepository entrepo) {
		
		setBackground(Color.GRAY);
		setLayout(new GridLayout(0, 2));
		_controller = controller;
		_person = aperson;
		_entrepo = entrepo;
		
		_canSubmit = true;
		try {
			if(_entrepo.getByPersonEMail(_person.getEMail()).size() >= 3) {
				_canSubmit = false;			
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		_panelTitle = new JLabel(_person.getFirstName() + "'s Contest Entries");
		_panelTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
		
		this.add(_panelTitle);
		this.add(new JLabel(" "));

		try {
			if(_entrepo.getByPersonEMail(_person.getEMail()).size() == 0) {
				JLabel noentrylabel = new JLabel("You have not submitted any entries.");
				this.add(noentrylabel);
			} else {
				
				JButton ent1 = new JButton("Entry #1: " + 
						_entrepo.getByPersonEMail(_person.getEMail()).get(0).getTitle());
				JButton rem1 = new JButton("Remove");
				
				ent1.addActionListener(new ActionListener() {
				    public void actionPerformed(final ActionEvent the_event) {
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
				
				rem1.addActionListener(new ActionListener() {
				    public void actionPerformed(final ActionEvent the_event2) {
				        try {
							_controller.removeEntry(_entrepo.getByPersonEMail(_person.getEMail()).get(0));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }});
				
				this.add(ent1);
				this.add(rem1);
				
				if (_entrepo.getByPersonEMail(_person.getEMail()).size() > 1){
					
					JButton ent2 = new JButton("Entry #2: " + 
							_entrepo.getByPersonEMail(_person.getEMail()).get(1).getTitle());
					JButton rem2 = new JButton("Remove");
					
					ent2.addActionListener(new ActionListener() {
					    public void actionPerformed(final ActionEvent the_event2) {
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
					
					rem2.addActionListener(new ActionListener() {
					    public void actionPerformed(final ActionEvent the_event2) {
					        try {
								_controller.removeEntry(_entrepo.getByPersonEMail(_person.getEMail()).get(1));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					    }});
					
					this.add(ent2);
					this.add(rem2);
					
					if(_entrepo.getByPersonEMail(_person.getEMail()).size() > 2){
						
						JButton ent3 = new JButton("Entry #3: " + 
								_entrepo.getByPersonEMail(_person.getEMail()).get(2).getTitle());
						JButton rem3 = new JButton("Remove");
						
						ent3.addActionListener(new ActionListener() {
						    public void actionPerformed(final ActionEvent the_event3) {
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
						
						rem3.addActionListener(new ActionListener() {
						    public void actionPerformed(final ActionEvent the_event2) {
						        try {
									_controller.removeEntry(_entrepo.getByPersonEMail(_person.getEMail()).get(2));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						    }});
						
						this.add(ent3);
						this.add(rem3);
						
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

		_submitButton = new JButton("Submit A New Entry");
		
		_submitButton.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent the_event) {
		        _controller.inputEntry(null, _canSubmit);
		    }});

		this.add(new JLabel(" "));
		this.add(_submitButton);
		
	}
}
