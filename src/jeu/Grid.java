package jeu;

import javax.swing.JFrame;

public class Grid {
	Bloc grid[][][];
	int x, y, z;
	

	void savegrid(String path) {
	}

	void loadgrid(String path) {
	}

	void display() {

	}

	Grid(int x, int y, int z, JFrame f) {
		grid = new Bloc[x][y][z];
		this.x = x;
		this.y = y;
		this.z = z;
		int middle = (y < z ? y : z) * 20;
		int down = 2 * middle;
		for (int i = 0; i < x; ++i)
			for (int j = 0; j < y; ++j)
				for (int k = 0; k < z; ++k) {
					System.out.println(i + "" + j + "" + k);
					grid[i][j][k] = new Bloc((byte) ((byte) (k + j + i) % 8));
					grid[i][j][k].jl.setBounds(middle - 20 * k + 20 * j, down - k * 10 - 10 * j + 20 * i, 40, 40);
					f.add(grid[i][j][k].jl);
				}
	}

	Grid(int x, int y, int z, JFrame f, Level l) {
		grid = new Bloc[x][y][z];
		this.x = x;
		this.y = y;
		this.z = z;
		int middle = (y < z ? y : z) * 20;
		int down = 2 * middle;
		for (int i = 0; i < x; ++i)
			for (int j = 0; j < y; ++j)
				for (int k = 0; k < z; ++k) {
					System.out.println(i + "" + j + "" + k + " " + l.values[i * y + j * z + k]);
					grid[i][j][k] = new Bloc(l.values[k + z * j + i * y * z]);
					grid[i][j][k].jl.setBounds(middle - 20 * k + 20 * j, down - k * 10 - 10 * j + 20 * i, 40, 40);
					f.add(grid[i][j][k].jl);
				}

	}

}
