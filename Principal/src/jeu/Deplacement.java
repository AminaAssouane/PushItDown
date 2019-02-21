package jeu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Deplacement implements KeyListener{

	Plateau plat;
	Bille b;
	public Deplacement(Plateau plat, Bille b){
		this.plat = plat;
		this.b = b;
	}
	
	@Override
	// Fonction qui sert à déplacer la bille
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int touche = e.getKeyCode();
		switch (touche){
		case KeyEvent.VK_RIGHT :
			Plateau.Deplacement d = plat.new Deplacement(b, b.getX()+1, b.getY(), b.getZ());
			plat.deplacement(d);
			break;
		case KeyEvent.VK_LEFT :
		    d = plat.new Deplacement(b, b.getX()-1, b.getY(), b.getZ());
			plat.deplacement(d);
			break;
		case KeyEvent.VK_UP :
			d = plat.new Deplacement(b, b.getX(), b.getY()-1, b.getZ());
			plat.deplacement(d);
			break;
		case KeyEvent.VK_DOWN :
			d = plat.new Deplacement(b, b.getX(), b.getY()+1, b.getZ());
            plat.deplacement(d);
			break;
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
