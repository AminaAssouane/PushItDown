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

    public Plateau(JLayeredPane pa, Level l, int niv, Bille b) {
        this.niv = niv;
        this.l = l;
        this.x = l.getX(niv);
        this.y = l.getY(niv);
        this.z = l.getZ(niv);
        this.pa = pa;
        plateau = new Cellule[x][y][z];
        int numbloc = 0;
        int middle = 200; //=120
        int down = 200; //= 240

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
                        plateau[d.x2][d.y2][oldZ2 + 1].vider(d.x2, d.y2, oldZ2);
                        Deplacement_mem mem = new Deplacement_mem(plateau[d.x2][d.y2 - 1][d.z2 + 1], d.x2, d.y2, d.z2, d.x2, d.y2 - 1, d.z2 + 1);
                        actuel.addCoup(mem.inverse());

                        /* deplacement de la bille */
                        plateau[d.x2][d.y2][oldZ2].entite = d.entite;
                        d.entite.deplacer(d.x2, d.y2, oldZ2, plateau[d.x2][d.y2][oldZ2 + 1].numBloc);
                        d.entite.setX(d.x2);
                        d.entite.setY(d.y2);
                        d.entite.setZ(oldZ2);
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
                        plateau[d.x2][d.y2][oldZ2 + 1].vider(d.x2, d.y2, oldZ2);
                        Deplacement_mem mem = new Deplacement_mem(plateau[d.x2][d.y2 - 1][d.z2 + 1], d.x2, d.y2, d.z2, d.x2, d.y2 - 1, d.z2 + 1);
                        actuel.addCoup(mem.inverse());
                        /* deplacement de la bille */
                        plateau[d.x2][d.y2][oldZ2].entite = d.entite;
                        d.entite.deplacer(d.x2, d.y2, oldZ2, plateau[d.x2][d.y2][oldZ2 + 1].numBloc);
                        d.entite.setX(d.x2);
                        d.entite.setY(d.y2);
                        d.entite.setZ(oldZ2);
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
                        plateau[d.x2][d.y2][oldZ2 + 1].vider(d.x2, d.y2, oldZ2);

                        Deplacement_mem mem = new Deplacement_mem(plateau[d.x2][d.y2 - 1][d.z2 + 1], d.x2, d.y2, d.z2, d.x2, d.y2 - 1, d.z2 + 1);
                        actuel.addCoup(mem.inverse());
                        /* deplacement de la bille */
                        plateau[d.x2][d.y2][oldZ2].entite = d.entite;
                        d.entite.deplacer(d.x2, d.y2, oldZ2, plateau[d.x2][d.y2][oldZ2 + 1].numBloc);
                        d.entite.setX(d.x2);
                        d.entite.setY(d.y2);
                        d.entite.setZ(oldZ2);
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
                        plateau[d.x2][d.y2][oldZ2 + 1].vider(d.x2, d.y2, oldZ2);
                        Deplacement_mem mem = new Deplacement_mem(plateau[d.x2][d.y2 + 1][d.z2 + 1],
                                d.x2, d.y2, d.z2, d.x2, d.y2 - 1, d.z2 + 1);
                        actuel.addCoup(mem.inverse());

                        /* deplacement de la bille */
                        plateau[d.x2][d.y2][oldZ2].entite = d.entite;
                        d.entite.deplacer(d.x2, d.y2, oldZ2, plateau[d.x2][d.y2][oldZ2 + 1].numBloc);
                        d.entite.setX(d.x2);
                        d.entite.setY(d.y2);
                        d.entite.setZ(oldZ2);
                        actuel.addCoup(d.inverse());
                        return test_final(d.entite);
                    }
                }
                return false;
            default:
                return false;
        }
    }

    /*
    protected Deplacement_mem deplacement(Deplacement_mem d){
        if (d== null) 
            return null;
        
        int dir = direction(d);
        if (dir==10) return null;
        // CAS DEPLACEMENT SPECIAL A TRAITER PLUS TARD
        
        if (estVide(d)) {
            if(plateau[d.x1][d.y1][d.z1].entite instanceof Cellule){
                return null;
            }
            deplacement_effectif(d);
            return d;
        } 
        
        else {
            if (bougeBloc(d)!= -1){
                
            }
        }
        
        return null;
    }
    
    private void deplacement_effectif(Deplacement_mem d){
        this.plateau[d.x1][d.y1][d.z1].entite = null;
        this.plateau[d.x2][d.y2][d.z2].entite = d.entite;
    }
    
    private void deplacement_effectif_bloc(Deplacement_mem d){
        this.plateau[d.x1][d.y1][d.z1].entite=null;
        this.plateau[d.x2][d.y2][d.z2].entite = d.entite;
        this.plateau[d.x2][d.y2][d.z2+1] = (Cellule)d.entite;
    }
    
    private int direction(Deplacement_mem d){
        // UTILISATION
        
        // RETURN 1 == HAUT
        // RETURN 2 == BAS
        // RETURN 3 == DROITE
        // RETURN 4 == GAUCHE
        // RETURN 10 == DIRECTION SPECIALE
        // RETURN 0 == IMMOBILE
        
        if (d.x1 == d.x2 && d.y1 == d.y2){
            return 0;
        }
        
        if (d.x1 == d.x2){
            if (d.y1-d.y2==-1){
                return  1;
            }
            
            if (d.y1 - d.y2 == 1){
            return 2;      
            }
            
            else {
                return 10;
            }
        }
        
        if (d.y1 == d.y2){
            if (d.x1-d.x2==-1){
                return  3;
            }
            
            if (d.x1 - d.x2 == 1){
            return 4;   
            }
            
            else {
                return 10;
            }
        }
        
        return 10;
    }
    
    protected int bougeBloc(Deplacement_mem d){
        if (plateau[d.x2][d.y2][d.z2].entite == null){
            // Il y a une entite derriere le bloc
            return -1;
        }
        
        return test_hauteur2(d.x2,d.y2,d.z2);
    }
    
    protected boolean estVide(Deplacement_mem d){
        if (plateau[d.x2][d.y2][d.z2].entite == null
                && (plateau[d.x2][d.y2][d.z2+1] != null)){
            return true;
        }
        return false;
    }
    
    // pour bloc uniquement.
    private int test_hauteur2(int x, int y, int z) {
    	if (dansletableau(x,y,z)){
            int aux = z;
    		while (this.plateau[x][y][aux] == null && aux > 0){
    			aux--;
    		}
    		
                if (aux == 0){
                    return 0;
                }
                
                if (plateau[x][y][aux].entite != null){
                    return -1;
                }
                
                return aux;
        } 
        else {
            return -1;
        }
    }    
     */
 /* Fonction de deplacement bloc/bille (elle appelle la fonction de deplacement bloc) */
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
                actuel.addCoup(d);
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
        return (dansletableau(x, y, z) && plateau[x][y][z].entite != null);
    }

    public boolean occupe(Deplacement_mem dpm) {
        return occupe(dpm.x2, dpm.y2, dpm.z2);
    }

    public boolean retour(int n) {
        return coup_arriere(n);
    }

    private boolean coup_arriere(int n) {
        if (n >= actuel.getList().size()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            Deplacement_mem dm = actuel.getList().get(actuel.getList().size() - 1 - i);
            if (!deplacementr(dm.x1, dm.y1, dm.z1, dm.x2, dm.y2, dm.z2)) {
                return false;
            }
        }

        return true;
    }

    private boolean deplacementr(int x0, int y0, int z0, int x, int y, int z) {

        if (!dansletableau(x0, y0, z0) || !dansletableau(x, y, z)
                || occupe(x, y, z)) {
            // coordonnées pas dans le tableau OU case d'arrivée occupée
            return false;
        }

        Entite e = plateau[x0][y0][z0].entite;

        if (e instanceof Cellule) {
            if (((Cellule) e).entite != null) {
                return false;
            }
            if (!dansletableau(x, y, z + 1) || plateau[x][y][z + 1] != null) {
                return false;
            }
            plateau[x0][y0][z0 + 1] = null;
            plateau[x][y][z + 1] = (Cellule) e;
        }
        plateau[x0][y0][z0].entite = null;
        //suppression entitée case d'origine
        plateau[x][y][z].entite = e;
        //ajout case d'arrivée
        return true;
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
        private Entite entite;

        public Cellule(int x, int y, int z, Byte b, boolean bool, int numBloc, JLayeredPane pa) {
            super(b, pa, numBloc); // Appele le constructeur Bloc(Byte i)
            this.x = x;
            this.y = y;
            this.z = z;
            this.entite = null;
            this.arrivee = bool;
        }

        public boolean isArrivee() {
            return arrivee;
        }

        public boolean isDepart() {
            return depart;
        }

        public void deplacer(int x, int y, int z) {
            super.deplacer(x, y, z);
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
    public JLayeredPane getPanel() {
        return this.pa;
    }

    public Cellule getCellule(int x, int y, int z) {
        return plateau[x][y][z];
    }

    //* CODE POUR LA CREATION DE PLATEAU, EDITEUR GRAPHIQUE
    /*
        Test s'il y a une cellule en dessous
        puis essaye d'ajouter l'entité si oui
        Uniquement si la cellule d'en dessous est vide !
     */
    protected boolean ajouterEntite(Entite j, int x, int y, int z) {
        if (dansletableau(x, y, z) && plateau[x][y][z] != null && plateau[x][y][z].entite == null) {
            plateau[x][y][z].entite = j;
            return true;
        }
        return false;
    }

    // public void setCellule(int x, int y, int z)
}

// TESTS
/*   protected ArrayList<Deplacement> possible(Entite bille) {
    ArrayList<Deplacement> deplacements = new ArrayList<Deplacement>();
    // on essaye les 4 déplacements possibles
    add(test_deplacement(bille, bille.getX(), bille.getY(), bille.getZ(), true, false, false, false), deplacements);
    add(test_deplacement(bille, bille.getX(), bille.getY(), bille.getZ(), false, true, false, false), deplacements);
    add(test_deplacement(bille, bille.getX(), bille.getY(), bille.getZ(), false, false, true, false), deplacements);
    add(test_deplacement(bille, bille.getX(), bille.getY(), bille.getZ(), false, false, false, true), deplacements);
    return deplacements;
}

private Deplacement test_deplacement(Entite j, int x, int y, int z, boolean d, boolean g, boolean h, boolean b) {
    // permet de deplacer la balle, un bloc ou une entite quelconque
    int x2 = x;
    int y2 = y;

    if (!testbool(d, g, h, b)) {
        return null;
    }

    if (g) {
        x--;
        x2 = x - 1;
        y2 = y;
    }

    if (d) {
        x++;
        x2 = x + 1;
        y2 = y;
    }

    if (h) {
        y++;
        y2 = y + 1;
        x2 = x;
    }

    if (b) {
        y--;
        y2 = y - 1;
        x2 = x;
    }

    if (dansletableau(x, y, z + 1) && plateau[x][y][z] != null) {
        return null;
    }

    if (occupe(x, y, z) && !(occupe(x2, y2, z))) {

        if (plateau[x][y][z].entite == null || !(plateau[x][y][z].entite instanceof Cellule)) {
            return test_deplacement(plateau[x][y][z], x, y, z, d, g, h, b);
        }
        // on peut deplacer un bloc UNIQUEMENT s'il n y a pas un autre bloc au dessus
        // (mais une balle ou une autre entite c'est possible)
        // ajoute a la liste des deplacements possible le fait de pouvoir pousser une
        // entite, un bloc ou autre
    }

    if (dansletableau(x, y, z) && !occupe(x, y, test_hauteur(x, y, z))) {
        return new Deplacement(j, x, y, test_hauteur(x, y, z));
        // sinon, ajoute un deplacement a liste
    }

    return null;
}

private boolean testbool(boolean a, boolean b, boolean c, boolean d) {
    ArrayList<Boolean> test = new ArrayList<Boolean>();
    test.add(a);
    test.add(b);
    test.add(c);
    test.add(d);
    int essai = 0;
    essai = test.stream().filter((aux) -> (aux)).map((_item) -> 1).reduce(essai, Integer::sum);
    return (essai == 1);
}
 */

 /*    protected boolean getDepart(int x, int y, int z) {
return cellule_departs.stream().anyMatch((c) -> (c.z == z && c.x == x && c.y == y));
}

protected boolean addCellule(int x, int y, int z, boolean depart, boolean end) {
if (dansletableau(x, y, z) && plateau[x][y][z] == null) {
    Cellule c = new Cellule(x, y, z,1,false,pa,1);
    if (dansletableau(x, y, z - 1)) {
        if (!ajouterEntite(c, x, y, z - 1)) {
            return false;
        }
    }

    plateau[x][y][z] = c;
    plateau[x][y][z].depart = depart;
    if (depart == true) {
        cellule_departs.add(plateau[x][y][z]);
    }
    plateau[x][y][z].arrivee = end;

    return true;
}
return false;
}




/*   private boolean add(Deplacement d, ArrayList<Deplacement> a) {
if (d == null) {
    return false;
}
return a.add(d);
}*/
