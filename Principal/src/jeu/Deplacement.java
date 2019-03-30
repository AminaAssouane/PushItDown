package jeu;

import java.awt.event.*;
import javax.swing.*;

public class Deplacement implements KeyListener{

	Plateau plat;
	Bille b;
	JButton btnNext;
	JLabel feux = new JLabel(new ImageIcon("blue.gif"));
	JLabel feux2 = new JLabel(new ImageIcon("pink.gif"));
	public Deplacement(Plateau plat, Bille b, JButton btn){
		this.plat = plat;
		this.b = b;
		this.btnNext = btn;
	}
	
	@Override
	// Fonction qui sert à déplacer la bille
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int touche = e.getKeyCode();
		switch (touche){
		case KeyEvent.VK_RIGHT :
			Plateau.Deplacement_mem d = plat.new Deplacement_mem(b, b.getX()+1, b.getY(), b.getZ());
			boolean o = plat.deplacement(d,"RIGHT");
			if (o) {
				this.gagne();
			}
			break;
		case KeyEvent.VK_LEFT :
		    d = plat.new Deplacement_mem(b, b.getX()-1, b.getY(), b.getZ());
			o = plat.deplacement(d,"LEFT");
			if (o) {
				this.gagne();
			}
			break;
		case KeyEvent.VK_UP :
			d = plat.new Deplacement_mem(b, b.getX(), b.getY()-1, b.getZ());
			o = plat.deplacement(d,"UP");
			if (o) {
				this.gagne();
			}
			break;
		case KeyEvent.VK_DOWN :
			//System.out.println(b.getX()); //+ " " + b.getY() + " " + b.getZ());
			d = plat.new Deplacement_mem(b, b.getX(), b.getY()+1, b.getZ());
            o = plat.deplacement(d,"DOWN");
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
	
	public void efface(){
		this.plat.getPanel().remove(feux);
		this.plat.getPanel().remove(feux2);
	}
	
	public void gagne(){
		/* Feux d'artifices */
		feux.setBounds(0, 0, 500, 500);
		feux2.setBounds(-20, -20, 500, 500);
		this.plat.getPanel().add(feux,1000,1);
		this.plat.getPanel().add(feux2,1000,1);
		/* Le bouton NEXT s'affiche */
		if (btnNext != null) 
			btnNext.setVisible(true);
	}

}
