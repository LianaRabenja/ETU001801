package util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;


public class Util {

    public List<Class<?>> getAllClass(String absolute_path_tomcat, String dir_classes) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        File directory = new File(absolute_path_tomcat);

        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                List<Class<?>> innerClasses = this.getAllClass(file.getAbsolutePath(), dir_classes);
                classes.addAll(innerClasses);
            } else if (file.getName().endsWith(".class")) {
                String absolute_path_class = file.getPath().replace("\\", "/");
                int tom_int_path = absolute_path_class.indexOf(dir_classes);

                String className = absolute_path_class
                        .substring(tom_int_path + dir_classes.length())
                        .replace(".class", "")
                        .replace("/", ".");
                Class<?> clazz = Class.forName(className);

                classes.add(clazz);
            }
        }
        return classes;
    }

    public Object castPrimaryType(String data, Class<?> type) throws ParseException {
        if(data == null || type == null) return null;
        if(data.equals("")) {
            if(type.equals(Date.class) || type.equals(String.class)) return null;
            else return 0;
        }

        if (type.equals(Date.class)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return type.cast(format.parse(data));
        }else if(type.equals(int.class)) return Integer.parseInt(data);
        else if(type.equals(float.class)) return Float.parseFloat(data);
        else if(type.equals(double.class)) return Double.parseDouble(data);
        else if(type.equals(boolean.class)) return Boolean.getBoolean(data);

        return data;
    }



    public boolean isIn(String[] data, String find) {
        for (String s : data) {
            if(s.trim().equals(find.trim())) return true;
        }
        return false;
    }

    public String processUrl(String url_input, String ctx) {
        ctx+="/";
        int ctx_ind = url_input.indexOf(ctx);

        return url_input.substring(ctx_ind + ctx.length());
    }

    public String casse(String input) {
        char[] strrep = input.toCharArray();
        strrep[0] = Character.toUpperCase(strrep[0]);

        return new String(strrep);
    }

}
