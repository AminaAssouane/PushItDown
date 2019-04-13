package jeu;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class InterfaceJeu {
	
	private JFrame f = new JFrame(); 
	private Level l = new Level(1);
	private int niveau = 1;
	private Bille b;
	private Plateau plat;
	private JLayeredPane pa;
	private Deplacement d;
	JButton btnNext, btnRetour;
	int indBille = 0;
	
	public void nextLevel() throws IOException{
		l.nextlvl();
		niveau++;
		b.efface();
		d.efface();
		pa.removeKeyListener(d);
		plat.efface();
		plat = new Plateau(pa,l,niveau,b);
		
		// On a besoin des 4 lignes suivantes pour mettre la bille dans la position de départ
		indBille = l.getZ(niveau)-1;
		while (plat.getCellule(0,0,indBille).jl.getName().equals("vide"))
			indBille--;
		b = new Bille(0,0,indBille,pa);
		
		d = new Deplacement(plat,b,btnNext);
		pa.addKeyListener(d);
	}
	
	public void nouvellePartie(){
		f.setBounds(100, 100, 450, 300);
		pa = new JLayeredPane();
		pa.setPreferredSize(new Dimension(500, 500));
		pa.setFocusable(true);
		
		
		plat = new Plateau(pa,l,niveau,b);
		indBille = l.getZ(niveau)-1;
		while (plat.getCellule(0,0,indBille).jl.getName().equals("vide"))
			indBille--;
		b = new Bille(0,0,indBille,pa);
		
		
		d = new Deplacement(plat,b,btnNext);
		pa.addKeyListener(d);
		
		JLabel lblJoueur = new JLabel("Joueur : ");
		lblJoueur.setBackground(new Color(154, 205, 50));
		lblJoueur.setBounds(33, 45, 46, 14);
		pa.add(lblJoueur);
		
		JLabel lblScore = new JLabel("Score :");
		lblScore.setBounds(348, 45, 46, 14);
		pa.add(lblScore);
		
		btnRetour = new JButton("RETOUR >");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				plat.retour(1);
                                pa.requestFocus();
			}
		});
		btnRetour.setBounds(332, 70, 100, 23);
		pa.add(btnRetour);
		
		btnNext = new JButton("NEXT >");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					nextLevel();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
