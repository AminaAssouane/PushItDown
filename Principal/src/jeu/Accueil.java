package jeu;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Accueil {
	
	private JFrame frame;
	
	public Accueil() {
		initialize();
		frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		

		Controleur_DebutPartie cd = new Controleur_DebutPartie();
		
                
                
		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setIcon(new ImageIcon("images\\nouveaujoueur.png"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cd.nouveauJoueur();
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(200, 322, 281, 62);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnEditeur = new JButton("");
		btnEditeur.setIcon(new ImageIcon("images\\editeurdecarte.png"));
		//btnNewButton_1.setBorderPainted(false);
		btnEditeur.setBackground(Color.BLACK);
		btnEditeur.setBounds(200, 382, 281, 57);
		frame.getContentPane().add(btnEditeur);
		
		JLabel lblPushItDown = new JLabel("");
		lblPushItDown.setIcon(new ImageIcon("images\\pushitdown3.png"));
		lblPushItDown.setForeground(new Color(0, 0, 0));
		lblPushItDown.setFont(new Font("Royal Acidbath", Font.PLAIN, 50));
		lblPushItDown.setBounds(60, 103, 588, 138);
		frame.getContentPane().add(lblPushItDown);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon("images\\quitter.png"));
		btnNewButton_2.setBackground(Color.BLACK);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_2.setBounds(200, 497, 281, 57);
		frame.getContentPane().add(btnNewButton_2);
		
		Help h = new Help(this);
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setIcon(new ImageIcon("images\\help.png"));
		btnNewButton_3.setBackground(Color.BLACK);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				h.getFrame().setVisible(true);
			}
		});
		btnNewButton_3.setBounds(200, 437, 281, 64);
		frame.getContentPane().add(btnNewButton_3);
	}
	
	// GETTERS AND SETTERS
	
	public JFrame getFrame(){
		return this.frame;
	}
}
