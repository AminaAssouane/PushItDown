package jeu;

import java.util.LinkedList;

import javax.swing.ImageIcon;

public class Joueur {

	private String nom;
	private int score; // Le score courant du joueur
	private int bestScore;
	private int lvlMaxAtteint;
	private LinkedList<Plateau.Deplacement_mem> listCoups;
	private ImageIcon avatar;

	public Joueur(String nom) {
		this.setNom(nom);
		this.bestScore = 0;
		this.lvlMaxAtteint = 0;
		this.listCoups = new LinkedList<Plateau.Deplacement_mem>();
		this.avatar = null;
	}
	
	// Ajoute un deplacement � la liste de coups 
	public void addCoup(Plateau.Deplacement_mem dep){
		this.listCoups.addLast(dep);
	}
	
	// Renvoie (mais NE RETIRE PAS) le dernier coup effectu� par le joueur
	public Plateau.Deplacement_mem getLastCoup(){
		return listCoups.peekLast();
	}
	
	// Renvoie (ET RETIRE) le dernier coup effectu� par le joueur
	public Plateau.Deplacement_mem undoLastCoup(){
		return listCoups.pollLast();
	}	
	
	// A ajouter : 1. retirer le niveau ou on est arriv� de la classe "Jeu" ou "Plateau"
	// 2. Sauvegarde dans un fichier avec jflex
	public void sauvegarde(){
		if (this.score > this.bestScore)
			this.bestScore = this.score;	
	}
	
	// Possibilit� d'ajouter un avatar
	public void addAvatar(String photo){
		this.avatar = new ImageIcon(photo);
	}
	
	
	/* GETTERS AND SETTERS */
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public int getBestScore() {
		return bestScore;
	}

	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}

	public void setLvlMaxAtteint(int lvl) {
		this.lvlMaxAtteint = lvl;
	}
	
	public int getLvlMaxAtteint() {
		return this.lvlMaxAtteint;
	}
	
	public void setListCoups(LinkedList<Plateau.Deplacement_mem> list) {
		this.listCoups = list;
	}
	
	public LinkedList<Plateau.Deplacement_mem> getListCoups() {
		return this.listCoups;
	}

}
