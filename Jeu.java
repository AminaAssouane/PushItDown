/* Classe qui implémente quelque règles du jeu */
public abstract class Jeu {
	
	// Tableau qui liste les niveaux disponibles
	public Niveau niveaux[];
	
	// Méthode qui determine si le déplacement voulu est autorisé
	public static boolean deplacementOK(Plateau P, Case caseNow, int coordXD, int coordYD){
		
		// si on sort des bornes
		if ((coordXD < 0) || (coordYD < 0)  
				|| (coordXD > P.getHauteur()) || (coordYD > P.getLargeur())){
			return false;
		}
		else {
			if (P.getCase(coordXD,coordYD).getAxeZ() > caseNow.getAxeZ()){
				return false;
			}
			else {
				return true;
			}
		}
	}
	
	public boolean gagne(Partie p){
		if ((p.getCoordX() == p.getPlateau().getHauteur()) && (p.getCoordY() == p.getPlateau().getLargeur()))
		return true;
		else return false;
	}
}
 