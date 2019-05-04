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
        this.fenetre_3D.setSize(1000, 600);
	this.fenetre_3D.setLayout(null);
	this.fenetre_3D.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
        this.fenetre_3D.setVisible(true);

    }
    
	Bloc grid[][][];
	int x,y,z;


    public void afficherPlateau(byte[][][] plateau){
   }
    
    public void fselection(int x, int y, int z){
        
    }

    public void afficherplateau(byte plateau[][][],JFrame plplp,int xoffset,int yoffset){
 			byte[][][] blocs = plateau; 
			x=blocs.length;
			y=blocs[0].length;
			z=blocs[0][0].length;
 			Bloc[][][] grid = new Bloc[x][y][z];; 
			int middle=(y<z?y:z)*20;
			int down=2*middle;
			for(int i = 0;i<x;++i) 
				for(int j = 0;j<y;++j)
					for(int k = z;k>0;--k) {
						System.out.println(i+""+j+""+k+" "+blocs[i][j][k]);
						grid[i][j][k]=new Bloc(blocs[i][j][k]);
						grid[i][j][k].jl.setBounds(
								middle-20*k+20*j+xoffset,
								 down-k*10-10*j+20*i+yoffset
								, 40, 40);
                                                System.out.println(i+" "+j+" "+k+"\n");
                                             plplp.add(grid[i][j][k].jl);
					}

       
    }
    static ImageIcon ii0 = new ImageIcon("images\\CUBE0.png");
    static ImageIcon ii = new ImageIcon("images\\CUBE.png");
    static ImageIcon ii2 = new ImageIcon("images\\CUBEa.png");
    static ImageIcon ii3 = new ImageIcon("images\\CUBEb.png");
    static ImageIcon ii4 = new ImageIcon("images\\BALL.png");
    static ImageIcon ii5 = new ImageIcon("images\\BALLa.png");
    static ImageIcon ii6 = new ImageIcon("images\\BALLb.png");
    static ImageIcon ii7 = new ImageIcon("images\\CUBEc.png");
    static ImageIcon ii8 = new ImageIcon("images\\CUBEd.png");
	
    public class Bloc {
	JLabel jl;
	BufferedImage image;


        Bloc(){
		jl= new JLabel(ii);
		jl.setBounds(0, 0, 40, 40);
	}
	Bloc(Byte i){
    	
            switch (i) {
		case 0:
			jl= new JLabel(EditeurVue.ii0);
			break;
		case 1:
			jl= new JLabel(EditeurVue.ii);
			break;
		case 2:
			jl= new JLabel(EditeurVue.ii2);
			break;
		case 3:
			jl= new JLabel(EditeurVue.ii3);
			break;
		case 4:
			jl= new JLabel(EditeurVue.ii4);
			break;
		case 5:
			jl= new JLabel(EditeurVue.ii5);
			break;
		case 6:
			jl= new JLabel(EditeurVue.ii6);
			break;
		case 7:
			jl= new JLabel(EditeurVue.ii7);
			break;
		case 8:
			jl= new JLabel(ii8);
			break;
		default:
                    System.out.println("ID de bloc incorrect");
			jl= new JLabel(EditeurVue.ii2);
			break;

            }
                jl.setBounds(0, 0, 40, 40);
                jl.setVisible(true);
	}
    }

    
}
