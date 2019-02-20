package jeu;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;  

public class Main {
	
	
	public static void main(String args[]) {
//		Sortie s[];
		JFrame f = new JFrame(); 
		JPanel p = new JPanel();
		f.setBounds(100, 100, 450, 300);
		p.setLayout(null);
		
		JLayeredPane pa = new JLayeredPane();
		pa.setPreferredSize(new Dimension(500, 500));
			
		Bille b = new Bille(200, 78, 1, pa);
//		int moves;
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