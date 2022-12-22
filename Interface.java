
import javax.swing.JFrame;

import affiche.Affichage;
import matri.Matrice;
import pt.Coordo;

public class Interface {
    Client c;
    Matrice matr;
    Affichage aff;

    public Interface(Matrice m) {
        // c=cl;
        matr = m;
        aff = new Affichage(matr);
        JFrame f = new JFrame();
        f.add(aff);
        f.revalidate();
        f.setSize(500, 500);
        f.setLayout(null);
        f.setVisible(true);
    }

    public Client getC() {
        return c;
    }

    public void setC(Client c) {
        this.c = c;
    }

    public Matrice getMatr() {
        return matr;
    }

    public void setMatr(Matrice matr) {
        this.matr = matr;
    }

    public Affichage getAff() {
        return aff;
    }

    public void setAff(Affichage aff) {
        this.aff = aff;
    }

}
