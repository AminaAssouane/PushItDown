package jeu;

public class Controleur_DebutPartie implements Ecouteur_DebutPartie {
	InterfaceGraphique_Jeu ij = new InterfaceGraphique_Jeu();
	
	public void creerJoueur(){ }

	public void choisirCarte(){ }

	public void choisirRegle(){ }

	public void creerBille(){ }

	public void nouvellePartie(){
		ij.nouvellePartie();
	}
	
	public void help(){
		Help h = new Help();
	}
	

}
