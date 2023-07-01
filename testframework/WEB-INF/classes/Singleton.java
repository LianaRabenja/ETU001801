package test;


import etu1801.framework.annotation.Scope;
import etu1801.framework.type.ScopeType;
import etu1801.framework.ModelView;
import etu1801.framework.annotation.Urls;

import test.Test;

@Scope( type = ScopeType.SINGLETON)
public class Singleton {
    private int test;

    @Urls( url = "s.do")
    public ModelView save() {
        ModelView mv = new ModelView();

        Test t = new Test();
        t.setId(test);
        t.setNom("Singleton");

        mv.setView("/test.jsp");
        mv.addItem("obj", t);


        test++;

        return mv;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }
    
}
