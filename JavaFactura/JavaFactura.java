import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.io.*;

public class JavaFactura implements Serializable {
    private Actor ator;
    private HashMap<Integer,Contribuinte> contribuintes;
    private HashMap<Integer,Empresa> empresas;
    private HashMap<Integer,Fatura> faturas;
    private int numFaturas;
    
    public JavaFactura () {
        this.ator = null;
        this.contribuintes = new HashMap<>();
        this.empresas = new HashMap<>();
        this.faturas = new HashMap<>();
        this.numFaturas = this.faturas.size();
    }
    
    public int getNumFaturas(){
        return this.numFaturas;
    }
   
    public void setNumFaturas(int n){
        this.numFaturas = n;
    }
    
     public HashMap<Integer,Fatura> getFaturas() {
         return this.faturas;
    }
    
    public void iniciaSessao (int nif, String pass, int i) throws InputMismatchException {
        Scanner output = new Scanner(System.in);
        if(i==1)
            ator = contribuintes.get(nif);
        if(i==2)
            ator = empresas.get(nif);
        if(ator != null){
            if(!ator.getPass().equals(pass)){
                ator = null;
                throw new InputMismatchException("Password incorreta!");
            }
        }
        else{
            if(i==1)
                throw new InputMismatchException("O nif " +nif+ " não está registado como contribuinte!");
            else
                throw new InputMismatchException("O nif " +nif+ " não está registado como empresa!");
        }
        System.out.println("\nSessão iniciada com sucesso!");
        System.out.println("\n (Pressione para continuar)");
        output.nextLine();
        output.close();
    }
    
    public void registarContribuinte (Contribuinte c) throws ContribuinteExistenteException {
        if(contribuintes.containsKey(c.getNif()))
            throw new ContribuinteExistenteException();
            
     
        else{
            contribuintes.put(c.getNif(),c);
            System.out.println("\nContribuinte registado com sucesso!");
        }
    
    }
    
    public void registarEmpresa (Empresa e) throws EmpresaExistenteException {
        if(empresas.containsKey(e.getNif()))
            throw new EmpresaExistenteException();
        else{
            empresas.put(e.getNif(),e);
            System.out.println("\nEmpresa registada com sucesso!");
        }
    }
    
        public void registarFatura (Fatura f) throws FaturaExistenteException {
        if(faturas.containsKey(f.getId()))
            throw new FaturaExistenteException();
        else {
            faturas.put(f.getId(),f);
            this.numFaturas++;
            System.out.println("\nFatura registada com sucesso!");
        }
    }
    
     public double totalEmpresa (int nif,GregorianCalendar begin, GregorianCalendar end) {
        double soma = 0;
        for(Fatura f: faturas.values()) {
            if((f.getNifE() == nif) && (f.getData().after(begin)) && (f.getData().before(end)))
                 soma = soma + f.getValor();
        }
        return soma; 
    }
    
    public double montanteDeducaoPorFatura (Fatura f) { // supondo que o montante de dedução é apenas o valor da despesa multiplicado pelo valor de dedução da atividade
        double montante = 0;
         switch(f.getAtividade()) {
            case "Educacao" : 
                montante = f.getValor() * 0.30;
                break;
             
            case "Saude" : 
                montante = f.getValor() * 0.15;
                break;
             
            case "Geral" :
                montante = f.getValor() * 0.35;
                break;
                
            case "Habitacao" :
                montante = f.getValor() * 0.15;   
        }
        return montante;
    }
        
    public double montanteDeducaoPorListagemFatura (List<Fatura> fatur) { // supondo que o montante de dedução é apenas o valor da despesa multiplicado pelo valor de dedução da atividade
         int i = 0;
         double montante = 0;
         for(i=0; i < fatur.size();i++)
             montante = montante + montanteDeducaoPorFatura(fatur.get(i));
         return montante;
    }
        
     /**
     * Método que obtém as faturas de um contribuinte individual.
     */
    public ArrayList<Fatura> listagemFaturasIndividual (int nif) {
        ArrayList listagem = new ArrayList();
        for(Fatura f: faturas.values()){
            if(f.getNifC() == nif)
                 listagem.add(f.clone());
        }
        return listagem;
    }
    
    
    /**
     * Método que obtém as faturas de uma empresa.
     */
    public ArrayList<Fatura> listagemFaturasEmpresa (int nif) {
        ArrayList listagem = new ArrayList();
        for(Fatura f: faturas.values()){
            if(f.getNifE() == nif)
                 listagem.add(f.clone());
        }
        return listagem;
    }
    
     /**
     * Método que obtém uma empresa.
     */
    public Empresa getEmpresa(int nif) throws EmpresaExistenteException {
        Empresa empresa = new Empresa();
        if(!(empresas.containsKey(nif)))
            throw new EmpresaExistenteException();
        for(Empresa e: empresas.values()) {
            if (e.getNif() == nif)
                empresa = e;
        }
        return empresa;
    }
    
    /**
     * Método que obtém uma fatura.
     */
    public Fatura getFatura(int nif) throws FaturaExistenteException {
        Fatura fatura = new Fatura();
        if(!(faturas.containsKey(nif)))
            throw new FaturaExistenteException(String.valueOf(nif));
        for(Fatura f: faturas.values()) {
            if (f.getId() == nif)
                 fatura = f;
        }
        return fatura;
    }
    
    public Contribuinte getContribuinteIndividual(int nif) throws ContribuinteExistenteException  {
        Contribuinte contr = new Contribuinte();
        if(!(contribuintes.containsKey(nif)))
            throw new ContribuinteExistenteException();
        for(Contribuinte c: contribuintes.values()){
            if (c.getNif() == nif)
                contr = c;
        }
        return contr;
    }
    
    public boolean containsContribuinteIndividual(int nif) {
        if(contribuintes.containsKey(nif))
            return true;
        else
            return false;
    }

    public boolean containsContribuinteEmpresa(int nif) {
        if(empresas.containsKey(nif))
            return true;
        else
            return false;
    }
    
    
    public boolean containsFatura(int id) {
        if(faturas.containsKey(id))
            return true;
        else
            return false;
    }
    
    
    
    /**
     * Método que obtém por parte das empresas, as listagens das facturas por contribuinte num determinado intervalo de datas.
     */
 
    public Map<Integer,ArrayList<Fatura>> listFaturasPorContribuinte (Empresa e, GregorianCalendar begin, GregorianCalendar end) {
        Map<Integer,ArrayList<Fatura>> mapa = new HashMap<Integer,ArrayList<Fatura>>();
        ArrayList<Fatura> faturasEmpresa = listagemFaturasEmpresa(e.getNif()); // Guarda as faturas em que a empresa está associada
        int i;
        ArrayList<Fatura> faturasCliente = new ArrayList<>();
        ArrayList<Fatura> faturasClienteEmpresa = new ArrayList<>();
        for(i=0; i < faturasEmpresa.size(); i++) {
            Fatura f = faturasEmpresa.get(i);
            faturasCliente = listagemFaturasIndividual(f.getNifC());  // todas as faturas do cliente
            faturasClienteEmpresa = faturasEmpresaClienteDatas(faturasCliente, e.getNif(),begin,end); // faturas relacionadas com o cliente e a empresa
            mapa.put(f.getNifC(),faturasClienteEmpresa);
        }
        return mapa;
    }
    
    
    /**
     * Método que guarda em um ArrayList as faturas em comum de um empresa e de um contribuinte numa determinada data. Este método recebe
     * as faturas de um cliente, o nif da empresa, a data de início e a data do fim.
     */
    public ArrayList<Fatura> faturasEmpresaClienteDatas(ArrayList<Fatura> faturas, int nifEmpresa, GregorianCalendar begin, GregorianCalendar end ) {
        ArrayList<Fatura> array = new ArrayList<>();
        for(int i=0; i < faturas.size(); i++) { 
            Fatura f = faturas.get(i);
            if ((f.getNifE() == nifEmpresa) && (f.getData().after(begin)) && (f.getData().before(end)))
                array.add(f);
        }
        return array;
    }
    
    
    
    /**
     * Método que guarda em um ArrayList as faturas em comum de um empresa e de um contribuinte. Este método recebe as faturas de um cliente e o nif da empresa.
     */
    public ArrayList<Fatura> faturasEmpresaCliente(ArrayList<Fatura> faturas, int nifEmpresa) {
        ArrayList<Fatura> array = new ArrayList<>();
        for(int i=0; i < faturas.size(); i++) { 
            Fatura f = faturas.get(i);
            if (f.getNifE() == nifEmpresa)
                array.add(f);
        }
        return array;
    }
    
    
  
    
    /**
     * Método que óbtem por parte das empresas, as listagens das facturas por contribuinte ordenadas por valor decrescente de despesa.
     */
    public Map<Integer,ArrayList<Fatura>> listFaturasPorContribuinteOrdenado (Empresa e) {
        Map<Integer,ArrayList<Fatura>> mapa = new HashMap<Integer,ArrayList<Fatura>>();
        ArrayList<Fatura> faturasEmpresa = listagemFaturasEmpresa(e.getNif()); // Guarda as faturas em que a empresa está associada
        ArrayList<Fatura> faturasCliente = new ArrayList<>();                 // Guarda as faturas do cliente 
        ArrayList<Fatura> faturasClienteEmpresa = new ArrayList<>();          //Guarda as faturas em que a empresa e o cliente estão associados 
        int i;
        for(i=0; i < faturasEmpresa.size(); i++) {
            Fatura f = faturasEmpresa.get(i);
            faturasCliente = listagemFaturasIndividual(f.getNifC());  // todas as faturas do cliente
            faturasClienteEmpresa = faturasEmpresaCliente(faturasCliente, e.getNif()); // faturas que os clientes e as empresas têm em comum
            Collections.sort (faturasClienteEmpresa, new ComparadorDeFaturasDecrescente()); 
            mapa.put(f.getNifC(),faturasClienteEmpresa); // Guarda no map o nif do cliente e a sua lista de faturas ordenada por ordem decrescente         
        }
        return mapa;
    }
    
    
    /**
     * Método que óbtem quanto é que um contribuinte individual gasta.
     */
    public double getQuantoGasta(int nif) {
         double soma = 0;
         ArrayList<Fatura> faturasCliente = listagemFaturasIndividual(nif);
         int i;
         for(i=0; i < faturasCliente.size(); i++)
             soma = soma + faturasCliente.get(i).getValor();
         return soma;
    }
    
    
    
    public class ContribuinteQuantoGasta implements Comparator<Contribuinte> {
        public int compare(Contribuinte c1,Contribuinte c2) {
            if(getQuantoGasta(c1.getNif()) > getQuantoGasta(c2.getNif()))
                return -1;
            if(getQuantoGasta(c1.getNif()) < getQuantoGasta(c2.getNif()))
                return 1;
            return 0;
        }
    }
    
    
    
    public class EmpresaMaisFaturas implements Comparator<Empresa> {
        public int compare(Empresa e1,Empresa e2) {
            if(listagemFaturasEmpresa(e1.getNif()).size() > listagemFaturasEmpresa(e2.getNif()).size())
                return -1;
            if(listagemFaturasEmpresa(e1.getNif()).size() < listagemFaturasEmpresa(e2.getNif()).size())
                return 1;
            return 0;
        }
    }
    
    
    
    
    /**
     * Método que óbtem os dez contribuintes que mais gastam. 
     */
    
    public void dezQueMaisGastam() {
        List<Contribuinte> contribuintesIndividuais = new ArrayList<Contribuinte>();
        int i;
        contribuintesIndividuais = contribuintes.values().stream().sorted(new ContribuinteQuantoGasta()).limit(10).collect(Collectors.toList());
        System.out.println("Clientes que mais gastam:\n");
        for(Contribuinte cc : contribuintesIndividuais) {
            System.out.println("O contribuinte  " + cc.getNif() + " gastou  " + getQuantoGasta(cc.getNif()));
        }
    }
    
    
    /**
     * Método que através do nif da empresa, calcula o total da dedução fiscal que as faturas associadas a ela têm.
     */
    public double getDeducaoTotalEmpresa(int nif) throws EmpresaExistenteException {
        List<Fatura> faturas = new ArrayList<Fatura>();
        
        double montante = 0;
        
        faturas = listagemFaturasEmpresa(nif);
        
        montante = montanteDeducaoPorListagemFatura(faturas) * getEmpresa(nif).getFator();
        
        return montante;
    }
    
    
    /**
     * Método que imprime as X empresas com mais faturas e o montante de deduções fiscais que as despesas registadas representam.
     */
    
    public void xEmpresas(int x) throws EmpresaExistenteException {
        List<Empresa> xEmpresa = new ArrayList<Empresa>();
        xEmpresa = empresas.values().stream().sorted(new EmpresaMaisFaturas()).limit(x).collect(Collectors.toList());
        System.out.println(x + " empresas com mais faturas:\n");
        for(Empresa ee : xEmpresa) {
            System.out.println("A empresa " + ee.getNome() + " tem  " + listagemFaturasEmpresa(ee.getNif()).size() + " faturas!\n");
            System.out.println("E o montante de deduções fiscais que as despesas registadas representam é: " + getDeducaoTotalEmpresa(ee.getNif()) + "\n");
          
        }
    }
    
    /**
     * Método que carrega o estado da aplicação a partir de um ficheiro de objetos.
     * 
     * @param nome do ficheiro onde está guardado um objecto do tipo JavaFactura
     * @return objecto JavaFactura inicializado
     */
    public static JavaFactura iniciaApp (String nomeFicheiro) throws FileNotFoundException,
                                                        IOException, ClassNotFoundException {
                                       
         FileInputStream fis = new FileInputStream(nomeFicheiro);
         ObjectInputStream ois = new ObjectInputStream(fis);
         JavaFactura j = (JavaFactura) ois.readObject();
         ois.close();
         return j;
    }
    
    /**
     * Método que guarda estado da aplicação num ficheiro de texto
     * 
     * @param nome do ficheiro txt de saída
     */
    
    public void guardarFicheiroTxt (String nomeFicheiro) throws IOException {
        PrintWriter fich = new PrintWriter(nomeFicheiro);
        fich.println("------ JavaFactura ------");
        fich.println(this.toString());
        fich.flush();
        fich.close();
    }
    
    /**
     * Método que guarda estado da aplicação num ficheiro de objetos
     * 
     * @param nome do ficheiro de objetos de saída
     */
    
    public void guardarEstado(String nomeFicheiro) throws FileNotFoundException, IOException{
        FileOutputStream fos = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("JavaFactura");
        sb.append("\n" + "--->Contribuintes:\n");
        sb.append(contribuintes.toString());
        
        
        sb.append("\n" + "--->Empresas:\n");
        sb.append(empresas.toString());
       

        sb.append("\n" + "--->Facturas:\n");
        sb.append(faturas.toString());
        

        return sb.toString();
    }
}