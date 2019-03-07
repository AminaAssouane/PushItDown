package jeu;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Color;
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
		frame.getContentPane().setBackground(new Color(0, 206, 209));
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Nouveau joueur");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controleur_DebutPartie cd = new Controleur_DebutPartie();
				cd.nouvellePartie();
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(170, 259, 132, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Ancien Joueur");
		btnNewButton_1.setBounds(170, 293, 132, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblPushItDown = new JLabel("Push It Down!");
		lblPushItDown.setForeground(new Color(0, 0, 0));
		lblPushItDown.setFont(new Font("Royal Acidbath", Font.PLAIN, 50));
		lblPushItDown.setBounds(67, 114, 364, 73);
		frame.getContentPane().add(lblPushItDown);
		
		JButton btnNewButton_2 = new JButton("Classement");
		btnNewButton_2.setBounds(170, 327, 132, 23);
		frame.getContentPane().add(btnNewButton_2);

	}
}
