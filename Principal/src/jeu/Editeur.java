package jeu;

public class Editeur {

    private Byte[][][] plateau;
    private byte x, y, z, indice;

    // ATTENTION, le dernier etage n'est pas utilisable sinon la bille sort du 
    // tableau, donc les fonctions test pour z-1.

    public void setIndice(byte ind) {
        this.indice = ind;
    }

    public Byte[][][] getPlateau() {
        return this.plateau;
    }

    public Byte getC(byte x, byte y, byte z) {
        if (!dansletableau(x, y, z)) {
            return null;
        }
        return this.plateau[x][y][z];
    }

    public Editeur(byte x, byte y, byte z) {
        this.plateau = new Byte[x][y][z];
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private boolean dansletableau(byte x, byte y, byte z) {
        return (x < plateau.length && x > -1 && y < plateau[x].length && y > -1
                && z < plateau[x][y].length - 1 && z > -1);
    }

    public boolean add(byte x, byte y, byte z) {
        if (plateau[x][y][z] == null) {
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
            if (plateau[x][y][z] != null && plateau[x][y][z + 1] == null) {
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
            if (plateau[x][y][z] != null && plateau[x][y][z + 1] == null) {
                if (plateau[x][y][z] != 2) {
                    plateau[x][y][z] = 2;
                    return true;
                }
            }

        }
        return false;
    }

    public void enregister() {
        // completer l'enregistrement
    }

}
