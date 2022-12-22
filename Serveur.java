
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import matri.Matrice;

public class Serveur extends Thread {
    public static String ip;
    Matrice m;

    public Serveur() {
        m = new Matrice();
        m.table(3, 3);
    }

    @Override
    public void run() {
        try {
            ServerSocket sk = new ServerSocket(5000);
            while (true) {
                System.out.println("En attente de connexion");
                Socket sc = sk.accept();
                new ServeurTraitement(sc, m).start();
                new ServeurLecture(sc, m).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] test) throws IOException {
        new Serveur().start();

    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        Serveur.ip = ip;
    }

    public Matrice getM() {
        return m;
    }

    public void setM(Matrice m) {
        this.m = m;
    }

}