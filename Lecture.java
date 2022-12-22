package lecture;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import pt.Coordo;

public class Lecture {
    public Vector<String> lire() {
        Vector<String> v = new Vector<>();
        File file = new File("Data.txt");
        try {
            FileReader read = new FileReader(file);
            BufferedReader br = new BufferedReader(read);
            String lin = br.readLine();
            while (lin != null) {
                v.add(lin);
                lin = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public void Ecrire(Vector<Coordo> obj, int id) {
        try {
            File file = new File("Data.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter write;
            FileReader read;
            write = new FileWriter(file, true);
            read = new FileReader("Data.txt");
            BufferedReader r_donnee = new BufferedReader(read);
            BufferedWriter donnee = new BufferedWriter(write);
            String verif = r_donnee.readLine();
            if (verif != null) {
                donnee.write("\n");
            }
            // for (int i = 0; i < obj.size(); i++) {
            donnee.write(
                    String.valueOf(obj.get(obj.size() - 1).getX()) + ","
                            + String.valueOf(obj.get(obj.size() - 1).getY()) + "//" + String.valueOf(id));
            // }
            donnee.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}