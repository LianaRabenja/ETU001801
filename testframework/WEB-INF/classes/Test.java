package test;


import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import etu1801.framework.FileUpload;
import etu1801.framework.ModelView;
import etu1801.framework.annotation.Urls;
import etu1801.framework.annotation.Auth;
import etu1801.framework.annotation.restAPI;
import etu1801.framework.annotation.UseSession;


public class Test {

    private int id;
    private String nom;
    private HashMap<String, Object> session;

    @UseSession
    @restAPI
    @Urls( url = "useSession.do")
    public List<Test> useSession() {
        List<Test> test = new ArrayList<>();

        for(String s : session.keySet()) {
            Test a = new Test();
            a.setId(0);
            a.setNom(session.get(s).toString());
            test.add(a);
        }

        return test;
    }

    @restAPI
    @Urls( url = "rest.do")
    public HashMap restAPI() {
        HashMap<String, Object> test = new HashMap<>();
        
        test.put("pers1", 123);
        test.put("pers2", 123);
        test.put("pers3", 123);

        return test;
    }

    @restAPI
    @Urls( url = "rest1.do")
    public Object[] restObject() {
        Object[] result = new Object[3];
        
        result[0] = "Rabenja";
        result[1] = "Liana";
        result[2] = 1801;

        return result;
    }


    @Urls( url = "login.do", paramName="profil")
    public ModelView test(String profil) {
        ModelView mv = new ModelView();

        mv.setView("/index.jsp");
        mv.addSession("userProfil", profil);

        return mv;
    }

    @Urls( url = "deco.do")
    public ModelView deco() {
        ModelView mv = new ModelView();

        mv.setView("/index.jsp");
        mv.removeSession("userProfil");

        return mv;
    }
    
    @Auth( profil = "admin")
    @Urls( url = "save.do")
    public ModelView save() {
        ModelView mv = new ModelView();

        mv.setView("/test.jsp");
        mv.addItem("obj", this);
        mv.setJson(true);

        return mv;
    }

    @Auth( profil = "admin,simple")
    @Urls( url = "setNewTest.do", paramName = "identifiant,name")
    public ModelView newTest(int identifiant, String name) {
        ModelView mv = new ModelView();

        this.setId(identifiant);
        this.setNom(name); 

        mv.setView("/test.jsp");
        mv.addItem("obj", this);

        return mv;
    }

    @Urls( url = "upload.do", paramName = "fu")
    public ModelView upload(FileUpload fu) {
        ModelView mv = new ModelView();

        this.setId(0);
        this.setNom("Elyse"); 

        mv.setView("/test.jsp");
        mv.addItem("obj", this);

        return mv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public HashMap<String, Object> getSession() {
        return session;
    }

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }
    
}