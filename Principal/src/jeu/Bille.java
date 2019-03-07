package jeu;

import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Bille extends Bloc implements Entite{
	int x,y,z;
	JLayeredPane f;
	public Bille(){};
	public Bille(Byte b){
		super(b);
	}
	
	Bille(int x,int y,int z,JLayeredPane f){
		this.f=f;
		this.x=x;
		this.y=y;
		this.z=z;
		jl= new JLabel(new ImageIcon("BALL.png"));
		jl.setBounds(200, 200-((z+1)*20 + 2), 40, 40);
		f.add(jl,1000,1);
	}
	
	public void deplacer(int x,int y, int z){
		jl.setBounds(200+(x*20)-(y*20), 200 + (x * 10) + (10 * y)-((z+1)*20 + 2), 40, 40);
		f.add(jl,10000,1);	
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
