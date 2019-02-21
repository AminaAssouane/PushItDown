package jeu;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;  

public class Main {
	
	
	public static void main(String args[]) {
		JFrame f = new JFrame(); 
		f.setBounds(100, 100, 450, 300);
		
		JLayeredPane pa = new JLayeredPane();
		pa.setPreferredSize(new Dimension(500, 500));
		
		Level l = new Level();
		int niveau = 1;
		
		pa.setFocusable(true);
		
		Bille b = new Bille(0, 0, l.getZ(niveau)-1, pa);
		Plateau plat = new Plateau(pa,l,niveau,b);

		pa.addKeyListener(new Deplacement(plat,b));
		f.setSize(500, 500);
		
		
		f.getContentPane().add(pa, BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		f.setVisible(true);
		

	}
}
