package jeu;

import java.util.ArrayList;
import javax.swing.JLayeredPane;

public class Plateau {

    private Cellule[][][] plateau;
    // private ArrayList<Cellule> cellule_departs = new ArrayList<Cellule>();
    int x, y, z, niv;
    private JLayeredPane pa;
    private Level l;
    private boolean editeur = false;
    Joueur actuel;
    private ArrayList<Deplacement_mem> deplist = new ArrayList<Deplacement_mem>();

    public Plateau(JLayeredPane pa, Level l, int niv, Bille b) {
        this.actuel = new Joueur("Test");
        this.niv = niv;
        this.l = l;
        this.x = l.getX(niv);
        this.y = l.getY(niv);
        this.z = l.getZ(niv);
        this.pa = pa;
        plateau = new Cellule[x][y][z];
        int numbloc = 0;
        int middle = 300; //=120
        int down = 300; //= 240
        for (int k = 0; k < z; k++) {
            for (int j = 0; j < y; j++) {
                for (int i = 0; i < x; i++) {
                    plateau[i][j][k] = new Cellule(i, j, k, l.niveau(niv, numbloc), l.arrivee(niv, numbloc), numbloc, pa);
                    plateau[i][j][k].jl.setBounds(middle + (20 * i) - (20 * j), down + (i * 10) + (10 * j) - (20 * k), 40, 40);
                    numbloc++;
                    pa.add(plateau[i][j][k].jl, numbloc, 1);
                }
            }
        }

        int indBille = z - 1;
        while (plateau[0][0][indBille].jl.getName().equals("vide")) {
            indBille--;
        }

        plateau[0][0][indBille].setEntite(b);
    }

    public Cellule[][][] getPlateau() {
        return this.plateau;
    }

    /* Supprime tout le plateau (pour afficher un nouveau plateau quand on passe au niveau supérieur)*/
    public void efface() {
        for (int k = 0; k < this.z; k++) {
            for (int j = 0; j < this.y; j++) {
                for (int i = 0; i < this.x; i++) {
                    if (plateau[i][j][k] != null) {
                        pa.remove(plateau[i][j][k].jl);
                    }
                }
            }
        }
    }

    /* Verifie si une case est dans les bornes du tableau */
    private boolean dansletableau(int x, int y, int z) {
        return (x < plateau.length && x > -1 && y < plateau[x].length && y > -1 && z < plateau[x][y].length && z > -1);
    }

    // retourne une hauteur possible pour un déplacement, plus basse que la
    // coordonnée z fournie en paramètre
    private int test_hauteur(int x, int y, int z) {
        if (dansletableau(x, y, z)) {
            while (this.plateau[x][y][z].jl.getName().equals("vide") && z > 0) {
                z--;
            }
            if (!this.plateau[x][y][z].jl.getName().equals("vide")) {
                if (this.plateau[x][y][z].entite == null) {
                    return z;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /* Test si la case est la case d'arrivée */
    protected boolean test_final(Entite j) {
        InterfaceJeu.actualiservue();
        return (plateau[j.getX()][j.getY()][j.getZ()].arrivee);
    }

    /* Supprime la bille j du champ entité de la case du plateau */
    protected boolean supprimer_entite(Entite j) {
        if (j == null) {
            return false;
        }
        if (j instanceof Cellule) {
            if (((Cellule) j).entite != null) {
                return false;
            }
        }
        /* On retire pour cela les coordonnées de l'entité j */
        plateau[j.getX()][j.getY()][j.getZ()].entite = null;
        return true;
    }

    /* Fonction de deplacement bloc */
    protected boolean deplacementBloc(Deplacement_mem d, String direction) {
        boolean boolHauteur = false; // Booleen qui sert a g�rer le cas ou le bloc tombe
        int oldZ2 = d.z2; // l'ancienne position du bloc, avant qu'il ne tombe
        /* S'il y a 2 ou plus blocs superpos�s, on retourne faux : impossible de bouger dans cette direction */
        if (dansletableau(d.x2, d.y2, d.z2 + 2) && ((plateau[d.x2][d.y2][d.z2 + 2] != null) && (!plateau[d.x2][d.y2][d.z2 + 2].jl.getName().equals("vide")))) {
            return false;
        }

        /* Si on ne peut pas pousser le bloc car il touche le bord, on retourne faux  */
 /* S'il y a 2 blocs ou plus dans la direction ou l'on veut avancer, on retourne faux : 
    	 * impossible de pousser le bloc dans cette direction */
        switch (direction) {
            case "RIGHT":
                if (!dansletableau(d.x2 + 1, d.y2, d.z2)) {
                    return false;
                }
                if (dansletableau(d.x2 + 1, d.y2, d.z2 + 1) && (!plateau[d.x2 + 1][d.y2][d.z2 + 1].jl.getName().equals("vide"))) { //&& (plateau[d.x2+1][d.y2][d.z2 + 1].jl.getName() != "ii0"))) {
                    return false;
                }

                d.setZ2(test_hauteur(d.x2 + 1, d.y2, d.z2));

                if (dansletableau(d.x2 + 1, d.y2, d.z2 + 1) && !occupe(d.x2 + 1, d.y2, d.z2 + 1)) {
                    if (supprimer_entite(d.entite)) {
                        /* deplacement du bloc */
                        plateau[d.x2 + 1][d.y2][d.z2 + 1].deplacer(d.x2 + 1, d.y2, d.z2 + 1);

                        int a1 = d.x2 + 1;
                        int b1 = d.y2;
                        int c1 = d.z2 + 1;

                        plateau[d.x2][d.y2][oldZ2 + 1].vider(d.x2, d.y2, oldZ2);

                        int a2 = d.x2;
                        int b2 = d.y2;
                        int c2 = oldZ2 + 1;

                        Deplacement_mem me = new Deplacement_mem(null, a1, a2, b1, b2, c1, c2);

                        deplist.add(me);
                        /* deplacement de la bille */
                        plateau[d.x2][d.y2][oldZ2].entite = d.entite;

                        d.entite.deplacer(d.x2, d.y2, oldZ2, plateau[d.x2][d.y2][oldZ2 + 1].numBloc);

                        d.entite.setX(d.x2);
                        d.entite.setY(d.y2);
                        if (d.z2 == -1) {
                            d.z2 = 0;
                        }
                        actuel.addCoup(d.inverse());
                        return test_final(d.entite);
                    }
                }
                return false;
            case "LEFT":
                if (!dansletableau(d.x2 - 1, d.y2, d.z2)) {
                    return false;
                }
                if (dansletableau(d.x2 - 1, d.y2, d.z2 + 1) && (!plateau[d.x2 - 1][d.y2][d.z2 + 1].jl.getName().equals("vide"))) {// && (plateau[d.x2-1][d.y2][d.z2 + 1].jl.getName() != "ii0"))) {
                    return false;
                }

                d.setZ2(test_hauteur(d.x2 - 1, d.y2, d.z2));

                if (dansletableau(d.x2 - 1, d.y2, d.z2 + 1) && !occupe(d.x2 - 1, d.y2, d.z2 + 1)) {
                    if (supprimer_entite(d.entite)) {
                        /* deplacement du bloc */
                        plateau[d.x2 - 1][d.y2][d.z2 + 1].deplacer(d.x2 - 1, d.y2, d.z2 + 1);
                        int a1 = d.x2 - 1;
                        int b1 = d.y2;
                        int c1 = d.z2 + 1;
                        plateau[d.x2][d.y2][oldZ2 + 1].vider(d.x2, d.y2, oldZ2);
                        int a2 = d.x2;
                        int b2 = d.y2;
                        int c2 = oldZ2 + 1;
                        Deplacement_mem me = new Deplacement_mem(null, a1, a2, b1, b2, c1, c2);
                        deplist.add(me);

                        /* deplacement de la bille */
                        plateau[d.x2][d.y2][oldZ2].entite = d.entite;
                        d.entite.deplacer(d.x2, d.y2, oldZ2, plateau[d.x2][d.y2][oldZ2 + 1].numBloc);
                        d.entite.setX(d.x2);
                        d.entite.setY(d.y2);
                        if (d.z2 == -1) {
                            d.z2 = 0;
                        }
                        actuel.addCoup(d.inverse());
                        return test_final(d.entite);
                    }
                }
                return false;
            case "UP":
                if (!dansletableau(d.x2, d.y2 - 1, d.z2)) {
                    return false;
                }
                if (dansletableau(d.x2, d.y2 - 1, d.z2 + 1) && (!plateau[d.x2][d.y2 - 1][d.z2 + 1].jl.getName().equals("vide"))) {// && (plateau[d.x2][d.y2-1][d.z2 + 1].jl.getName() != "ii0"))) {
                    return false;
                }

                d.setZ2(test_hauteur(d.x2, d.y2 - 1, d.z2));

                if (dansletableau(d.x2, d.y2 - 1, d.z2 + 1) && !occupe(d.x2, d.y2 - 1, d.z2 + 1)) {
                    if (supprimer_entite(d.entite)) {
                        /* deplacement du bloc */
                        plateau[d.x2][d.y2 - 1][d.z2 + 1].deplacer(d.x2, d.y2 - 1, d.z2 + 1);

                        int a1 = d.x2;
                        int b1 = d.y2 - 1;
                        int c1 = d.z2 + 1;

                        plateau[d.x2][d.y2][oldZ2 + 1].vider(d.x2, d.y2, oldZ2);

                        int a2 = d.x2;
                        int b2 = d.y2;
                        int c2 = oldZ2 + 1;

                        Deplacement_mem me = new Deplacement_mem(null, a1, a2, b1, b2, c1, c2);

                        deplist.add(me);

                        /* deplacement de la bille */
                        plateau[d.x2][d.y2][oldZ2].entite = d.entite;
                        d.entite.deplacer(d.x2, d.y2, oldZ2, plateau[d.x2][d.y2][oldZ2 + 1].numBloc);
                        d.entite.setX(d.x2);
                        d.entite.setY(d.y2);
                        if (d.z2 == -1) {
                            d.z2 = 0;
                        }

                        actuel.addCoup(d.inverse());
                        return test_final(d.entite);
                    }
                }
                return false;
            case "DOWN":
                if (!dansletableau(d.x2, d.y2 + 1, d.z2)) {
                    return false;
                }
                if (dansletableau(d.x2, d.y2 + 1, d.z2 + 1) && (!plateau[d.x2][d.y2 + 1][d.z2 + 1].jl.getName().equals("vide"))) {// && (plateau[d.x2][d.y2+1][d.z2 + 1].jl.getName() != "ii0"))) {
                    return false;
                }

                d.setZ2(test_hauteur(d.x2, d.y2 + 1, d.z2));

                if (dansletableau(d.x2, d.y2 + 1, d.z2 + 1) && !occupe(d.x2, d.y2 + 1, d.z2 + 1)) {
                    if (supprimer_entite(d.entite)) {
                        /* deplacement du bloc */

                        plateau[d.x2][d.y2 + 1][d.z2 + 1].deplacer(d.x2, d.y2 + 1, d.z2 + 1);
                        int a1 = d.x2;
                        int b1 = d.y2 + 1;
                        int c1 = d.z2 + 1;
                        plateau[d.x2][d.y2][oldZ2 + 1].vider(d.x2, d.y2, oldZ2);
                        int a2 = d.x2;
                        int b2 = d.y2;
                        int c2 = oldZ2 + 1;
                        plateau[a2][b2][c2].jl.setName("vide");
                        Deplacement_mem me = new Deplacement_mem(null, a1, a2, b1, b2, c1, c2);

                        deplist.add(me);

                        /* deplacement de la bille */
                        plateau[d.x2][d.y2][oldZ2].entite = d.entite;
                        d.entite.deplacer(d.x2, d.y2, oldZ2, plateau[d.x2][d.y2][oldZ2 + 1].numBloc);
                        d.entite.setX(d.x2);
                        d.entite.setY(d.y2);
                        d.entite.setZ(oldZ2);
                        if (d.z2 == -1) {
                            d.z2 = 0;
                        }

                        actuel.addCoup(d.inverse());

                        return test_final(d.entite);
                    }
                }
                return false;
            default:
                return false;
        }
    }

    protected boolean deplacement(Deplacement_mem d, String direction) {
        if (d == null) {
            return false;
        }

        /* Verifie s'il y a un bloc qui barre le passage, si oui on essaye de le bouger */
        if (dansletableau(d.x2, d.y2, d.z2 + 1) && (!plateau[d.x2][d.y2][d.z2 + 1].jl.getName().equals("vide"))) {// && (plateau[d.x2][d.y2][d.z2 + 1].jl.getName() != "ii0"))) {        	
            return deplacementBloc(d, direction);
        }

        /* Quand la boule tombe : on change d.z2 */
        d.setZ2(test_hauteur(d.x2, d.y2, d.z2));

        if ((d.z2 != -1) && dansletableau(d.x2, d.y2, d.z2) && !occupe(d.x2, d.y2, d.z2)) {
            if (supprimer_entite(d.entite)) {
                actuel.addCoup(d.inverse());
                plateau[d.x2][d.y2][d.z2].entite = d.entite;
                d.entite.deplacer(d.x2, d.y2, d.z2, plateau[d.x2][d.y2][d.z2 + 1].numBloc);
                d.entite.setX(d.x2);
                d.entite.setY(d.y2);
                d.entite.setZ(d.z2);

                return test_final(d.entite);
            }
        }
        return false;
    }

    /* Fonction qui vérifie s'il y a une bille dans la case (x,y,z) */
    private boolean occupe(int x, int y, int z) {
        return (dansletableau(x, y, z) && (plateau[x][y][z].entite != null));
    }

    public boolean occupe(Deplacement_mem dpm) {
        return occupe(dpm.x2, dpm.y2, dpm.z2);
    }

    public boolean retour(int n) {
        return coup_arriere(n);
    }

    private boolean coup_arriere(int n) {
        if (n > actuel.getList().size()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            Deplacement_mem dm = actuel.getList().get(actuel.getList().size() - 1 - i);
            if (!plateau[dm.x2][dm.y2][dm.z2 + 1].jl.getName().equals("vide")) {
                actuel.getList().remove(actuel.getList().size() - 1);
                if (actuel.getList().size() > 0) {
                    Deplacement_mem d = actuel.getList().get(actuel.getList().size() - 1 - i);
                    d.x1 = dm.x1;
                    d.y1 = dm.y1;
                    d.z1 = dm.z1;
                    return coup_arriere(n);
                }
            }

            if (plateau[dm.x2][dm.y2][dm.z2].jl.getName().equals("vide")) {
                actuel.getList().remove(actuel.getList().size() - 1 - i);
                if (actuel.getList().size() > 0) {
                    Deplacement_mem d = actuel.getList().get(actuel.getList().size() - 1 - i);
                    d.x1 = dm.x1;
                    d.y1 = dm.y1;
                    d.z1 = dm.z1;
                    return coup_arriere(n);
                }

            }

            if (!deplacementr(dm, dm.x1, dm.y1, dm.z1, dm.x2, dm.y2, dm.z2)) {
                return false;
            }
            actuel.getList().remove(actuel.getList().size() - 1);
        }
        return true;
    }

    private boolean deplacementr(Deplacement_mem m, int x0, int y0, int z0, int x, int y, int z) {
        //suppression entitée case d'origine
        if ((m.z2 != -1) && dansletableau(m.x2, m.y2, m.z2) && !occupe(m.x2, m.y2, m.z2)) {
            if (supprimer_entite(m.entite)) {
                plateau[m.x2][m.y2][m.z2].entite = m.entite;
                m.entite.deplacer(m.x2, m.y2, m.z2, plateau[m.x2][m.y2][m.z2 + 1].numBloc);
                m.entite.setX(m.x2);
                m.entite.setY(m.y2);
                m.entite.setZ(m.z2);
                test_final(m.entite);
                return true;
            }
        }
        //ajout case d'arrivée
        return false;
    }

    public JLayeredPane getPanel() {
        return this.pa;
    }

    public Cellule getCellule(int x, int y, int z) {
        return plateau[x][y][z];
    }

    public void afficherl() {
        System.out.println("Liste des coordonnees");
        for (Deplacement_mem m : actuel.getList()) {
            System.out.println(m.x1 + "|" + m.y1 + "|" + m.z1);
            System.out.println(m.x2 + "|" + m.y2 + "|" + m.z2);

        }
    }

    public boolean blocarriere() {
        if (deplist.size() > 0) {
            Deplacement_mem aux = deplist.get(deplist.size() - 1);
            if (plateau[aux.x2][aux.y2][aux.z2].jl.getName().equals("vide") && plateau[aux.x2][aux.y2][aux.z2 - 1].entite == null
                    && plateau[aux.x1][aux.y1][aux.z1].entite == null) {

                plateau[aux.x2][aux.y2][aux.z2].deplacer(aux.x2, aux.y2, aux.z2);
                plateau[aux.x1][aux.y1][aux.z1].vider(aux.x1, aux.y1, aux.z1);
                deplist.remove(deplist.size() - 1);
                InterfaceJeu.actualiservue();
                return true;
            }
        }
        return false;
    }

    private void clear() {
        actuel.getList().clear();
    }

    protected class Cellule extends Bloc implements Entite {

        private int x;
        private int y;
        private int z;
        private boolean depart;
        private boolean arrivee;
        Entite entite;

        public Cellule(int x, int y, int z, Byte b, boolean bool, int numBloc, JLayeredPane pa) {
            super(b, pa, numBloc); // Appele le constructeur Bloc(Byte i)
            this.x = x;
            this.y = y;
            this.z = z;
            this.entite = null;
            this.arrivee = bool;
        }

        public boolean occupe() {
            return this.entite != null;
        }

        public boolean isArrivee() {
            return arrivee;
        }

        public boolean isDepart() {
            return depart;
        }

        public void deplacer(int x, int y, int z) {
            super.deplacer(x, y, z);
            InterfaceJeu.actualiservue();
        }

        /**
         * Getters and Setters *
         */
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

        @Override
        public void setX(int x) {
            this.x = x;
        }

        @Override
        public void setY(int y) {
            this.y = y;
        }

        @Override
        public void setZ(int z) {
            this.z = z;
        }

        public void setArrivee(boolean arrivee) {
            this.arrivee = arrivee;
        }

        public void setDepart(boolean depart) {
            this.depart = depart;
        }

        public void setEntite(Entite e) {
            this.entite = e;
        }

    }

    protected class Deplacement_mem {

        private int x1;
        private int x2;
        private int y1;
        private int y2;
        private int z1;
        private int z2;
        private Entite entite;

        public Deplacement_mem(Entite entite, int x1, int x2, int y1, int y2, int z1, int z2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.z1 = z1;
            this.z2 = z2;
            this.entite = entite;
        }

        public Deplacement_mem(Entite entite, int x, int y, int z) {
            x1 = entite.getX();
            y1 = entite.getY();
            z1 = entite.getZ();
            x2 = x;
            y2 = y;
            z2 = z;
            this.entite = entite;
        }

        public Deplacement_mem inverse() {
            return new Deplacement_mem(entite, x2, x1, y2, y1, z2, z1);
        }

        public Entite getEntite() {
            return entite;
        }

        // GETTERS AND SETTERS 
        public void setZ2(int z) {
            this.z2 = z;
        }
    }

    // GETTERS AND SETTERS
}
