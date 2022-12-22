import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import matri.Matrice;

public class ServeurTraitement extends Thread {
    Socket s;
    public static String ip;
    Matrice m;

    public ServeurTraitement(Socket s, Matrice matr) {
        super();
        this.s = s;
        m = matr;
    }

    public void run() {
        try {
            System.out.println("Connecter");
            while (true) {
                ip = s.getRemoteSocketAddress().toString();
                ObjectOutputStream objout = new ObjectOutputStream(s.getOutputStream());

                // s.getInputStream();

                objout.writeObject(m);
                // if (!(s.getInputStream()).equals(null)) {
                // System.out.println("eo ary hoe= " + s.getInputStream());
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
