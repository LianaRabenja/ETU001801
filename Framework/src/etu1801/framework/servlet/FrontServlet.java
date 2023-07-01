package etu1801.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import etu1801.framework.FileUpload;
import etu1801.framework.annotation.*;
import etu1801.framework.type.ScopeType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;

import etu1801.framework.Mapping;
import util.Util;
import etu1801.framework.ModelView;

@MultipartConfig()
public class FrontServlet extends HttpServlet {
    private PrintWriter out;

    HashMap<String, Mapping> mappingUrls;
    HashMap<String, Object> singleton;
    private Util util;
    private String sessionVariable;

    @Override
    public void init() throws ServletException {
        super.init();
        try {

            util = new Util();
            mappingUrls = new HashMap<>();
            singleton = new HashMap<>();

            sessionVariable = getInitParameter("session");

            String dir_classes = "/WEB-INF/classes/";
            String absolute_path_tomcat = getServletContext().getRealPath(dir_classes);
            loadMapping(absolute_path_tomcat, dir_classes, mappingUrls, singleton);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURL().toString();
        url = util.processUrl(url, request.getContextPath());

        try {
            Mapping map = mappingUrls.get(url);

            if (map == null) throw new Exception("Not Found");

            ModelView mv = invokeMethod(request, map, singleton, sessionVariable);
            if(!mv.isJson()){
                setAttributeRequest(request, mv);
                request.getRequestDispatcher(mv.getView()).forward(request, response);
            }else {
                out = response.getWriter();
                out.print(mv.toJson());
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {

        } catch (ServletException | IOException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setAttributeRequest(HttpServletRequest request, ModelView mv) {
        HashMap<String, Object> donne = mv.getData();
        for(String key : donne.keySet()) {
            request.setAttribute(key, donne.get(key));
        }

        HashMap<String, String> session = mv.getSession();
        for (String key : session.keySet()) {
            request.getSession().setAttribute(key, session.get(key));
        }

        List<String> removeSession = mv.getRemoveSession();
        for (String s : removeSession) {
            request.getSession().removeAttribute(s);
        }

        if(mv.isInvalidateSession()) {
            Enumeration<String> sessionName = request.getSession().getAttributeNames();
            while (sessionName.hasMoreElements()) {
                request.getSession().removeAttribute(sessionName.nextElement());
            }
        }
    }

    public ModelView invokeMethod(HttpServletRequest request, Mapping mapping, HashMap<String, Object> singleton, String session) throws Exception {
        ArrayList<Class<?>> type = new ArrayList<>();
        ArrayList<Object> value = new ArrayList<>();
        setArgValue(request, mapping, type, value);

        Object o = setObjectByRequest(request, mapping, singleton);

        Method m = o.getClass().getMethod(mapping.getMethod(), type.toArray(Class[]::new));

        if(m.isAnnotationPresent(UseSession.class)) setSessionInUseFonction(request, o);

        if(!m.isAnnotationPresent(restAPI.class)) {
            if(m.isAnnotationPresent(Auth.class)){
                String[] allPermission = m.getAnnotation(Auth.class).profil().split(",");
                String userPermission = String.valueOf(request.getSession().getAttribute(session));
                if (util.isIn(allPermission, userPermission)) {
                    return (ModelView) m.invoke(o, value.toArray(Object[]::new));
                }else throw new Exception("Permission denied");
            }else return (ModelView) m.invoke(o, value.toArray(Object[]::new));
        } else {
            ModelView mv = new ModelView();
            mv.addItem("objectDataResultFramework", m.invoke(o, value.toArray(Object[]::new)));
            mv.setJson(true);
            mv.setApi(true);
            return mv;
        }
    }

    public Object setObjectByRequest(HttpServletRequest request, Mapping map, HashMap<String, Object> singleton) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, ServletException, IOException, ParseException {
        Object o = singleton.get(map.getClassName());
        if(o == null) {
            Class<?> clazz = Class.forName(map.getClassName());
            o = clazz.getDeclaredConstructor().newInstance();
        }
        this.initObject(o);

        Field[] allField = o.getClass().getDeclaredFields();
        String field_name;
        Object value_temp;
        Object value;

        for(Field f : allField) {
            field_name = f.getName();
            value_temp = (f.getType().equals(FileUpload.class)) ? getValueUploadedFile(request, field_name) : request.getParameter(field_name);

            if(value_temp != null) {
                try {
                    if(!f.getType().equals(FileUpload.class)) value = util.castPrimaryType(value_temp.toString(), f.getType());
                    else value = value_temp;
                    o.getClass()
                            .getMethod("set"+util.casse(field_name), f.getType())
                            .invoke(o, value);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return o;
    }

    private void initObject(Object o) throws IllegalAccessException, ParseException {
        for (Field field : o.getClass().getDeclaredFields()) {

            if(field.getName().equals("session")) continue;

            field.setAccessible(true);
            field.set(o, util.castPrimaryType("", field.getType()));
            field.setAccessible(false);
        }
    }

    public void setArgValue(HttpServletRequest request, Mapping mapping, ArrayList<Class<?>> type, ArrayList<Object> value) throws Exception {
        Method m = this.getMethodByClassName(mapping.getClassName(), mapping.getMethod());

        if(m.isAnnotationPresent(Urls.class) && !m.getAnnotation(Urls.class).paramName().equals("") ) {
            type.addAll(List.of(m.getParameterTypes()));

            String[] paramName = m.getAnnotation(Urls.class).paramName().split(",");

            if(paramName.length != type.size()) throw new Exception("Nombre d'argument incompatible");

            String value_temp;
            for (int i=0; i< paramName.length; i++) {
                value_temp = request.getParameter(paramName[i].trim());
                value.add(util.castPrimaryType(value_temp, type.get(i)));
            }
        }
    }

    private static FileUpload getValueUploadedFile(HttpServletRequest request, String field_name) throws ServletException, IOException {
        Part filePart = request.getPart(field_name);
        FileUpload result = new FileUpload();
        result.setName(filePart.getSubmittedFileName());
        result.setFile(filePart.getInputStream().readAllBytes());

        return result;
    }

    public void setSessionInUseFonction(HttpServletRequest request, Object o) throws Exception {
        HashMap<String, Object> session = sessionToHashmap(request.getSession());

        try {
            o.getClass().getMethod("setSession", HashMap.class).invoke(o, session);
        } catch (NoSuchMethodException e) {
            throw new Exception("Declarative de session incompatible");
        }
    }

    public HashMap<String, Object> sessionToHashmap(HttpSession session) {
        HashMap<String, Object> result = new HashMap<>();

        Enumeration<String> keys = session.getAttributeNames();
        String next;
        while (keys.hasMoreElements()) {
            next = keys.nextElement();
            result.put(next, session.getAttribute(next));
        }

        return result;
    }

    public Method getMethodByClassName(String className, String method) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(className);
        Object o = clazz.getDeclaredConstructor().newInstance();

        Method result = null;
        Method[] allMethod = o.getClass().getDeclaredMethods();
        for (Method m : allMethod) {
            if(m.getName() .equals(method)) {
                result = m;
                break;
            }
        }
        return result;
    }

    public void loadMapping(String absolute_path_tomcat, String dir_classes, HashMap<String, Mapping> mappingUrls, HashMap<String, Object> singleton) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Class<?>> allClass = util.getAllClass(absolute_path_tomcat, dir_classes);
        Mapping mapping;
        Method[] allMethods;

        for(Class<?> c : allClass) {
            allMethods = c.getMethods();

            if (c.isAnnotationPresent(Scope.class)) {
                if (c.getAnnotation(Scope.class).type().equals(ScopeType.SINGLETON)){
                    Class<?> clazz = Class.forName(c.getName());
                    Object temp = clazz.getDeclaredConstructor().newInstance();
                    singleton.put(c.getName(), temp);
                }
            }

            for(Method m : allMethods) {
                if(m.isAnnotationPresent(Urls.class)) {
                    mapping = new Mapping();
                    mapping.setClassName(c.getName());
                    mapping.setMethod(m.getName());
                    mappingUrls.put(m.getAnnotation(Urls.class).url(), mapping);
                }
            }
        }
    }
}
