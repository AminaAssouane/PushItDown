package jeu;

public interface Entite {
	public int getX();
	public int getY();
	public int getZ();

    /**
     * deplace l'entite sur la case x, y ,z
     * @param x
     * @param y
     * @param z
     */
    public void deplacer(int x, int y, int z);
}
