package jeu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InterfaceJeu {
	
	private JFrame f = new JFrame(); 
	private Level l = new Level();
	private int niveau = 1;
	private Bille b;
	private Plateau plat;
	private JLayeredPane pa;
	private Deplacement d;
	JButton btnNext;
	
	public void nextLevel(){
		niveau++;
		b.efface();
		d.efface();
		pa.removeKeyListener(d);
		plat.efface();
		b = new Bille(0, 0, l.getZ(niveau)-1, pa);
		plat = new Plateau(pa,l,niveau,b);
		d = new Deplacement(plat,b,btnNext);
		pa.addKeyListener(d);
	}
	
	public void nouvellePartie(){
		f.setBounds(100, 100, 450, 300);
		pa = new JLayeredPane();
		pa.setPreferredSize(new Dimension(500, 500));
		pa.setFocusable(true);

		b = new Bille(0, 0, l.getZ(niveau)-1, pa);
		plat = new Plateau(pa,l,niveau,b);
		d = new Deplacement(plat,b,btnNext);
		pa.addKeyListener(d);
		
		JLabel lblJoueur = new JLabel("Joueur : ");
		lblJoueur.setBackground(new Color(154, 205, 50));
		lblJoueur.setBounds(33, 45, 46, 14);
		pa.add(lblJoueur);
		
		JLabel lblScore = new JLabel("Score :");
		lblScore.setBounds(348, 45, 46, 14);
		pa.add(lblScore);
		
		btnNext = new JButton("NEXT >");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextLevel();
				btnNext.setVisible(false);
			}
		});
		btnNext.setBounds(332, 399, 89, 23);
		pa.add(btnNext);
		
		f.setSize(500, 500);
		f.getContentPane().setBackground(new Color(0, 206, 209));
		f.setLocationRelativeTo(null);
		f.getContentPane().add(pa, BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		f.setVisible(true);
	}
	
}
