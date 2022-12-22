package matri;

import java.io.Serializable;
import java.util.Vector;

import pt.Coordo;
import java.awt.*;
import javax.swing.*;

public class Matrice implements Serializable {
	// attributs
	int col;
	int line;
	int[][] tableau;
	JLabel[] lab;

	public Matrice() {
		lab = new JLabel[9];
		for (int j = 0; j < this.lab.length; j++) {
			lab[j] = new JLabel();
			lab[j].setSize(50, 50);
			lab[j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
	}

	/*
	 * public void verifWin(int id) {
	 * if (this.tableau[0][0] == id && this.tableau[1][0] == id &&
	 * this.tableau[2][0] == id) {
	 * 
	 * }
	 * for (int i = 0; i < this.tableau.length; i++) {
	 * for (int j = 0; j < this.tableau[i].length; j++) {
	 * if (this.tableau[j][i]) {
	 * 
	 * }
	 * }
	 * }
	 * }
	 */

	public int getCol() {
		return col;
	}

	public void setCol(int co) {
		col = co;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int li) {
		line = li;
	}

	public int[][] getTableau() {
		return tableau;
	}

	public void setTableau(int[][] tab) {
		tableau = tab;
	}

	public void table(int li, int col) {
		int[][] tab = new int[li][col];
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[i].length; j++) {
				tab[i][j] = 0;
				// System.out.println(tab);
			}

		}
		this.setTableau(tab);
	}

	// methodes

	public boolean CheckallLine(int id) {
		int k = 0;
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < this.getTableau()[i].length; j++) {
				if (this.getTableau()[i][j] == (id)) {
					count++;
				} else {
					break;
				}
			}
			if (count == 3) {
				return true;
			}
		}

		return false;
	}

	public boolean Checkallcolomn(int id) {
		int k = 0;
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < this.getTableau()[i].length; j++) {
				if (this.getTableau()[j][i] == (id)) {
					count++;
				} else {
					break;
				}
			}
			if (count == 3) {
				return true;
			}
		}

		return false;
	}

	public boolean Checkdiagonal1(int id) {
		int k = 0;
		int count = 0;
		for (int i = 0; i < this.getTableau().length; i++) {
			if (this.getTableau()[i][i] == id) {
				count++;
			} else {
				break;
			}
		}
		if (count == 3) {
			return true;
		}
		return false;
	}

	public boolean Checkdiagonal2(int id) {
		int k = 0;
		int count = 0;
		for (int i = 0; i < this.getTableau().length; i++) {
			if (this.getTableau()[i][this.getTableau().length - 1 - i] == id) {
				count++;
			} else {
				break;
			}
		}
		if (count == 3) {
			return true;
		}
		return false;
	}

	public boolean Checkwinner(int id) {
		if (this.CheckallLine(id) || this.Checkallcolomn(id) || this.Checkdiagonal1(id)
				|| this.Checkdiagonal2(id) == true) {
			System.out.println("le joueur:"+id + "gagner");
			return true;

		}
		return false;
	}

	// public void checktour(int id) {
	// 	int tourcourant = 0;
	// 	if (tourcourant % 2 == 0) {
	// 		System.out.println("vrai");
	// 		id = 2;
	// 		tourcourant++;
	// 	} else {
	// 		System.out.println("fals");
	// 		id = 1;
	// 	}
	// 	tourcourant++;
	// }

	public void Afficher() {
		for (int i = 0; i < this.getTableau().length; i++) {
			for (int j = 0; j < this.getTableau()[i].length; j++) {
				System.out.print(this.getTableau()[i][j] + "\t");

			}
			System.out.println("\n");
		}
	}

	public void placer(Coordo c, int x) {
		/// c=c-1 ;
		// l=l-1;
		// tester si la grille existe
		if (c.getX() < 0 || c.getY() < 0 || c.getX() < this.line || c.getY() < this.col) {
			System.out.println("error");
		}
		// si on est sur la bonne case
		if (this.getTableau()[c.getY()][c.getX()] == 0) {

			this.getTableau()[c.getY()][c.getX()] = x;
		}
	}

	public Coordo checking(int value) {

		for (int i = 0; i < this.getTableau().length; i++) {
			for (int j = 0; j < this.getTableau()[i].length; j++) {
				if (this.getTableau()[i][j] == value) {
					System.out.println("Placage en  x= " + j + " y=" + i);
					return new Coordo(j, i);
				}
			}
		}
		return new Coordo(50, 50);
	}

	public void setting() {

		for (int i = 0; i < this.getTableau().length; i++) {
			for (int j = 0; j < this.getTableau()[i].length; j++) {
				if (this.getTableau()[i][j] == 1) {
					for (int j2 = 0; j2 < lab.length; j2++) {
						if (this.lab[j2].getX() / 50 == j && this.lab[j2].getY() / 50 == i) {
							this.lab[j2].setIcon(new ImageIcon("./img/TheX.png"));
						}
					}
				}

				if (this.getTableau()[i][j] == 2) {
					for (int j2 = 0; j2 < lab.length; j2++) {
						if (this.lab[j2].getX() / 50 == j && this.lab[j2].getY() / 50 == i) {
							this.lab[j2].setIcon(new ImageIcon("./img/RedCircle.png"));
						}
					}
				}

			}
		}
	}

	public JLabel[] getLab() {
		return lab;
	}

	public void setLab(JLabel[] lab) {
		this.lab = lab;
	}

}
