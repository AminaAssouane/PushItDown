package jeu;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Help {
	
	private JFrame frame;
	private Accueil a;
	
	public Help() {
		initialize();
		frame.setVisible(true);
	}
	
	public Help(Accueil a) {
		initialize();
		this.a = a;
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 500);
		frame.getContentPane().setBackground(new Color(240, 230, 140));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel lblUtilisezLesFlches = new JLabel("\u2022  Utilisez les fleches du clavier pour vous deplacer.\r\n\r\n");
		lblUtilisezLesFlches.setBounds(65, 173, 406, 37);
		frame.getContentPane().add(lblUtilisezLesFlches);
		
		JLabel lblNewLabel = new JLabel("\u2022  Le deplacement n'est autorise que s'il n'y a aucun obstacle dans");
		lblNewLabel.setBounds(65, 203, 471, 23);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u2022 Vous gagnez la partie lorsque vous arrivez a la case verte.");
		lblNewLabel_1.setBounds(65, 333, 408, 45);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("la direction choisie.");
		lblNewLabel_2.setBounds(65, 221, 210, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Help ! ");
		lblNewLabel_3.setIcon(new ImageIcon("images\\help2.png"));
		lblNewLabel_3.setFont(new Font("Royal Acidbath", Font.BOLD, 45));
		lblNewLabel_3.setBounds(161, 68, 176, 69);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				a.getFrame().setVisible(true);
			}
		});
		btnRetour.setBounds(192, 389, 89, 23);
		frame.getContentPane().add(btnRetour);
		
		JLabel lblVousPouvez = new JLabel("\u2022 Vous pouvez pousser les blocs pour vous frayer un chemin !");
		lblVousPouvez.setBounds(65, 241, 408, 32);
		frame.getContentPane().add(lblVousPouvez);
		
		JLabel lblIlY = new JLabel("\u2022 Il y a deux boutons pour revenir en arri\u00E8re : un pour la bille et un pour les blocs.");
		lblIlY.setBounds(65, 272, 408, 32);
		frame.getContentPane().add(lblIlY);
		
		JLabel lblSiVous = new JLabel("\u2022 Si vous etes definitivement bloque, cliquez sur recommencer.");
		lblSiVous.setBounds(65, 307, 408, 32);
		frame.getContentPane().add(lblSiVous);
	}
	
	// GETTERS AND SETTERS
	
	public JFrame getFrame(){
			return this.frame;
	}

}
