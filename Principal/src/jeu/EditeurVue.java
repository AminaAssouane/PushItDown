package jeu;

import javax.swing.JFrame;

public class EditeurVue {
    private JFrame fenetre_3D;
    private JFrame fenetre_2D;
    private JFrame fenetre_selection;
    
    public void EditeurVue(){
        this.fenetre_3D = new JFrame();
        this.fenetre_2D = new JFrame();
        this.fenetre_selection = new JFrame();
        this.fenetre_selection.setVisible(true);
    }
    
    public void afficherPlateau(byte[][][] plateau){
        
    }
    
    public void fselection(int x, int y, int z){
        
    }
    
}
