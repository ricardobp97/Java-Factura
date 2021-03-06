import java.util.List;
import java.util.ArrayList;

public class Empresa extends Actor {
    private double fator;             //fator que a empresa tem no cálculo de dedução fiscal
    private List<String> atividades;   //Atividades em que a empresa atua
    
    public Empresa () {
        super();
        this.fator = 0;
        this.atividades = new ArrayList<>();
    }
    
    public Empresa (Empresa e) {
       super(e);
       this.fator = e.getFator();
       this.atividades = e.getAtiv();
    }
    
    public Empresa (int nif, String email, String nome,String morada, String pass, double fator, List<String> atividades) {
        super(nif,email,nome,morada,pass);
        this.fator = fator;
        this.atividades = new ArrayList<String>(atividades);
    }
    
    public double getFator () {
        return this.fator;
    }
    
    public List<String> getAtiv () {
        return new ArrayList(this.atividades);
    }
    
    public void setFator (double fator) {
        this.fator = fator;
    }
    
    public void setAtiv (List<String> ativ) {
        this.atividades = new ArrayList<String>();
        for(String a : ativ)
            this.atividades.add(a);
    }
    
     public boolean equals (Object e) {
        if(e == this)
            return true;
        if(e == null || e.getClass() != this.getClass())
            return false;
        Empresa p = (Empresa)e;
        return (super.equals(p) && this.fator == p.getFator()
               && this.atividades.equals(p.getAtiv()));
    }
    
    public Empresa clone () {
        return new Empresa(this);
    }
    
    public String toString(){
        return super.toString() + ";\nFator de Dedução Fiscal: " + this.fator + ";\n Atividades: " + atividades.toString() + "\n";
    }
}