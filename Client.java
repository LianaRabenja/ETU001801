
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

import lecture.Lecture;
import pt.Coordo;
import matri.Matrice;

public class Client extends Thread implements Serializable {
        Matrice m;
        Interface i;

        public void run() {
                String ip = JOptionPane.showInputDialog("Entrer l'adresse ip du serveur");
                Socket socket;
                Lecture lec = new Lecture();
                try {
                        socket = new Socket(ip, 5000);

                        System.out.println("Connecter au server");

                        ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());

                        m = (Matrice) oi.readObject();

                        i = new Interface(m);
                        // while (!m.equals(null)) {
                        // i.setM(m);
                        // }
                        // socket.close();ject(m);

                        ObjectOutputStream objout = new ObjectOutputStream(socket.getOutputStream());

                        int ix = 0;
                        // while (ix < 3) {
                        // i.getMatr().Afficher();

                        /// }
                        // i.getMatr().placer(new Coordo(2, 2), 3);
                        // objout.writeObject(i.getAff().getLis().getVc());

                } catch (

                Exception e) {
                        e.printStackTrace();
                }
        }

        public static void main(String[] args) throws UnknownHostException, IOException {
                new Client().start();
        }

        public Matrice getM() {
                return m;
        }

        public void setM(Matrice m) {
                this.m = m;
        }

        public Interface getI() {
                return i;
        }

        public void setI(Interface i) {
                this.i = i;
        }
}