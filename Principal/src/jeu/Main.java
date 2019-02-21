package jeu;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;  

public class Main {
	
	
	public static void main(String args[]) {
//		Sortie s[];
		JFrame f = new JFrame(); 
		f.setBounds(100, 100, 450, 300);
		
		JLayeredPane pa = new JLayeredPane();
		pa.setPreferredSize(new Dimension(500, 500));
			
		Bille b = new Bille(200, 78, 1, pa);

      // Dans la fonction Grid on a qu'à indiqué le niveau (ici le niveau est 1, et il n'y a que 2 niveaux pour 
      // l'instant.
      // Les niveaux sont stockés dans la classe "Level", et sont choisi à partir de la fonction "niveau"
      // L'utilisateur n'a pas besoin de spécifié les coordonnées x, y et z du niveau, celles si sont donné par la
      // classe "Level"
      
		new Grid(pa,new Level(),1);
		f.setSize(500, 500); 
		
		f.getContentPane().add(pa, BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		f.setVisible(true);
		
		/*for(int i = 0;i<8;i++) {
			b.moveright();
//			f.update(jl.getGraphics());
		}*/
	}

}