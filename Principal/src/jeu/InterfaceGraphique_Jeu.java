package jeu;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;  

public class InterfaceGraphique_Jeu implements Ecouteur_Tour, Ecouteur_DebutPartie  {
	private Controleur_Tour controleurtour = new Controleur_Tour();
	
	@Override
	
	public void nouvellePartie(){
		JFrame f = new JFrame(); 
		f.setBounds(100, 100, 450, 300);
		
		JLayeredPane pa = new JLayeredPane();
		pa.setPreferredSize(new Dimension(500, 500));
		Level l = new Level();
		int niveau = 3;
		
		pa.setFocusable(true);
		
		Bille b = new Bille(0, 0, l.getZ(niveau)-1, pa);
		Plateau plat = new Plateau(pa,l,niveau,b);

		pa.addKeyListener(new Deplacement(plat,b));
		
		f.setSize(500, 500);
		f.getContentPane().setBackground(new Color(0, 206, 209));
		f.setLocationRelativeTo(null);
		f.getContentPane().add(pa, BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		f.setVisible(true);
	}
	
	public void tour() {
		int n = 0;
		
		// ton interface graphique qui modifie n avec les choix dans jeu
		// un bouton qui modifie n a -1 pour passer le tour
		// un bouton qui modifie n a -2 pour abandonner la partie
		// un bouton qui modifie n en fonction des choix dans la liste
		// Jeu.choixactuel.get(int n)
		controleurtour.choisir(n);
	}

	@Override
	public void fintour() {
		// Lire le plateau de jeu et afficher les informations actualisees

	}

	@Override
	public void creerJoueur() {
		// TODO Auto-generated method stub

	}

	@Override
	public void choisirCarte() {
		// TODO Auto-generated method stub

	}

	@Override
	public void choisirRegle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void creerBille() {
		// TODO Auto-generated method stub

	}
	
	
}
