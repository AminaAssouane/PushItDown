package jeu;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Bille implements Entite{
	
	private int x,y,z;
	private JLabel jl;
	private JLayeredPane pa;
	
	public Bille(){};
	
	public Bille(int x,int y,int z,JLayeredPane pa){
		this.pa=pa;
		this.x=x;
		this.y=y;
		this.z=z;
		jl= new JLabel(new ImageIcon("BALL.png"));
		jl.setBounds(200, 200-((z+1)*20 + 2), 40, 40);
		pa.add(jl,1000,1);
	}
	
	/* Efface la bille */
	public void efface(){
		this.pa.remove(jl);
	}
	
	public void deplacer(int x,int y,int z,int numBloc){
		jl.setBounds(200+(x*20)-(y*20), 200 + (x * 10) + (10 * y)-((z+1)*20 + 2), 40, 40);
		this.pa.add(jl,numBloc,1);	
	}
	
	public void deplacer(int x,int y, int z){
		jl.setBounds(200+(x*20)-(y*20), 200 + (x * 10) + (10 * y)-((z+1)*20 + 2), 40, 40);
		this.pa.add(jl,10000,1);	
	}

  /** Getters and Setters **/
	
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public int getZ(){
		return this.z;
	}
	
	
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public void setZ(int z){
		this.z = z;
	}
}
