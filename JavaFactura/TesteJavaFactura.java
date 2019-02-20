
/**
 * CLASSE DE TESTE
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.*;
import java.io.Serializable;
import java.io.*;

public class TesteJavaFactura {
   public static void main(String[] args) throws ContribuinteExistenteException, FaturaExistenteException, EmpresaExistenteException, FileNotFoundException, IOException{
        JavaFactura java = new JavaFactura();
       
        ArrayList<Integer> a = new ArrayList<>();
        a.add(222222222);
        Contribuinte c1 = new Contribuinte(111111111, "Bruno@email", "Bruno Sousa", "Barcelos", "pass1", 2, a, 2.0);
        
		ArrayList<Integer> a2 = new ArrayList<>();
        a2.add(111111111);
        Contribuinte c2 = new Contribuinte(222222222, "Susana@email", "Susana Sousa", "Barcelos", "pass2", 2, a2, 2.0);
        
        ArrayList<Integer> a3 = new ArrayList<>();
        Contribuinte c3 = new Contribuinte(333333333, "Ricardo@email", "Ricardo Pereira", "Braga", "pass3", 1, a3, 1.0);
        
        ArrayList<Integer> a4 = new ArrayList<>();
        a4.add(555555555);a4.add(666666666);
        Contribuinte c4 = new Contribuinte(444444444, "Rafaela@email", "Rafaela Soares", "Braga", "pass4", 1, a4, 3.0);
        
        ArrayList<Integer> a5 = new ArrayList<>();
        a5.add(444444444);a5.add(666666666);
        Contribuinte c5 = new Contribuinte(555555555, "João@email", "João Soares", "Braga", "pass5", 1, a5, 3.0);
        
        ArrayList<Integer> a6 = new ArrayList<>();
        a6.add(444444444);a6.add(555555555);
        Contribuinte c6 = new Contribuinte(666666666, "Maria@email", "Maria Soares", "Braga", "pass6", 1, a6, 3.0);
        
		java.registarContribuinte(c1);java.registarContribuinte(c2);java.registarContribuinte(c3);
		java.registarContribuinte(c4);java.registarContribuinte(c5);java.registarContribuinte(c6);
		
       

        List<String> la1 = new ArrayList<>();
        la1.add("Geral");
        Empresa e1 = new Empresa(777777777, "Geral@emai", "Geral Empresa","Porto", "pass7", 1.5, la1);

	    List<String> la2 = new ArrayList<>();
        la2.add("Habitacao");
        Empresa e2 = new Empresa(888888888, "Imobiliaria@email", "Imobiliária","Porto", "pass8", 5.5, la2);
       
        List<String> la3 = new ArrayList<>();
        la3.add("Educacao");la3.add("Saude");
        Empresa e3 = new Empresa(999999999, "SaudeEducacao@email", "Saúde e Educação Lda.", "Lisboa", "pass9", 6.5, la3);
       
        java.registarEmpresa(e1);
	    java.registarEmpresa(e2);
	    java.registarEmpresa(e3);
       
        GregorianCalendar data1 = new GregorianCalendar(2018,01,26);
        Fatura f1 = new Fatura(999999999, "Saúde e Educação Lda.", data1, 111111111, "N/A", "Tem de ser catalogada","N/A", 50.0);
        f1.setId(1);
        
        GregorianCalendar data2 = new GregorianCalendar(2018,02,29);
		Fatura f2 = new Fatura(888888888, "Imobiliária", data2, 333333333, "Arrendamento", "Habitacao","N/A", 100.0);
		f2.setId(2);
		
		GregorianCalendar data3 = new GregorianCalendar(2018,03,2);
		Fatura f3 = new Fatura(777777777, "Geral Empresa", data3, 444444444, "N/A", "Geral","N/A", 25.0);
		f3.setId(3);
		
		GregorianCalendar data4 = new GregorianCalendar(2018,04,12);
		Fatura f4 = new Fatura(777777777, "Geral Empresa", data4, 222222222, "N/A", "Geral","N/A", 70.0);
		f3.setId(4);
		
		
		java.registarFatura(f1);
		java.registarFatura(f2);
		java.registarFatura(f3);
		java.registarFatura(f4);

     	java.guardarEstado("Teste.obj");
       	java.guardarFicheiroTxt("Teste.txt");
    }
}
