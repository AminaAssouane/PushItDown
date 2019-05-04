package jeu;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Editeur {

    private byte[][][] plateau;
    
//    Description du plateau :
//    Pour la valeur plateau[x][y][z]
//    Si = 0, cellule vide
//    Si = 1, cellule simple
//    Si = 2, cellule de depart
//    Si = 3, cellule d'arrivee

    private byte x, y, z, indice;
    private ArrayList<Triplet> arrivee = new ArrayList<Triplet>();
    private ArrayList<Triplet> depart = new ArrayList<Triplet>();

    // ATTENTION, le dernier etage n'est pas utilisable sinon la bille sort du 
    // tableau, donc les fonctions test pour z-1.
    public void setIndice(byte ind) {
        this.indice = ind;
    }

    public byte[][][] getPlateau() {
        return this.plateau;
    }

    public byte getC(byte x, byte y, byte z) {
        if (!dansletableau(x, y, z)) {
            return -1;
        }
        return this.plateau[x][y][z];
    }

    public Editeur(byte x, byte y, byte z) {
        this.plateau = new byte[x][y][z];
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private boolean dansletableau(byte x, byte y, byte z) {
        return (x < plateau.length && x > -1 && y < plateau[x].length && y > -1
                && z < plateau[x][y].length - 1 && z > -1);
    }

    public boolean add(byte x, byte y, byte z) {
        if (dansletableau(x, y, z)) {
            if (plateau[x][y][z] == 0) {
                plateau[x][y][z] = 1;
                return true;
            }
        }
        return false;
    }

    public boolean remove(byte x, byte y, byte z) {
        if (plateau[x][y][z] != 0) {
            if (this.arrivee.contains(new Triplet(x, y, z))) {
                this.arrivee.remove(new Triplet(x, y, z));
            }
            plateau[x][y][z] = 0;
            return true;
        }
        return false;
    }

    public boolean adddepart(byte x, byte y, byte z) {
        if (dansletableau(x, y, z) && !depart.contains(new Triplet(x, y, z))) {
            this.plateau[x][y][z] = 2;
            this.depart.add(new Triplet(x, y, z));
        }
        return false;
    }

    public boolean addarrive(byte x, byte y, byte z) {
        if (dansletableau(x, y, z) && !arrivee.contains(new Triplet(x, y, z))) {
            this.plateau[x][y][z] = 3;
            this.arrivee.add(new Triplet(x, y, z));
        }
        return false;
    }

    public static byte[][] convertir(ArrayList<Triplet> list) {
        byte[][] tab = new byte[4][3];
        for (int i = 0; i < list.size(); i++) {
            tab[i][0] = list.get(i).x;
            tab[i][1] = list.get(i).y;
            tab[i][2] = list.get(i).z;
        }
        return tab;
    }

    public void enregister(String path) throws IOException, FileNotFoundException {
        if (this.arrivee.size() > 0 && this.depart.size() > 0) {
            new Level().writelvleditor(path, (byte) 4, convertir(depart), convertir(arrivee), plateau);
        }
    }

    public static void main(String[] args) throws IOException {
        Editeur e = new Editeur((byte) 3, (byte) 3, (byte) 3);
        System.out.println(e.add((byte) 0, (byte) 0, (byte) 0));
        e.addarrive((byte)0, (byte)0, (byte)0);
        e.adddepart((byte)1, (byte)1, (byte)0);
        e.add((byte) 2, (byte) 2, (byte) 0);
        e.add((byte) 2, (byte) 3, (byte) 0);
        e.enregister("ter");
        EditeurVue ev =new EditeurVue();
        byte[][][] a={{{1,1,1,1},{1,1,1,1},{1,1,1,1}},{{1,1,1,1},{1,1,1,1},{1,1,1,1}}};
        e.plateau=a;
        ev.afficherplateau(e.plateau);//
        
    }

    private class Triplet {

        byte x;
        byte y;
        byte z;

        public Triplet(byte x, byte y, byte z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
