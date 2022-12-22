package affiche;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pt.*;
import joueurs.J2;
import listener.MouseLi;
import matri.Matrice;
import playeur.J1;

public class Affichage extends JPanel {
    JLabel[] tabpanel;
    MouseLi lis;
    J1[] joueurs;
    Matrice m;

    public J1[] getJ1() {
        return joueurs;
    }

    public void setJ1(J1[] j) {
        this.joueurs = j;
    }

    J2[] playeur;

    public J2[] getJ2() {
        return playeur;
    }

    public void setJ2(J2[] j2) {
        this.playeur = j2;
    }

    public Affichage(Matrice ma) {
        this.m = ma;
        this.lis = new MouseLi(this);
        this.tabpanel = ma.getLab();
        this.setLayout(new GridLayout(3, 3));
        this.setBounds(50, 50, 150, 150);
        for (int j = 0; j < this.tabpanel.length; j++) {
            tabpanel[j].addMouseListener(lis);
            this.add(tabpanel[j]);
        }
    }

    public J1[] getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(J1[] joueurs) {
        this.joueurs = joueurs;
    }

    public J2[] getPlayeur() {
        return playeur;
    }

    public void setPlayeur(J2[] playeur) {
        this.playeur = playeur;
    }

    public MouseLi getLis() {
        return lis;
    }

    public void setLis(MouseLi lis) {
        this.lis = lis;
    }

    public JLabel[] getTabpanel() {
        return tabpanel;
    }

    public void setTabpanel(JLabel[] tabpanel) {
        this.tabpanel = tabpanel;
    }

    public Matrice getM() {
        return m;
    }

    public void setM(Matrice m) {
        this.m = m;
    }

    // public JLabel[] getTabpanel() {
    // return tabpanel;
    // }

    // public void setTabpanel(JLabel[] tabpanel) {
    // this.tabpanel = tabpanel;
    // }

    // this.icJ2=new ImageIcon("/img/mur.jpg");
    // this.imgJ1=this.icJ2.getImage();

    // //this.addMouseListener(this);
}
