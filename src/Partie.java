
public class Partie {
	
	
	int nbCoups;
	int score;
	Joueur joueur;	
	Plateau p;
	int coordX; // Les coordonnées de la balle
	int coordY; // La coordonnée X correspond à la hauteur et Y à la largeur.
	
	// 0 : bas, 1 : gauche, 2 : haut, 3 : droit
	public void deplacement(Plateau p, int direction){
		switch (direction){ 
		case 0:
			if (Jeu.deplacementOK(p,p.getCase(coordX, coordY), coordX, coordY+1)){
				this.coordY++;
			}
			break;
		case 1:
			if (Jeu.deplacementOK(p,p.getCase(coordX, coordY), coordX-1, coordY)){
				this.coordX--;
			}
			break;
		case 2:
			if (Jeu.deplacementOK(p,p.getCase(coordX, coordY), coordX, coordY-1)){
				this.coordY--;
			}
			break;
		case 3:
			if (Jeu.deplacementOK(p,p.getCase(coordX, coordY), coordX+1, coordY)){
				this.coordX++;
			}
			break;
		default : 
			break;
		}
	}
	
	public boolean tour(){
		return true;
	}
	
	// Getters and Setters
	
	public int getCoordX(){ return this.coordX; }
	public int getCoordY(){ return this.coordY; }
	public Plateau getPlateau(){ return this.p; }
}
