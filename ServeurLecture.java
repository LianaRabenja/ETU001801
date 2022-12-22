
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import lecture.Lecture;
import matri.Matrice;
import pt.Coordo;

public class ServeurLecture extends Thread {

    Socket s;
    public static String ip;
    Matrice m;

    public ServeurLecture(Socket s, Matrice matr) {
        super();
        this.s = s;
        m = matr;
    }

    public void run() {
        try {
            ip = s.getRemoteSocketAddress().toString();

            ObjectInputStream oi = new ObjectInputStream(s.getInputStream());

            while (true){
                /*
                 * Vector<String> vs = new Lecture().lire();
                 * // System.out.println(vc.size());
                 * for (int k = 0; k < vs.size(); k++) {
                 * m.placer(new Coordo(
                 * Integer.parseInt(vs.get(k).split("//")[0].split(",")[0]),
                 * Integer.parseInt(vs.get(k).split("//")[0].split(",")[1])),
                 * Integer.parseInt(vs.get(k).split("//")[1]));
                 * }
                 * m.setting()
                 */
            }

        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }
}
