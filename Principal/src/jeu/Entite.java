package jeu;

public interface Entite {

    /**
     * deplace l'entite sur la case x, y ,z
     * @param x
     * @param y
     * @param z
     */
    public void deplacer(int x, int y, int z);
    
    /** Getters and Setters **/
	public int getX();
	public int getY();
	public int getZ();
	public void setX(int x);
	public void setY(int y);
	public void setZ(int z);

}
