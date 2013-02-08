package JamesGUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

public class ButtonClickListener extends Observable
	implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println(arg0);
		
		setChanged();
		notifyObservers();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

		
	
}
