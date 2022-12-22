import javax.swing.JFrame;
import affiche.Affichage;
import joueurs.J2;
import matri.Matrice;
import pt.Coordo;

public class Main{
    public static void main(String[] args) {
        JFrame frame=new JFrame();
         Matrice tab1=new Matrice();
         J2 j= new J2();
         j.setC(new Coordo(1, 2));
        tab1.table(3,3);  
        tab1.placer(j.getC(),1); 

        Affichage af=new Affichage(tab1);
        frame.setTitle("coucou");
        frame.setLayout(null);
        frame.setSize(700,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(af);
        frame.setVisible(true);
    }
}