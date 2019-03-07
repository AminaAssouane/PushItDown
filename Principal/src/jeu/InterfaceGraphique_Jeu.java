package jeu;

public class InterfaceGraphique_Jeu implements Ecouteur_Tour, Ecouteur_DebutPartie  {
	private Controleur_Tour controleurtour = new Controleur_Tour();
	private Controleur_DebutPartie debutpartie = new Controleur_DebutPartie();
	@Override
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
