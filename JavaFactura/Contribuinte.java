import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Contribuinte extends Actor {
    private int numAgregado;              //numero de dependentes do agregado familiar

    private ArrayList<Integer> nifsAgregados;  //numeros fiscais do agregado familiar

    private double coefFiscal;            //coeficiente fiscal para efeitos de deducao

    private ArrayList<Integer> codigos;        //codigos das atividades economicas para as
                                          //quais um determinado contribuinte tem
                                          //possibilidade de deduzir despesas
       
    public Contribuinte () {
        super();
        this.numAgregado = 0;
        this.nifsAgregados = new ArrayList<Integer>();
        this.coefFiscal = 0;
        this.codigos = new ArrayList<Integer>();
    }
    
    public Contribuinte (int nif, String e, String nome, String m, String p, int n, ArrayList<Integer> nifs, double c) {
        super(nif,e,nome,m,p);
        this.numAgregado = n;
        this.nifsAgregados = new ArrayList<Integer>();
        for(Integer i: nifs)
            this.nifsAgregados.add(i);
        this.coefFiscal = c;
        this.codigos = new ArrayList<Integer>();
        //for(Integer i: cod)
        //    this.codigos.add(i);
    }
    
    public Contribuinte (Contribuinte c) {
        super(c);
        this.numAgregado = c.getNumAgregado();
        this.nifsAgregados = c.getNifsAgregados();
        this.coefFiscal = c.getCoefFiscal();
        this.codigos = c.getCodigos();
    }
    
    public int getNumAgregado () {
        return this.numAgregado;
    }
    
    public ArrayList<Integer> getNifsAgregados () {
        ArrayList<Integer> aux = new ArrayList<Integer>(this.nifsAgregados.size());
        for(Integer i: this.nifsAgregados)
            aux.add(i);
        return aux;
    }
    
    public double getCoefFiscal () {
        return this.coefFiscal;
    }
    
    public ArrayList<Integer> getCodigos () {
        ArrayList<Integer> aux = new ArrayList<Integer>(this.codigos.size());
        for(Integer i: this.codigos)
            aux.add(i);
        return aux;
    }
    
    public void setNumAgregado (int n) {
        this.numAgregado = n;
    }
    
    public void setNifsAgregados (List<Integer> nifs) {
        ArrayList<Integer> aux = new ArrayList<Integer>(nifs.size());
        for(Integer i: nifs)
            aux.add(i);
        this.nifsAgregados = aux;
    }
    
    public void setCoefFiscal (double c) {
        this.coefFiscal = c;
    }
    
    public void setCodigos (List<Integer> c) {
        ArrayList<Integer> aux = new ArrayList<Integer>(c.size());
        for(Integer i: c)
            aux.add(i);
        this.codigos = aux;
    }
    
    public Contribuinte clone () {
        return new Contribuinte(this);
    }
    
    public boolean equals (Object o) {
        if(o == this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        Contribuinte c = (Contribuinte)o;
        return (super.equals(c) && c.getNumAgregado() == this.numAgregado &&
                c.getNifsAgregados().equals(this.nifsAgregados) &&
                c.getCoefFiscal() == this.coefFiscal && 
                c.getCodigos().equals(this.codigos));
    }
    
    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Numero de Agregados: ");
        sb.append(this.numAgregado);
        sb.append("\n");
        sb.append("Nifs Agregados: ");
        sb.append(this.nifsAgregados);
        sb.append("\n");
        sb.append("Coeficiente Fiscal: ");
        sb.append(this.coefFiscal);
        sb.append("\n");
        sb.append("Codigos: ");
        sb.append(this.codigos);
        sb.append("\n");
        return sb.toString();
    }
}