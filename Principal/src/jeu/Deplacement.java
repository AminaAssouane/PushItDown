package jeu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Deplacement implements KeyListener{

	Plateau plat;
	Bille b;
	JLabel feux = new JLabel(new ImageIcon("blue.gif"));
	JLabel feux2 = new JLabel(new ImageIcon("pink.gif"));
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
			boolean o = plat.deplacement(d);
			if (o) {
				this.gagne();
			}
			break;
		case KeyEvent.VK_LEFT :
		    d = plat.new Deplacement(b, b.getX()-1, b.getY(), b.getZ());
			o = plat.deplacement(d);
			if (o) {
				this.gagne();
			}
			break;
		case KeyEvent.VK_UP :
			d = plat.new Deplacement(b, b.getX(), b.getY()-1, b.getZ());
			o = plat.deplacement(d);
			if (o) {
				this.gagne();
			}
			break;
		case KeyEvent.VK_DOWN :
			d = plat.new Deplacement(b, b.getX(), b.getY()+1, b.getZ());
            o = plat.deplacement(d);
            if (o) {
				this.gagne();
			}
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
	
	public void gagne(){
		feux.setBounds(0, 0, 500, 500);
		feux2.setBounds(-20, -20, 500, 500);
		this.plat.getPanel().add(feux,1000,1);
		this.plat.getPanel().add(feux2,1000,1);
	}

}
