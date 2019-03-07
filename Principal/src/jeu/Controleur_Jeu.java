package jeu;

import java.util.ArrayList;

public class Controleur_Jeu implements Ecouteur_Tour, Ecouteur_DebutPartie  {
    private Controleur_Tour controleurtour = new Controleur_Tour();
    private Controleur_DebutPartie debutpartie = new Controleur_DebutPartie();
 
    private Jeu j;
        public Controleur_Jeu (Jeu j){
        this.j = j;
    }
    
    public void tour() {
        int n = 0;
       // ArrayList <Deplacement> deplacement  = this.j.getChoixactuel();
       // int taille = deplacement.size()-1;
        
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

	@Override
	public void nouvellePartie() {
		// TODO Auto-generated method stub
		
	}
}
