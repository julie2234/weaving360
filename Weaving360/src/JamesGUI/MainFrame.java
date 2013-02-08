package JamesGUI;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

public class MainFrame extends JFrame implements Observer {

	ButtonClickListener my_button_listener;
	
	public MainFrame() {
		
		this.setSize(500, 500);
		
		my_button_listener = new ButtonClickListener();
		
		my_button_listener.addObserver(this);
		
		getContentPane().setLayout(new BorderLayout());
		
		getContentPane().add(new LoginHeader(my_button_listener), 
				BorderLayout.NORTH);
		getContentPane().add(new DefaultBody(), BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//pack();
		setVisible(true);
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		System.out.println("updated");
		
		getContentPane().removeAll();
		getContentPane().add(new LoggedInHeader(), BorderLayout.NORTH);
		getContentPane().add(new DefaultBody(), BorderLayout.CENTER);
		
		setVisible(true);
		
	}
	
}
