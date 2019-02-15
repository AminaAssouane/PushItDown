public class Plateau {
	
	Case[][] plateau;
	private int hauteur;
	private int largeur;
	
	public void plateau(int haut, int larg){
		plateau = new Case[haut][larg];
		this.hauteur = haut;
		this.largeur = larg;
	}
	
	
	// Getters //
	public int getHauteur(){
		return this.hauteur;
	}
	
	public int getLargeur(){
		return this.largeur;
	}
	
	public Case getCase(int x, int y){
		return plateau[x][y];
	}
}
