import java.util.GregorianCalendar;
import java.lang.StringBuilder;
import java.io.*;

public class Fatura implements Serializable{
    private int id;            //Identificador da fatura
    private int nifE;          //NIF emitente
    private String designacao; //Designação do emitente
    private GregorianCalendar data; //Data da despesa -> Posteriormente pode-se alterar para LocalDate
    private int nifC;          //NIF cliente
    private String descricao;  //Descrição da despesa
    private String atividade;  //Actividade económica a que diz respeito a despesa
    private double valor;      //Valor da despesa
    private String historico;
    
    public Fatura () {
        this.id = 0;
        this.nifE = 0;
        this.designacao = "N/A";
        this.data = new GregorianCalendar();
        this.nifC = 0;
        this.descricao = "N/A";
        this.atividade = "N/A";
        this.valor = 0;
        this.historico = "N/A";
    }
    
    public Fatura (int ne, String des, GregorianCalendar d, int nc, String desc,String a, String hist, double v) {
        this.id = 0;
        this.nifE = ne;
        this.designacao = des;
        this.data = d;
        this.nifC= nc;
        this.descricao = desc;
        this.atividade = a;
        this.valor = v;
        this.historico = hist;
    }
    
    public Fatura (Fatura f) {
        this.id = f.getId();
        this.nifE = f.getNifE();
        this.data = f.getData();
        this.designacao = f.getDesignacao();
        this.nifC = f.getNifC();
        this.descricao = f.getDescricao();
        this.atividade = f.getAtividade();
        this.valor = f.getValor();
        this.historico = f.getHistorico();
    }
    
    public int getId () {
        return this.id;
    }
    
    public int getNifE () {
        return this.nifE;
    }
    
    public GregorianCalendar getData () {
        return this.data;
    }
    
    public String getDesignacao () {
        return this.designacao;
    }
    
    public int getNifC () {
        return this.nifC;
    }
    
    public String getDescricao () {
        return this.descricao;
    }
    
    public String getAtividade () {
        return this.atividade;
    }
    
    public double getValor () {
        return this.valor;
    }
    
    public String getHistorico () {
        return this.historico;
    }
    
    public void setHistorico (String h) {
        this.historico = h;
    }
    
    
    public void setId (int id) {
        this.id = id;
    }
    
    public void setNifE (int nifE) {
        this.nifE = nifE;
    }
    
    public void setData (GregorianCalendar data) {
        this.data = data;
    }
    
    public void setDesignacao (String designacao) {
        this.designacao = designacao;
    }
    
    public void setNifC (int nifC) {
        this.nifC = nifC;
    }
    
    public void setDescricao (String descricao) {
        this.descricao = descricao;
    }
    
    public void setAtividade (String atividade) {
        this.atividade = atividade;
    }
    
    public void setValor (double valor) {
        this.valor = valor;
    }
    
    public boolean equals (Object o) {
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        Fatura f = (Fatura) o;
        return (this.id == f.getId() && this.nifE == f.getNifE() &&
                this.designacao.equals(f.getDesignacao()) &&
                this.data.equals(f.getData()) && this.nifC == f.getNifC() &&
                this.descricao.equals(f.getDescricao()) &&
                this.atividade.equals(f.getAtividade()) &&
                this.valor == f.getValor() &&
                this.historico.equals(f.getHistorico()));
    }
    
    public Fatura clone () {
        return new Fatura(this);
    }
    
    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("NIF Emitente: ");
        sb.append(this.nifE);
        sb.append("\n");
        sb.append("Designação: ");
        sb.append(this.designacao);
        sb.append("\n");
        sb.append("Data: ");
        sb.append(this.data.get(3) + "-" + this.data.get(2) + "-" + this.data.get(1));
        sb.append("\n");
        sb.append("NIF Cliente: ");
        sb.append(this.nifC);
        sb.append("\n");
        sb.append("Descrição: ");
        sb.append(this.descricao);
        sb.append("\n");
        sb.append("Atividade: ");
        sb.append(this.atividade);
        sb.append("\n");
        sb.append("Valor: ");
        sb.append(this.valor);
        sb.append("\n");
        sb.append("Histórico: ");
        sb.append(this.historico);
        
        sb.append("\n");
        return sb.toString();
    }
}