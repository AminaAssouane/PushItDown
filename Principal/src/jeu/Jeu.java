package jeu;

import java.util.ArrayList;
import jeu.Plateau.Deplacement;

//MODELE DU JEU
public class Jeu {
    /* 
    Il y a une fonction ArrayList <Deplacement> possible(Entite Bille) 
    qui applique 4 fois  la fonction test_deplacement (droit gauche haut bas) 
    avec des arguments différents à chaque fois puis qui renvoi une Liste avec 
    tous les déplacements possible. 
    
    La fonction  test_deplacement() prévoit à la fois de se déplacer OU 
    de déplacer un bloc en fonction de la situation 
    et renvoi le déplacement correspondant.

    Dans le programme, j'ai conçu le jeu de manière à ce que possible 
    soit appelé et retourne une liste. Dans l'idée, pour l'affichage 
    et le contrôleur, il n y qu'à récupérer cette liste de déplacements 
    dans la classe Jeu avec l'arraylist choixactuel.
    */
    Ecouteur_Tour EventTour;
    private int nbRetour;
    // Si nombre de retour est négatif, le nombre de retour en arri�re possible est
    // Infini

    private ArrayList<Joueur_actuel> participants = new ArrayList<Joueur_actuel>();
    // Possibilit� d'avoir un jeu multi-joueur

    private ArrayList<Joueur_actuel> fini;
    // liste des joueurs qui ont termine ou abandonne

    private int actuel;
    // Iterateur pour le joueur actuel

    private boolean fin;
    // Indique si la partie est termine
    private boolean encours;
    // Indique si la partie est en cours

    private Plateau plateau;
    // Plateau de jeu

    /*
	 * 
	 * Explication:
	 * La fonction action declenche la fonction TOUR de InterfaceGraphique (qui implemente
	 * l'interface Ecouteur_Tour) l'interface Graphique se debrouille de façon à
	 * avoir plusieurs choix - Un bouton abandonner qui modifie la valeur int choix
	 * à (-2) - Un bouton passer tour qui modifie la valeur int choix à (-1) -
	 * Active le clic pour les cases où l'on peut se déplacer (ArrayList
	 * choixactuel) et les blocs que l'on peut pousser Chaque clic doit correpondre
	 * à l'indice dans la L'ArrayList et doit donc modifier int choix en conséquences
	 * - Qui passera par un controleur, qui modifiera la valeur choix
	 * 
     */
    private int choix;
    private boolean momentdechoisir;
    private ArrayList<Deplacement> choixactuel;

    public boolean getMoment() {
        return momentdechoisir;
    }

    public Jeu(int nbRetour, ArrayList<Bille> bille, Plateau plateau, ArrayList<Joueur> participants, InterfaceGraphique_Jeu Evenement) {
        this.nbRetour = nbRetour;
        this.plateau = plateau;
        this.EventTour = Evenement;
        Evenement.choisirCarte();
        Evenement.creerJoueur();
        Evenement.creerBille();
    }

    protected boolean addActuel(Joueur_actuel j) {
        if (this.participants.contains(j)) {
            return false;
        }
        return this.participants.add(j);
    }

    protected void choisir(int n) {
        this.choix = n;
    }

    public void plateau(Plateau p) {
        this.plateau = p;
    }

    public Bille creer_bille_depart(Joueur j, int x, int y, int z) {
        if (plateau.getDepart(x, y, z) && !encours) {
            return new Bille(x, y, z);
        } else {
            return null;
        }
    }

    public Joueur_actuel choisir_profil_temps(String nom, ArrayList<Bille> billes) {
        if (encours) {
            return null;
        }
        Joueur_actuel j = new Joueur_actuel(nom);
        j.billes = billes;
        return j;
    }

    public Joueur_actuel choisir_profil_existant(Joueur joueur, ArrayList<Bille> billes) {
        if (encours) {
            return null;
        }
        Joueur_actuel j = new Joueur_actuel(joueur.getNom());
        j.billes = billes;
        return j;
    }

    private void init() {
        if (encours == false) {
            encours = true;
        }
        if (participants.isEmpty()) {
            fin();
        } else {
            tour_suivant();
            init();
        }
    }

    private void fin() {
        fini.forEach((j) -> {
            j.score();
        });
    }

    private void tour_suivant() {
        if (participants.size() <= actuel) {
            actuel = 0;
        }
        action(participants.get(actuel));
        actuel++;
    }

    private void action(Joueur_actuel j) {
        ArrayList<Deplacement> d = new ArrayList<Deplacement>();
        j.billes.forEach((b) -> {
            d.addAll(this.plateau.possible(b));
        });
        if (d.isEmpty()) {
            tour_suivant();
        } else {
            EventTour.tour();
            momentdechoisir = true;
            setChoixactuel(d);
            Bille aux = choix_tour(choix, d);
            momentdechoisir = false;

            if (aux != null && plateau.test_final(aux)) {
                j.retirerbille(aux);
                j.multiplicateur++;
            }

            if (j.billes.isEmpty()) {
                j.score();
                participants.remove(j);
                fini.add(j);
            }
            EventTour.fintour();
            tour_suivant();
        }
    }

    private Bille choix_tour(int n, ArrayList<Deplacement> d) {
        if (n == -1) {
            return null;
        }
        if (n == -2) {
            participants.get(actuel).abandon();
            return null;
        } else {
            return choix_action(d.get(n));
        }
    }

    private Bille choix_action(Deplacement d) {
        if (plateau.deplacement(d)) {
            if (d.getEntite() instanceof Bille) {
                return (Bille) d.getEntite();
            } else {
                return null;
            }
        }
        return null;
    }

    public ArrayList<Deplacement> getChoixactuel() {
        return choixactuel;
    }

    public void setChoixactuel(ArrayList<Deplacement> choixactuel) {
        this.choixactuel = choixactuel;
    }

    protected class Joueur_actuel extends Joueur {

        private Joueur_actuel(String nom) {
            super(nom);
        }

        public void score() {
            billes2.forEach((b) -> {
                this.score += b.score;
            });
            score *= multiplicateur;
        }

        public Bille Choixbille(int x) {
            // TODO Auto-generated method stub
            return null;
        }

        public boolean fin() {
            if (billes.isEmpty()) {
                this.score();
                return true;
            }
            return false;
        }

        public void abandon() {
            billes.clear();
            this.fin();
        }

        protected void retirerbille(Bille b) {
            billes.remove(b);
            billes2.add(b);
        }

        private Joueur_actuel(Joueur j) {
            super(j.getNom());
        }

        private int score = 100;
        private int multiplicateur;
        private ArrayList<Bille> billes;
        private ArrayList<Bille> billes2;
    }

    protected class Bille implements Entite {

        private ArrayList<Deplacement> deplacements;
        private int x;
        private int y;
        private int z;
        private int score;

        private Bille(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public void deplacer(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void score(int reduction) {
            score -= reduction;
        }
        // A chaque deplacement, on reduit le score;

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public int getZ() {
            return z;
        }
    }
}
