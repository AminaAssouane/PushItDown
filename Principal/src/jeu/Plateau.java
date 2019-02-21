package jeu;

import java.util.ArrayList;
import javax.swing.JLayeredPane;


public class Plateau {

    private Cellule[][][] plateau;
    private ArrayList<Cellule> cellule_departs = new ArrayList<Cellule>();
    int x,y,z;

    protected Plateau(int x, int y, int z) {
        this.plateau = new Cellule[x][y][z];
      	this.x = x; this.y = y; this.z = z;
    }
  
  	public Plateau(JLayeredPane f, Level l, int niv, Bille b){
		this.x = l.getX(niv);
		this.y = l.getY(niv);
		this.z = l.getZ(niv);
		plateau  = new Cellule[x][y][z];
		int numbloc = 0;
		int middle = 200; //=120
		int down = 200; //= 240
		for (int k = 0; k < z; k++){
			for (int j = 0; j < y; j++){
				for (int i = 0; i < x; i++) {
					plateau[i][j][k] = new Cellule(i,j,k,l.niveau(niv,numbloc),b);
					plateau[i][j][k].jl.setBounds(middle + (20 * i) - (20 * j),down + (i * 10) + (10 * j) - (20 * k),40, 40);
					numbloc++;
					f.add(plateau[i][j][k].jl,numbloc,1);
					System.out.println(numbloc);
				}
			}
		}
		plateau[0][0][z-1].setEntite(b);
	}

    protected boolean getDepart(int x, int y, int z) {
        return cellule_departs.stream().anyMatch((c) -> (c.z == z && c.x == x && c.y == y));
    }

    protected boolean addCellule(int x, int y, int z, boolean depart, boolean end) {
        if (dansletableau(x, y, z) && plateau[x][y][z] == null) {
            Cellule c = new Cellule(x, y, z, 1, null);
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

    protected boolean ajouterEntite(Entite j, int x, int y, int z) {
        /*
                Test s'il y a une cellule en dessous
                puis essaye d'ajouter l'entité si oui
                Uniquement si la cellule d'en dessous est vide !
         */
        if (dansletableau(x, y, z) && plateau[x][y][z] != null && plateau[x][y][z].entite == null) {
            plateau[x][y][z].entite = j;
            return true;
        }
        return false;
    }

    private boolean dansletableau(int x, int y, int z) {
        return (x < plateau.length && x > -1 && y < plateau[x].length && y > -1 && z < plateau[x][y].length && z > -1);
    }

    private boolean add(Deplacement d, ArrayList<Deplacement> a) {
        if (d == null) {
            return false;
        }
        return a.add(d);
    }

    protected boolean supprimer_entite(Entite j) {
        if (j == null) {
            return false;
        }
        if (j instanceof Cellule) {
            if (((Cellule) j).entite != null) {
                return false;
            }
        }
        plateau[j.getX()][j.getY()][j.getZ()].entite = null;
        return true;
    }

    protected boolean deplacement(Deplacement d) {
        if (d == null) {
            return false;
        }

        if (dansletableau(d.x2, d.y2, d.z2 + 1) && ((plateau[d.x2][d.y2][d.z2 + 1] != null) && (plateau[d.x2][d.y2][d.z2 + 1].jl.getName() != "ii0"))) {
            return false;
        }

        if (dansletableau(d.x2, d.y2, d.z2) && !occupe(d.x2, d.y2, d.z2)) {
            if (supprimer_entite(d.entite)) {
                plateau[d.x2][d.y2][d.z2].entite = d.entite;
                d.entite.deplacer(d.x2, d.y2, d.z2);
                d.entite.setX(d.x2);
                d.entite.setY(d.y2);
                d.entite.setZ(d.z2);
                return true;
            }
        }
        return false;
    }

    protected boolean test_final(Entite j) {
        return (plateau[j.getX()][j.getY()][j.getZ()].arrivee);
    }

    // TESTS
    protected ArrayList<Deplacement> possible(Entite bille) {
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

    private boolean occupe(int x, int y, int z) {
        return (dansletableau(x, y, z) && plateau[x][y][z].entite != null);
    }

    private int test_hauteur(int x, int y, int z) {
        // retourne une hauteur possible pour un déplacement, plus basse que la
        // coordonnée z fournie en paramètre
        while (this.plateau[x][y][z] == null && z > -1) {
            z--;
        }
        if (this.plateau[x][y][z].entite == null) {
            return z;
        } else {
            return -1;
            // -1 signifie qu'il n y a pas de case disponible
        }
    }

    protected class Cellule extends Bille implements Entite {

        private int x;
        private int y;
        private int z;
        private int bonus;
        private int color;
        private boolean depart;
        private boolean arrivee;
        private Entite entite;

        public Cellule(int x, int y, int z, int color, Entite entite) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.color = color;
            this.entite = entite;
        }

        public boolean isArrivee() {
            return arrivee;
        }

        public boolean isDepart() {
            return depart;
        }


        @Override
        public void deplacer(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
      
      
      /** Getters and Setters **/

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
      	public void setX(int x){
			this.x = x;
		}
      
      	@Override
		public void setY(int y){
			this.y = y;
		}
      
      	@Override
		public void setZ(int z){
			this.z = z;
		}
      
      	public int getColore() {
            return this.color;
        }
      
      	public void setArrivee(boolean arrivee) {
            this.arrivee = arrivee;
        }
      
      	public void setDepart(boolean depart) {
            this.depart = depart;
        }
      
      	public void setEntite(Entite e){
        	this.entite = e;
        }
      
    }

    protected class Deplacement {

        private int x1;
        private int x2;
        private int y1;
        private int y2;
        private int z1;
        private int z2;
        private Entite entite;

        public Deplacement(Entite entite, int x1, int x2, int y1, int y2, int z1, int z2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.z1 = z1;
            this.z2 = z2;
            this.entite = entite;
        }

        public Deplacement(Entite entite, int x, int y, int z) {
            x1 = entite.getX();
            y1 = entite.getY();
            z1 = entite.getZ();
            x2 = x;
            y2 = y;
            z2 = z;
            this.entite = entite;
        }

        public Deplacement inverse() {
            return new Deplacement(entite, x2, x1, y2, y1, z2, z1);
        }

        public Entite getEntite() {
            return entite;
        }
    }
}
