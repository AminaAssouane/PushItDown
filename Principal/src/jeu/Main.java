package jeu;

import java.util.concurrent.TimeUnit;

import javax.swing.*;  

public class Main {
	
	
	public static void main(String args[]) {
//		Sortie s[];
		JFrame f = new JFrame();  
		Bille b = new Bille(200, 200, 1,f);
//		int moves;
		new Grid(3,8,8,f,new Level());
		f.setSize(1000, 600);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		f.setVisible(true);
		
		for(int i = 0;i<8;i++) {
			b.moveright();
//			f.update(jl.getGraphics());
		}
	}

}
