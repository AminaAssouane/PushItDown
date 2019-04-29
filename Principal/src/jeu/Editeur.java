package jeu;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Editeur {

    private byte[][][] plateau;
    private byte x, y, z, indice;
    private byte[][] depart = new byte[4][3];
    private byte[][] arrivee = new byte[4][3];
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
        if (plateau[x][y][z] == 0) {
            plateau[x][y][z] = 1;
            return true;
        }
        return false;
    }

    public boolean remove(byte x, byte y, byte z) {
        if (plateau[x][y][z] != 0) {
            plateau[x][y][z] = 0;
            return true;
        }
        return false;
    }

    public boolean adddepart(byte x, byte y, byte z) {

        if (dansletableau(x, y, z)) {
            if (plateau[x][y][z] != 0 && plateau[x][y][z + 1] == 0) {
                if (plateau[x][y][z] != 3) {
                    plateau[x][y][z] = 3;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addarrive(byte x, byte y, byte z) {
        if (dansletableau(x, y, z)) {
            if (plateau[x][y][z] != 0 && plateau[x][y][z + 1] == 0) {
                if (plateau[x][y][z] != 2) {
                    plateau[x][y][z] = 2;
                    return true;
                }
            }

        }
        return false;
    }
    
    public void enregister(String path) throws IOException, FileNotFoundException {
        new Level().writelvleditor(path,(byte)4,depart,arrivee,plateau);
    }

    
    public static void main (String []args) throws IOException{
        Editeur e = new Editeur ((byte)10,(byte)10,(byte)10);
        e.enregister("frm");
    }
}
