package jeu;

import java.awt.Component;
import javax.swing.JFrame;
import java.awt.image.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class EditeurVue {
    private JFrame fenetre_3D;
    private JFrame fenetre_2D;
    private JFrame fenetre_selection;
    
    public void EditeurVue(){
        this.fenetre_3D = new JFrame();
        this.fenetre_2D = new JFrame();
        this.fenetre_selection = new JFrame();
        this.fenetre_selection.setVisible(true);
        this.fenetre_3D.setVisible(true);

    }
    
	Bloc grid[][][];
	int x,y,z;


    public void afficherPlateau(byte[][][] plateau){
   }
    
    public void fselection(int x, int y, int z){
        
    }

    public void afficherplateau(byte plateau[][][]){
 			byte[][][] blocs = plateau; 
			x=blocs.length;
			y=blocs[0].length;
			z=blocs[0][0].length;
 			Bloc[][][] grid = new Bloc[x][y][z];; 
			int middle=(y<z?y:z)*20;
			int down=2*middle;
			for(int i = 0;i<x;++i) 
				for(int j = 0;j<y;++j)
					for(int k = 0;k<z;++k) {
						System.out.println(i+""+j+""+k+" "+blocs[i][j][k]);
						grid[i][j][k]=new Bloc(blocs[i][j][k]);
						grid[i][j][k].jl.setBounds(
								middle-20*k+20*j,
								 down-k*10-10*j+20*i
								, 40, 40);
                                                System.out.println(i+" "+j+" "+k+" ");
                                                System.out.print(this.fenetre_3D==null);
                                             this.fenetre_3D.add(grid[i][j][k].jl);
					}

       
    }
    static ImageIcon ii0 = new ImageIcon("CUBE0.png");
    static ImageIcon ii = new ImageIcon("CUBE.png");
    static ImageIcon ii2 = new ImageIcon("CUBEa.png");
    static ImageIcon ii3 = new ImageIcon("CUBEb.png");
    static ImageIcon ii4 = new ImageIcon("BALL.png");
    static ImageIcon ii5 = new ImageIcon("BALLa.png");
    static ImageIcon ii6 = new ImageIcon("BALLb.png");
    static ImageIcon ii7 = new ImageIcon("CUBEc.png");
    static ImageIcon ii8 = new ImageIcon("CUBEd.png");
	
    public class Bloc {
	JLabel jl;
	BufferedImage image;
	Bloc(){
		jl= new JLabel(ii);
//		jl.setBounds(0, 0, 40, 40);
	}
	Bloc(Byte i){
    	
            switch (i) {
		case 0:
			jl= new JLabel(ii0);
			break;
		case 1:
			jl= new JLabel(ii);
			break;
		case 2:
			jl= new JLabel(ii2);
			break;
		case 3:
			jl= new JLabel(ii3);
			break;
		case 4:
			jl= new JLabel(ii4);
			break;
		case 5:
			jl= new JLabel(ii5);
			break;
		case 6:
			jl= new JLabel(ii6);
			break;
		case 7:
			jl= new JLabel(ii7);
			break;
		case 8:
			jl= new JLabel(ii8);
			break;
		default:
			jl= new JLabel(ii2);
			break;

            }
                jl.setBounds(0, 0, 40, 40);
                jl.setVisible(true);
	}
    }

    
}
