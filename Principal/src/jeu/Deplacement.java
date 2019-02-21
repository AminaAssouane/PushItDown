package jeu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Deplacement implements KeyListener{

	@Override
	// Fonction qui sert à déplacer la bille
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int touche = e.getKeyCode();
		switch (touche){
		case KeyEvent.VK_LEFT :
			System.out.println("testGauche");
		case KeyEvent.VK_RIGHT :
		case KeyEvent.VK_UP :
		case KeyEvent.VK_DOWN :
			default : 
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
