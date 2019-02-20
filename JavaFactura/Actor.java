import java.io.Serializable;

public class Actor implements Serializable{
    private int nif;
    private String email;
    private String nome;
    private String morada;
    private String pass;
    
    public Actor () {
        this.nif = 0;
        this.email = "N/A";
        this.nome = "N/A";
        this.morada = "N/A";
        this.pass = "N/A";
    }
    
    public Actor (int ni, String e, String n, String m, String p) {
        this.nif = ni;
        this.email = e;
        this.nome = n;
        this.morada = m;
        this.pass = p;
    }
    
    public Actor (Actor a) {
        this.nif = a.getNif();
        this.email = a.getEmail();
        this.nome = a.getNome();
        this.morada = a.getMorada();
        this.pass = a.getPass();    
    }
    
    public  int getNif () {
        return this.nif;
    }
    
    public String getEmail () {
         return this.email;
    }
    
    public String getNome () {
        return this.nome;
    }
    
    public String getMorada () {
        return this.morada;
    }
    
    public String getPass () {
        return this.pass;
    }
    
    public void setNif (int n) {
        this.nif = n;
    }
    
    public void setEmail (String e) {
        this.email = e;
    }
    
    public void setNome (String n) {
        this.nome = n;
    }
    
    public void setMorada (String m) {
        this.morada = m;
    }
    
    public void setPass (String p) {
        this.pass = p;
    }
    
    public boolean equals (Object o) {
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        Actor a = (Actor) o;
        return (this.nif == a.getNif() && this.email.equals(a.getEmail())
                && this.nome.equals(a.getNome()) && this.morada.equals(a.getMorada())
                && this.pass.equals(a.getPass()));
    }
    
    public Actor clone () {
        return new Actor(this);
    }
    
    public String toString () {
        return "NIF: " + this.nif + ";\nEmail: " + this.email + ";\nNome: " + 
            this.nome + ";\nMorada: " + this.morada + ";\nPassword: " + this.pass;
    }
}