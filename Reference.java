package repere;
public class Reference {
    int x;
    int y;
    int idt;

    public int getX(){
        return this.x;
    }
    public void setX(int a){
        x=a;
    }
    
    public int getY(){
        return this.y;
    }
    public void setY(int b){
        y=b;
    }

    public int getIdt(){
        return this.idt;
    }
    public void setIdt(int id){
        idt=id;
    }

    public Reference(int a,int b,int id){
        setX(a);
        setY(b);
        setIdt(id);
    }
}

