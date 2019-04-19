package jeu;
import java.util.ArrayList;

public class Editeur {

    private Cellule[][][] plateau;
    private ArrayList<Cellule> listedepart = new ArrayList();
    private ArrayList<Cellule> listearrive = new ArrayList();
    int x, y, z, indice;

    public Etage stage(int x) {
        return new Etage(x);
    }

    public void setIndice(int ind) {
        this.indice = ind;
    }

    public Cellule[][][] getPlateau() {
        return this.plateau;
    }

    public Cellule getC(int x, int y, int z) {
        if (!dansletableau(x, y, z)) {
            return null;
        }
        return this.plateau[x][y][z];
    }

    public Editeur(int x, int y, int z) {
        this.plateau = new Cellule[x][y][z];
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private boolean dansletableau(int x, int y, int z) {
        return (x < plateau.length && x > -1 && y < plateau[x].length && y > -1
                && z < plateau[x][y].length && z > -1);
    }

    public boolean add(int x, int y, int z) {
        if (plateau[x][y][z] == null) {
            plateau[x][y][z] = new Cellule(x, y, z);
            return true;
        }
        return false;
    }

    public boolean adddepart(int x, int y, int z) {

        if (dansletableau(x, y, z)) {
            if (plateau[x][y][z] != null) {
                if (!listearrive.contains(plateau[x][y][z])
                        && !listedepart.contains(plateau[x][y][z])) {
                    listedepart.add(plateau[x][y][z]);
                    plateau[x][y][z].state = 1;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addarrive(int x, int y, int z) {
        if (dansletableau(x, y, z)) {
            if (plateau[x][y][z] != null) {
                if (!listearrive.contains(plateau[x][y][z])
                        && !listedepart.contains(plateau[x][y][z])) {
                    listearrive.add(plateau[x][y][z]);
                    plateau[x][y][z].state = 2;
                    return true;
                }
            }
        }
        return false;
    }

    public void enregister() {
        // completer l'enregistrement
    }

    /**
     *
     */
    protected class Cellule {

        private int state;
        /*
        * state 0 => Normal
        * state 1 => Depart
        * state 2 => Arrivee
         */

        private int x;
        private int y;
        private int z;
        //rivate boolean depart;
        //private boolean arrivee;

        public Cellule(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            //this.arrivee = bool;
        }

    }

    protected class Etage {

        int etage;

        public Etage(int z) {
            etage = z;
        }

        public void modifier(int x, int y) {
            if (plateau[x][y][z] == null) {
                add(x, y, z);
            } else {
                plateau[x][y][z] = null;
            }
        }

    }

}
