package jeu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
public class Level {
	
    /* Les niveaux sont crées comme suit : */
    /*
    1. Les "0x0" correspondent à  des bloques vides, les "0x1" à  des blocs normaux (marrons), les "0x2" aux blocs 
    finaux (verts), et les "0x3" aux blocs de début (bleu)

    2. Chaque "bloc" est un étage, donc le level 1 qui ne contient qu'un seul "bloc" ne contient qu'un seul étage, 
    et le niveau 3 contient 6 étages. Ceci correspond à  l'axe Z.
    
    3. Chaque ligne correspond à  une ligne de l'axe X. Donc déplacement avec les touches gauche et droite du clavier.
    
    4. Chaque colonne correspond à  une ligne de l'axe Y. Donc déplacement avec les touches haut et bas du clavier.
    */
	

	boolean solvable;
	Byte difficulty;
	
	public int getX(int niv){
		return dimensions[0];
	}
	
	public int getY(int niv){
		return dimensions[1];
	}

	public int getZ(int niv){
		return dimensions[2];
	}
	
	
	// Fonction qui retourne le "byte" correspondant au bloc voulu
		public Byte niveau(int niv,int numbloc){
			return level[numbloc];
//			switch (niv){
//			case 1:
//				return level1[numbloc];
//			case 2:
//				return level2[numbloc];
//			case 3 :
//				return level3[numbloc];
//			case 4 :
//				return level4[numbloc];
//			case 5 :
//				return level5[numbloc];
//			default :
//				return level1[numbloc];
//			}
		}
	
	// Fonction qui retourne si oui ou non la case est une case d'arrivée (de sortie)	
		public boolean arrivee(int niv, int numbloc){
				if (numbloc == 35) 
					return true;
				else
					return false;
		}

		//tout ce qui est au dessus de ce commentaire ne sera plus utile en version finale

		String file,path;
		savedpath sp;
		int currentlvl;

		byte[] dimensions = {//X,Y,Z
				0x6,0x6,0x1
		};

		
		byte nbbille=1;//nombre de billes
		byte[][] start = {//tableau de tableau de coordonnees des billes
				{0x0,0x0,0x1},//bille 1
				{0x0,0x0,0x1},//bille 2
				{0x0,0x0,0x1},//etc
				{0x0,0x0,0x1}
		};
		byte[][] exit= {
				{0x5,0x5,0x1},//idem
				{0x5,0x5,0x1},
				{0x5,0x5,0x1},
				{0x5,0x5,0x1}
		};

		byte level[];

		void tab3entab(byte[][][] tab3){
			dimensions[0]=(byte) tab3[0][0].length;
			dimensions[1]=(byte) tab3[0].length;
			dimensions[2]=(byte) tab3.length;			
			level=new byte[dimensions[0]*dimensions[1]*dimensions[2]];
			for(int i =0; i<dimensions[0];++i) 
				for(int j =0; j<dimensions[1];++j) 
					for(int k =0; k<dimensions[1];++k) 
						{
							level[i+j*i+k*j*i]=tab3[i][j][k];
						} 
		}
		
		void writelvleditor(String path,byte nbbilles,byte[][]depart,byte[][]arrivee,byte[][][]lv) throws FileNotFoundException, IOException {
			FileOutputStream fos = new FileOutputStream(path);
			tab3entab(lv);
			System.out.println("sauvegarde du niveau dans "+path);
			fos.write(dimensions);
			System.out.println("les dimensions sont X"+dimensions[0]+" Y"+dimensions[1]+" Z"+dimensions[2]);
			fos.write(nbbilles);
			System.out.println("il y a "+nbbilles+" billes");
			for(int i=0;i<nbbilles;i++)
				fos.write(depart[i]);
			for(int i=0;i<nbbilles;i++)
				fos.write(arrivee[i]);
			fos.write(level);
			fos.flush();
			fos.close();
			System.out.println("sauvegarde avec succes");
		}
		void readlvleditor(String path) throws IOException {
			FileInputStream fis = new FileInputStream(path);
			fis.read(dimensions);
			System.out.println("dimensions are X"+dimensions[0]+" Y"+dimensions[1]+" Z"+dimensions[2]);
			level=new byte[dimensions[0]*dimensions[1]*dimensions[2]];
			System.out.println("there are "+level.length+"bytes");
			fis.read(level);
			
			fis.close();
		}

		
		void writelvl(String path) throws FileNotFoundException, IOException {
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(dimensions);
			fos.write(level);
			fos.flush();
			fos.close();
		}
		//readlvl("level"+numero)
		void readlvl(String path) throws IOException {
			FileInputStream fis = new FileInputStream(path);
			fis.read(dimensions);
			System.out.println("dimensions are X"+dimensions[0]+" Y"+dimensions[1]+" Z"+dimensions[2]);
			level=new byte[dimensions[0]*dimensions[1]*dimensions[2]];
			System.out.println("there are "+level.length+"bytes");
			fis.read(level);
			
			fis.close();
		}
		
		
		Level(){
			
//			try {
//				writelevels();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}//provisoire
		}
		Level(int n) {
			try {
//				writelevels();
				changelvl(n);
			} catch (IOException e) {
				e.printStackTrace();
			}//provisoire
			
		}

		void changelvl(int n) throws IOException{
			currentlvl=n;
			file="level"+n;
			readlvl(file);
		}
		void nextlvl() throws IOException{
			currentlvl++;
			file="level"+currentlvl;
			readlvl(file);
		}
		//en dessous de ce commentaire = a supprimer plus tard, seulement pour sauvegarder les niveaux de ce fichier
//		void writelevels() throws FileNotFoundException, IOException{
//			for(int i=1;i<=5;++i)
//				{
//					writelvlp("level"+i,i);
//					System.out.print("file"+i+" created\n");
//				}
//		}
//		void writelvlp(String path,int i) throws FileNotFoundException, IOException {
//			FileOutputStream fos = new FileOutputStream(path);
//			fos.write(getX(i));
//			fos.write(getY(i));
//			fos.write(getZ(i));
//			fos.write(wniveau(i));
//			fos.flush();
//			fos.close();
//		}
//		public byte[] wniveau(int niv){
//			switch (niv){
//			case 1:
//				return level1;
//			case 2:
//				return level2;
//			case 3 :
//				return level3;
//			case 4 :
//				return level4;
//			case 5 :
//				return level5;
//			default :
//				return level1;
//			}
//		}
//
//		
	}
