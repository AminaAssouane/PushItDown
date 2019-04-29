package jeu;
import java.util.ArrayList;

public class Editeur {

    private Cellule[][][] plateau;
    private ArrayList<Cellule> listedepart = new ArrayList();
    private ArrayList<Cellule> listearrive = new ArrayList();
    private byte x, y, z, indice;
    
    // ATTENTION, le dernier etage n'est pas utilisable sinon la bille sort du 
    // tableau, donc les fonctions test pour z-1.
   
    public Etage stage(byte x) {
        if (x < this.z-1){
        return new Etage(x);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public void setIndice(byte ind) {
        this.indice = ind;
    }

    public Cellule[][][] getPlateau() {
        return this.plateau;
    }

    public Cellule getC(byte x, byte y, byte z) {
        if (!dansletableau(x, y, z)) {
            return null;
        }
        return this.plateau[x][y][z];
    }

    public Editeur(byte x, byte y, byte z) {
        this.plateau = new Cellule[x][y][z];
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private boolean dansletableau(byte x, byte y, byte z) {
        return (x < plateau.length && x > -1 && y < plateau[x].length && y > -1
                && z < plateau[x][y].length-1 && z > -1);
    }

    public boolean add(byte x, byte y, byte z) {
        if (plateau[x][y][z] == null) {
            plateau[x][y][z] = new Cellule(x, y, z);
            return true;
        }
        return false;
    }

    public boolean adddepart(byte x, byte y, byte z) {

        if (dansletableau(x, y, z)) {
            if (plateau[x][y][z] != null && plateau[x][y][z+1]==null) {
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

    public boolean addarrive(byte x, byte y, byte z) {
        if (dansletableau(x, y, z)) {
            if (plateau[x][y][z] != null && plateau[x][y][z+1]==null) {
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

        private byte x;
        private byte y;
        private byte z;
        //rivate boolean depart;
        //private boolean arrivee;

        public Cellule(byte x, byte y, byte z) {
            this.x = x;
            this.y = y;
            this.z = z;
            //this.arrivee = bool;
        }
        
        public void setState(int x){
            this.state=x;
        }

    }

    protected class Etage {

        byte etage;

        public Etage(byte z) {
            etage = z;
        }

        public void modifier(byte x, byte y) {
            if (plateau[x][y][z] == null) {
                add(x, y, z);
            } else {
                plateau[x][y][z] = null;
            }
        }

    }

}
