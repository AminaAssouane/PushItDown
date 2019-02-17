package jeu;

import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Bille extends Bloc{
	int x,y,z;
	Bille(int x,int y,int z,JFrame f){
		this.x=x;
		this.y=y;
		this.z=z;
		jl= new JLabel(new ImageIcon("BALL.png"));
		jl.setBounds(x, y, 40, 40);
		f.add(jl);
	}
	void moveleft(){
		int x2=x-20;
		while(x>x2) {
			waits();
			x-=2;
			y--;
		}
	}
	void moveup(){}
	void movedown(){}
	void moveright(){
		int x2=x+20;
		while(x<x2) {
			waits();
			x+=2;
			y++;
			jl.setBounds(x, y, 40, 40);
			System.out.println("ok");
		}
	}

	static void waits() {//pour tester
//		System.nanoTime()
	}
}
