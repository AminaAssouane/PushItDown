package jeu;

public class Controleur_DebutPartie implements Ecouteur_DebutPartie {
	InterfaceJeu ij = new InterfaceJeu();


	public void nouveauJoueur(){
		ij.nouveauJoueur();
	}
		
	public void help(){
		Help h = new Help();
	}
	

}
