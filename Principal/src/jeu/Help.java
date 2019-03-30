package jeu;

import java.awt.Color;
import java.awt.EventQueue;

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
		frame.getContentPane().setBackground(new Color(0, 206, 209));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel lblUtilisezLesFlches = new JLabel(" Utilisez les flèches du clavier (_,_,_,_) pour vous déplacer.\r\n\r\n");
		lblUtilisezLesFlches.setBounds(68, 168, 406, 54);
		frame.getContentPane().add(lblUtilisezLesFlches);
		
		JLabel lblNewLabel = new JLabel("Le déplacement n'est autorisé que s'il n'y a aucun obstacle dans");
		lblNewLabel.setBounds(68, 217, 471, 76);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Vous gagnez la partie lorsque vous arrivez à la case verte.");
		lblNewLabel_1.setBounds(68, 309, 408, 45);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("la direction choisie.");
		lblNewLabel_2.setBounds(68, 262, 200, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Help ! ");
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
	}
	
	// GETTERS AND SETTERS
	
	public JFrame getFrame(){
			return this.frame;
	}

}
