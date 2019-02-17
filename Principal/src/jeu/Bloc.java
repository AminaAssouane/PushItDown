package jeu;

import java.awt.image.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Bloc {
	JLabel jl;
	static ImageIcon ii0 = new ImageIcon("CUBE0.png");
	static ImageIcon ii = new ImageIcon("CUBE.png");
	static ImageIcon ii2 = new ImageIcon("CUBEa.png");
	static ImageIcon ii3 = new ImageIcon("CUBEb.png");
	static ImageIcon ii4 = new ImageIcon("BALL.png");
	static ImageIcon ii5 = new ImageIcon("BALLa.png");
	static ImageIcon ii6 = new ImageIcon("BALLb.png");
	static ImageIcon ii7 = new ImageIcon("CUBEc.png");
	static ImageIcon ii8 = new ImageIcon("CUBEd.png");
	BufferedImage image;

	Bloc() {
		jl = new JLabel(ii);
//		jl.setBounds(0, 0, 40, 40);
	}

	Bloc(Byte i) {
		switch (i) {
		case 0:
			jl = new JLabel(ii0);
			break;
		case 1:
			jl = new JLabel(ii);
			break;
		case 2:
			jl = new JLabel(ii2);
			break;
		case 3:
			jl = new JLabel(ii3);
			break;
		case 4:
			jl = new JLabel(ii4);
			break;
		case 5:
			jl = new JLabel(ii5);
			break;
		case 6:
			jl = new JLabel(ii6);
			break;
		case 7:
			jl = new JLabel(ii7);
			break;
		case 8:
			jl = new JLabel(ii8);
			break;
		}
		jl.setBounds(0, 0, 40, 40);
	}
}
