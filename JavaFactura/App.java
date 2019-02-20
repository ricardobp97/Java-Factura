import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.text.SimpleDateFormat;
import java.io.*;

public class App {
    
    private JavaFactura javaFactura;
    
    private static Menu menuInicial, menuContribuinte, menuEmpresa, menuAdministrador;
    
    private App () {
        this.javaFactura = new JavaFactura();
    };
    
    /**
         * Método de arranque da aplicação.
         * Iniciamos a aplicação com o ficheiro de teste onde já temos alguns contribuintes, empresas e faturas criadas, para facilitar a apresentação.
         * Sempre que terminamos sessão, a plataforma guarda o estado da aplicação no ficheiro "Estado.obj". 
         * Se posteriormente quisermos retomar desse ponto, em vez de iniciar com o ficheiro de teste, iniciamos com o ficheiro "Estado.obj"
    */
    
    public static void main (String[] args) throws EmpresaExistenteException, ContribuinteExistenteException,FileNotFoundException, IOException, ClassNotFoundException {
        
        int nifUser;
        App app = new App();
        app.carregarMenus();
        app.javaFactura = app.javaFactura.iniciaApp("Teste.obj");
        //app.javaFactura = app.javaFactura.iniciaApp("Estado.obj");
        
        
        do {
            nifUser = 0;
            menuInicial.executa();
            
            switch (menuInicial.getOpcao()) {
                case 1 :
                    nifUser = app.login(1);
                    if(nifUser != 0 && nifUser != -1)
                        app.menuContribuinte(nifUser);
                    break;
                case 2 :
                    nifUser = app.login(2);
                    if(nifUser != 0 && nifUser != -1)
                        app.menuEmpresa(nifUser);
                    break;
                case 3 :
                    app.registo();
                    break;
                case 4 :
                    nifUser = app.loginAdministrador();
                    if(nifUser==1111)
                        app.menuAdministrador();
                    break;
            }
        } while(menuInicial.getOpcao() != 0);
        
        app.javaFactura.guardarFicheiroTxt("Estado.txt");
        app.javaFactura.guardarEstado("Estado.obj");
    }
    
    
    /**
     * Método que carrega os menus inicial, do contribuinte,da empresa e do administrador.
     * 
     */
    public  void carregarMenus () {
        String[] op1 = {"Iniciar Sessão Contribuinte",
                        "Iniciar Sessão Empresa",
                        "Registar-se",
                        "Iniciar Sessão Administrador"};
        String[] op2 = {" Verificar as despesas emitidas em seu nome, assim como o montante fiscal acumulado por si e pelo seu agregado familiar",
                        "Corrigir a atividade da sua fatura",
                        "Associar atividade económica a uma fatura que esteja por catalogar"};

        String[] op3 = {"Criar Factura",
                        "Verificar o total faturado pela empresa num determinado período de tempo",
                        "Visualizar a listagem das faturas da empresa",
                        "Visualizar as listagens das faturas por contribuinte num determinado intervalo de datas",
                        "Visualizar as listagens das faturas por contribuinte ordenadas por valor decrescente de despesa"
                       };
                       
        String[] op4 = {"Verificar os dez contribuintes que mais gastam",
                        "Verificar a relação das X empresas que têm mais faturas em todo o sistema e o montante de deduções fiscais que as despesas registadas representam"
                        };
                                      
        
        menuInicial = new Menu(op1);
        menuContribuinte = new Menu(op2);
        menuEmpresa = new Menu(op3);
        menuAdministrador = new Menu(op4);
    }
    
    
    /**
     * Método que permite que o login dos administradores. O cógido seria dado ao administrador(definido como 1111).
     */
    private int loginAdministrador() {
        Scanner input = new Scanner(System.in);
        Scanner dados = new Scanner(System.in);
        Scanner output = new Scanner(System.in);
        boolean flag = false;
        int cod = 0;
        
        System.out.println("\n----------- AUTENTICAÇÃO ----------");
        
        System.out.println("Código: ");
        while(!flag) {
            try {
                cod= input.nextInt();
                if (cod == 1111)
                    flag = true;
                else{
                    System.out.println("Código inválido. Tente novamente, por favor."); 
                    System.out.println("Código:");
                    input.nextLine(); 
                }  
            }
            catch(InputMismatchException e) { 
                System.out.println("Código inválido. Tente novamente, por favor."); 
                System.out.println("Código:");
                input.nextLine(); 
            }
        }
        return cod;
    }
    
    /**
     * Método que possibilita o login dos contribuintes individuais e das empresas.
     */
    private int login (int i) {
        int nif = 0;
        
        String password = "";
        
        Scanner input = new Scanner(System.in);
        Scanner dados = new Scanner(System.in);
        Scanner output = new Scanner(System.in);
        boolean flag = false;
        boolean flag1 = false;
        System.out.println("\n----------- AUTENTICAÇÃO ----------");
        
        System.out.println("Nif: ");
        
         
        while(!flag) {
                try {
                    nif= input.nextInt();
                   
                    if  ((Integer.toString(nif).length() == 9) && nif>0) {
                     flag = true;
                    }
                
                    else { 
                       if (nif ==0) {
                        System.out.println("\nRegisto cancelado!");
                        System.out.println("\n (Pressione para continuar)");
                        output.nextLine();
                        output.close();
                        input.close();
                        return -1;                        /* go back */
                       }
                       else {
                        System.out.println("Nif inválido. Tente novamente, por favor"); 
                        System.out.println("Nif inválido:");
                        input.nextLine();
                            }
                     }
                }
                catch(InputMismatchException e) { 
                    System.out.println("Nif inválido. Tente novamente, por favor"); 
                    System.out.println("Nif inválido.");
                    input.nextLine(); 
                }
            }
        
        System.out.println("Password: ");
        
        while(!flag1) {
             try {
                 password = dados.nextLine();
                 flag1 = true;
             }
             catch(InputMismatchException e) 
                 { System.out.println("Password inválida. Por favor, tente novamente.\n"); 
                   System.out.println("Password");
                   dados.nextLine(); 
                 }
         }
         
        
       
        try{
            javaFactura.iniciaSessao(nif,password,i);
            return nif;
        }
        catch(InputMismatchException e){
            System.out.println("\nAutenticação errada!");
        }
        
        System.out.println("\n (Pressione para continuar)");
        output.nextLine();
        output.close();
        input.close();
        return -1;
    }
    
    
    /**
     * Método que permite o registo de um contribuinte individual ou de uma empresa.
     * 
     */
    
    private  void registo () {
         String email = "";
        String nome= "";
        String morada = "";
        String password = "";
        int nif = 0, opcao = 0;
        Scanner input = new Scanner(System.in);
        Scanner dados = new Scanner(System.in);
        Scanner dados1 = new Scanner(System.in);
        Scanner dados2 = new Scanner(System.in);
        Scanner output = new Scanner(System.in);
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        boolean flag5 = false;
        boolean flag6 = false;
        boolean flag7 = false;
        boolean flag8 = false;
        
        System.out.println("\n--------- REGISTO ----------");
        System.out.println("\n (Aperte 0 para cancelar)");
        
        
        System.out.println("\n1 - Contribuinte ou 2 - Empresa?");
            
            while(!flag) {
             try {
                 opcao = input.nextInt();
                 if (opcao==1 || opcao ==2) {
                       flag = true;
                }
                 else { 
                  if (opcao==0) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    input.close();
                    return;                        /* go back */
                  }
                  else {
                      System.out.println("Opção inválida. Tente novamente, por favor."); 
                      System.out.println("Opção:");
                      input.nextLine();
                  }
                }
             }
             catch(InputMismatchException e) { 
                 System.out.println("Opção inválida. Tente novamente, por favor."); 
                 System.out.println("Opção:");
                 input.nextLine(); 
             }
         }
          
        if(opcao==1) {
            
             System.out.println("\nNif: ");
         
         while(!flag1) {
                try {
                    nif= input.nextInt();
                   
                    if  ((Integer.toString(nif).length() == 9) && nif>0) {
                     flag1 = true;
                    }
                
                    else { 
                       if (nif ==0) {
                        System.out.println("\nRegisto cancelado!");
                        System.out.println("\n (Pressione para continuar)");
                        output.nextLine();
                        output.close();
                        input.close();
                        return;                        /* go back */
                       }
                       else {
                        System.out.println("Nif inválido. Tente novamente, por favor"); 
                        System.out.println("Nif inválido:");
                        input.nextLine();
                            }
                     }
                }
                catch(InputMismatchException e) { 
                    System.out.println("Nif inválido. Tente novamente, por favor"); 
                    System.out.println("Nif inválido.");
                    input.nextLine(); 
                }
            }
            
            
             System.out.println("\nEmail: ");
               
             while(!flag2) {
             try {
                 email = dados.nextLine();
                 if (email.equals("0")) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    return;                        /* go back */
                 }
                 else {
                        if(email.equals("")){
                            System.out.println("Email inválido. Por favor, tente novamente.\n"); 
                            System.out.println("Email:");
                            dados.nextLine(); 
                        }
                        else flag2 = true;
                    }
             }
             catch(InputMismatchException e) 
                 { System.out.println("Email inválido. Por favor, tente novamente.\n"); 
                   System.out.println("Email:");
                   dados.nextLine(); 
                 }
         }
         
        
              System.out.println("\nNome: ");
                
              while(!flag3) {
             try {
                 nome = dados.nextLine();
                 if (nome.equals("0")) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    return;                        /* go back */
                 }
                 else {
                        if(nome.equals("")){
                            System.out.println("Nome inválido. Por favor, tente novamente.\n"); 
                            System.out.println("Nome:");
                            dados.nextLine();
                        }
                        else flag3 = true;
                    }
             }
             catch(InputMismatchException e) 
                 { System.out.println("Nome inválido. Por favor, tente novamente.\n"); 
                   System.out.println("Nome:");
                   dados.nextLine(); 
                 }
         }
        
             System.out.println("\nMorada: ");
             while(!flag4) {
             try {
                 morada = dados.nextLine();
                 if (morada.equals("0")) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    return;                        /* go back */
                 }
                 else {
                        if(morada.equals("")){
                            System.out.println("Morada inválida. Por favor, tente novamente.\n"); 
                            System.out.println("Morada:");
                            dados.nextLine(); 
                        }
                        else flag4 = true;
                    }
             }
             catch(InputMismatchException e) 
                 { System.out.println("Morada inválida. Por favor, tente novamente.\n"); 
                   System.out.println("Morada:");
                   dados.nextLine(); 
                 }
         }
        
                
            
             System.out.println("\nPassword: ");
             while(!flag5) {
             try {
                 password = dados.nextLine();
                 if (password.equals("0")) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    return;                        /* go back */
                 }
                 else {
                        if(password.equals("")){
                            System.out.println("Password inválida. Por favor, tente novamente.\n"); 
                            System.out.println("Password:");
                            dados.nextLine(); 
                        }
                        else flag5 = true;
                      }
             }
             catch(InputMismatchException e) 
                 { System.out.println("Password inválida. Por favor, tente novamente.\n"); 
                   System.out.println("Password:");
                   dados.nextLine(); 
                 }
         }
        
        
            int numAgregado = 0;
            System.out.println("\nNúmero do agregado familiar:");
            
            while(!flag6) {
             try {
                 numAgregado= input.nextInt();
                  if (numAgregado>0){
                       flag6 = true;
                  }
                  else {
                  if (numAgregado==0) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    input.close();
                    return;                        /* go back */
                  }
                  else {
                      System.out.println("Número de agregado inválido. Tente novamente, por favor."); 
                      System.out.println("Número de agregado:");
                      input.nextLine(); 
                    }
                }
             }
             catch(InputMismatchException e) { 
                 System.out.println("Número de agregado inválido. Tente novamente, por favor."); 
                 System.out.println("Número de agregado:");
                 input.nextLine(); 
             }
         }
          
          
            ArrayList<Integer> nifsAgregados = new ArrayList<Integer>(numAgregado);
            System.out.println("\nNifs dos membros do agregado familiar: ");
            
            if(numAgregado==1) {
                System.out.println("\nComo não tem mais membros para além de si mesmo, este campo não se aplica! ");
            }
            
            for(int i=0; i<numAgregado-1;){
                boolean flag10 = false;
                int nifMembro = 0;
                
                while(!flag10) {
             try {
                 nifMembro= input.nextInt();
                   if ((Integer.toString(nifMembro).length() == 9) && nifMembro>0) {
                       flag10 = true;
                  }
                  else { 
                   if (nifMembro==-2) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    input.close();
                    return;                        /* go back */
                  }  
                  else {
                      System.out.println("Nif inválido. Tente novamente, por favor."); 
                      System.out.println("Nif:");
                      input.nextLine(); 
                  }
                }
             }
             catch(InputMismatchException e) { 
                 System.out.println("Número de agregado inválido. Tente novamente, por favor."); 
                 System.out.println("Número de agregado:");
                 input.nextLine(); 
             }
         }
                
                
                    nifsAgregados.add(nifMembro);
                    i++;
                
            }
            
            double coefFiscal = 0;
            System.out.println("\nCoeficiente fiscal: ");
            
             while(!flag8) {
             try {
                 coefFiscal= input.nextDouble();
                  if (coefFiscal>0){
                       flag8 = true;
                  }
                  else {
                  if (coefFiscal==0) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    input.close();
                    return;                        /* go back */
                  }
                  else {
                      System.out.println("Coeficiente fiscal inválido. Tente novamente, por favor."); 
                      System.out.println("Coeficiente fiscal:");
                      input.nextLine(); 
                    }
                }
             }
             catch(InputMismatchException e) { 
                 System.out.println("Coeficiente fiscal inválido. Tente novamente, por favor."); 
                 System.out.println("Coeficiente fiscal:");
                 input.nextLine(); 
             }
         }
            
            
            
            Contribuinte c = new Contribuinte(nif,email,nome,morada,password,numAgregado,nifsAgregados,coefFiscal);
            try{
                javaFactura.registarContribuinte(c);
            }
            catch(ContribuinteExistenteException e){
                System.out.println("Contribuinte já existente!");
            }
            
            
        }else{
           System.out.println("\nNif: ");
            while(!flag1) {
                try {
                    nif= input.nextInt();
                   
                    if  ((Integer.toString(nif).length() == 9) && nif>0) {
                     flag1 = true;
                    }
                
                    else { 
                       if (nif ==0) {
                        System.out.println("\nRegisto cancelado!");
                        System.out.println("\n (Pressione para continuar)");
                        output.nextLine();
                        output.close();
                        input.close();
                        return;                        /* go back */
                       }
                       else {
                        System.out.println("Nif inválido. Tente novamente, por favor"); 
                        System.out.println("Nif inválido:");
                        input.nextLine();
                            }
                     }
                }
                catch(InputMismatchException e) { 
                    System.out.println("Nif inválido. Tente novamente, por favor"); 
                    System.out.println("Nif inválido.");
                    input.nextLine(); 
                }
            }
          
            
            
             System.out.println("\nEmail: ");
               
             while(!flag2) {
             try {
                 email = dados.nextLine();
                 if (email.equals("0")) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    return;                        /* go back */
                 }
                 else {
                     if(email.equals("")){
                         System.out.println("Email inválido. Por favor, tente novamente.\n"); 
                         System.out.println("Email:");
                         dados.nextLine();
                        }
                     else flag2 = true;
                    }
             }
             catch(InputMismatchException e) 
                 { System.out.println("Email inválido. Por favor, tente novamente.\n"); 
                   System.out.println("Email:");
                   dados.nextLine(); 
                 }
         }
         
        
              System.out.println("\nNome: ");
                
              while(!flag3) {
             try {
                 nome = dados.nextLine();
                 if (nome.equals("0")) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    return;                        /* go back */
                 }
                 else {
                        if(nome.equals("")){
                            System.out.println("Nome inválido. Por favor, tente novamente.\n"); 
                            System.out.println("Nome:");
                            dados.nextLine();
                        }
                        else flag3 = true;
                    }
             }
             catch(InputMismatchException e) 
                 { System.out.println("Nome inválido. Por favor, tente novamente.\n"); 
                   System.out.println("Nome:");
                   dados.nextLine(); 
                 }
         }
        
             System.out.println("\nMorada: ");
             while(!flag4) {
             try {
                 morada = dados.nextLine();
                 if (morada.equals("0")) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    return;                        /* go back */
                 }
                 else {
                        if(morada.equals("")){
                            System.out.println("Morada inválida. Por favor, tente novamente.\n"); 
                            System.out.println("Morada:");
                            dados.nextLine();
                        }
                        else flag4 = true;
                    }
             }
             catch(InputMismatchException e) 
                 { System.out.println("Morada inválida. Por favor, tente novamente.\n"); 
                   System.out.println("Morada:");
                   dados.nextLine(); 
                 }
         }
        
                
            
             System.out.println("\nPassword: ");
             while(!flag5) {
             try {
                 password = dados.nextLine();
                 if (password.equals("0")) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    return;                        /* go back */
                 }
                 else {
                        if(password.equals("")){
                            System.out.println("Password inválida. Por favor, tente novamente.\n"); 
                            System.out.println("Password:");
                            dados.nextLine(); 
                        }
                        else flag5 = true;
                    }
             }
             catch(InputMismatchException e) 
                 { System.out.println("Password inválida. Por favor, tente novamente.\n"); 
                   System.out.println("Password:");
                   dados.nextLine(); 
                 }
         }
            
            double fator = 0;
            System.out.println("\nFator para o calculo de dedução fiscal: ");
               
             while(!flag6) {
             try {
                 fator= input.nextDouble();
                  if (fator>0){
                       flag6 = true;
                  }
                  else {if (fator==0) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    input.close();
                    return;                        /* go back */
                  }
                        else {
                       System.out.println("Fator para o calculo de dedução fiscal inválido. Tente novamente, por favor."); 
                       System.out.println("Fator para o calculo de dedução fiscal:");
                       input.nextLine(); 
                        }
                }
             }
             catch(InputMismatchException e) { 
                 System.out.println("Fator para o calculo de dedução fiscal inválido. Tente novamente, por favor."); 
                 System.out.println("Fator para o calculo de dedução fiscal:");
                input.nextLine(); 
             }
         }
            
            
            
            int numAti = 0;
            System.out.println("\nNúmero de atividades económicas: ");
             
             while(!flag7) {
             try {
                 numAti= dados.nextInt();
                  if (numAti>0 && numAti<5){
                       flag7 = true;
                  }
                  else {  
                          if (numAti==0) {
                            System.out.println("\nRegisto cancelado!");
                            System.out.println("\n (Pressione para continuar)");
                            output.nextLine();
                            output.close();
                            input.close();
                            return;                        /* go back */
                          }
                          else {
                              System.out.println("Número de atividades económicas inválido. Tente novamente, por favor."); 
                              System.out.println("Número de atividades económicas:");
                              dados.nextLine(); 
                           }
                }
             }
             catch(InputMismatchException e) { 
                 System.out.println("Número de atividades económicas inválido. Tente novamente, por favor."); 
                 System.out.println("Número de atividades económicas:");
                 dados.nextLine(); 
             }
         }
            
            
            
            List<String> atividades = new ArrayList<String>(numAti);
            System.out.println("\nAtividades Económicas(Geral, Educacao, Saude, Habitacao): ");
             for(int i=0; i<numAti;){
                String atividade = "";
                boolean flag9 = false;
                 while(!flag9) {
                     
             try {
               atividade= dados2.nextLine();
                  if(atividade.equals("Habitacao") || atividade.equals("Educacao") ||
                    atividade.equals("Saude") || atividade.equals("Geral")) {
                        flag9 = true;
                  }
                  else {
                     if (atividade.equals("-2")) {
                      System.out.println("\nRegisto cancelado!");
                      System.out.println("\n (Pressione para continuar)");
                      output.nextLine();
                      output.close();
                      input.close();
                      return;  
                     }  
                     else {
                              System.out.println("Atividade inválida. Tente novamente, por favor."); 
                              System.out.println("Atividade:");
                              dados2.nextLine();                        /* go back */
                  }
                }
             }
             catch(InputMismatchException e) { 
                 System.out.println("Atividade inválida. Tente novamente, por favor."); 
                 System.out.println("Atividades económicas:");
                 dados1.nextLine();
             }   
              
                    atividades.add(atividade);
                    i++;
                
            }
            
        }
         Empresa em = new Empresa(nif,email,nome,morada,password,fator,atividades);
            try{
                javaFactura.registarEmpresa(em);
            }catch(EmpresaExistenteException e){
                System.out.println("Empresa já existente!");
            }
        
    }
    }   
    
    
    /**
     * Método que permite ao contribuinte verificar, por parte do contribuinte individual, as despesas que foram emitidas em seu nome 
     * e verificar o montante de dedução fiscal acumulado, por si e pelo agregado familiar. Permite corrigir a atividade da fatura e associar uma atividade
     * a uma fatura.
     */
    private void menuContribuinte (int nif) throws EmpresaExistenteException, ContribuinteExistenteException{
        Scanner output = new Scanner(System.in);
        int opcao;
        do {
            menuContribuinte.executa();
            switch(menuContribuinte.getOpcao()){
                case 1: 
                    montanteContribuinteAgregado(nif);
                    break;
                
                case 2:
                    try{
                        corrigirAtividade(nif);
                    }
                    catch(FaturaExistenteException e){
                        System.out.println("Fatura não existe");
                    }
                    break;
                case 3 :
                    try{
                        associarAtividade(nif);
                    }
                    catch(FaturaExistenteException e){
                        System.out.println("Fatura não existe");
                    }
            }
        } while(menuContribuinte.getOpcao() !=0);
        System.out.println("\nSessão terminada com sucesso!");
        System.out.println("\n (Pressione para continuar)");
        output.nextLine();
        output.close();
    }
    
    /**
     * Método que permite à empresa criar faturas, verificar o total faturado num determinado período, obter a listagem das suas faturas, obter a listagem das 
     * suas faturas por contribuinte num determinado intervalo de datas e obter a listagem das suas faturas por contribuinte de forma decrescente.
     */
    private void menuEmpresa (int nif) throws EmpresaExistenteException {
        Scanner output = new Scanner(System.in);
        
        do {
            menuEmpresa.executa();
            
            switch (menuEmpresa.getOpcao()) {
                case 1 :
                    criarFactura(nif);
                    break;
                
                case 2 :
                    totalFaturadoEmpresa(nif);
                    break;
                    
                case 3 :
                    listagemFaturasDaEmpresa(nif);
                    break;
                    
                case 4 :
                    listagemFaturasPorContribuinte(nif);
                    break;
                
                case 5 :
                    listagemFaturasPorContribuinteOrdenado(nif);
            }
        } while(menuEmpresa.getOpcao() !=0);
        System.out.println("\nSessão terminada com sucesso!");
        System.out.println("\n (Pressione para continuar)");
        output.nextLine();
        output.close();
    }
    
    /**
     * Método que permite ao administrador verificar os dez contribuintes que mais gastam em todo o sistema e as X empresas que têm mais facturas em todo 
     * o sistema(e o montante de deduções fiscais que as despesas registadas representam).
     * 
     */
    
     private void menuAdministrador () throws EmpresaExistenteException {
        Scanner output = new Scanner(System.in);
        
        do {
            menuAdministrador.executa();
            
            switch (menuAdministrador.getOpcao()) {
                case 1 :
                    javaFactura.dezQueMaisGastam();
                    break;
                
                case 2 :
                    relacaoXempresas();
                    break;
                    
            }
        } while(menuAdministrador.getOpcao() !=0);
        System.out.println("\nSessão terminada com sucesso!");
        System.out.println("\n (Pressione para continuar)");
        output.nextLine();
        output.close();
    }
    
    /**
     * Método que obtém por parte do contribuinte individual, as despesas que foram emitidas em seu nome e verifica o montante de dedução fiscal acumulado,
     * por si e pelo agregado familiar.
     * Cria-se uma lista que guarda as faturas do contribuinte em questão e cria-se outra que guarda as faturas do seu agregado.
     * De seguida, é calculadoo montande de dedução fiscal de cada através do método montanteDeducaoPorListagemFatura.
     */
    public void montanteContribuinteAgregado(int nif) throws EmpresaExistenteException, ContribuinteExistenteException {
           double montante, montanteAgregado = 0;
           boolean flag = false;
           List<Fatura> faturasIndividual = new ArrayList<Fatura>(); // faturas do contribuinte individual em questão
           List<Fatura> faturasAgregado = new ArrayList<Fatura>(); // faturas do agregado do contribuinte individual em questão
           List<Integer> nifAgregados = new ArrayList<Integer>(); 
           double coefAgregado = 0;
           SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy HH:mm");
           
           faturasIndividual = javaFactura.listagemFaturasIndividual(nif);  // faturas em seu nome
           
           nifAgregados = javaFactura.getContribuinteIndividual(nif).getNifsAgregados(); // lista com os nifs dos agregado
           
            for(int i=0; i < nifAgregados.size(); i++) {
                coefAgregado = coefAgregado + javaFactura.getContribuinteIndividual(nif).getCoefFiscal();
            }
          
           for(int i=0; i < nifAgregados.size(); i++) {
               for(Fatura ff: javaFactura.getFaturas().values()) {
                   if (ff.getNifC() == nifAgregados.get(i))
                             faturasAgregado.add(ff.clone());
               }
           }
           
           montante = javaFactura.montanteDeducaoPorListagemFatura(faturasIndividual) * javaFactura.getContribuinteIndividual(nif).getCoefFiscal() ;
           montanteAgregado = javaFactura.montanteDeducaoPorListagemFatura(faturasAgregado) * coefAgregado;
           
           System.out.println ("O montante de dedução fiscal acumulado por si é   " + montante + "\n");
           System.out.println ("O montante de dedução fiscal acumulado pelo seu agregado familiar é   " + montanteAgregado + "\n");
           
           System.out.println ("As despesas que foram emitidas em seu nome são: \n");
           
           for (Iterator<Fatura> iterator = faturasIndividual.iterator(); iterator.hasNext(); ) {  
               Fatura f = iterator.next();  
               System.out.println ("--------------------FATURA--------------------\n");  
               System.out.println ("ID da fatura:  "  + f.getId() + "\n");  
               System.out.println ("Nif do emitente:  " + f.getNifE() + "\n");  
               System.out.println ("Data:  " + form.format(f.getData().getTime()) + "\n");  
               System.out.println ("Designação do emitente:  " + f.getDesignacao() + "\n");  
               System.out.println ("Nif do cliente:  " + f.getNifC() + "\n");  
               System.out.println ("Descrição da despesa:  " + f.getDescricao() + "\n");  
               System.out.println ("Atividade económica a que diz respeito a despesa:  " + f.getAtividade() + "\n");  
               System.out.println ("Valor da despesa:  " + f.getValor() + "\n");  
               System.out.println ("Histórico de alterações: " + f.getHistorico() + "\n");
               System.out.println ("------------------------------------------------\n\n\n");  
           }
    }
    
    
    /**
     * Método que obtém o total facturado por uma empresa num determinado período;
     * É pedido ao utilizador a data do início e do fim do período que quer verificar.
     * Recorre-se ao método totalEmpresa que soma todos os valores das faturas associadas à empresa.
     */
    public void totalFaturadoEmpresa(int nif) {
        GregorianCalendar begin = new GregorianCalendar();
        GregorianCalendar end = new GregorianCalendar();
        GregorianCalendar data = new GregorianCalendar();
        int dia1, dia2, mes1, mes2, ano1, ano2;
        double soma;
 
        System.out.println("Para a data do início do período que quer calcular, introduza o dia: ");
        dia1 = leDia("Dia inválido! Escreva novamente,por favor.", "Dia: ");
        System.out.println("Para a data do início do período que quer calcular, introduza o mês: ");
        mes1 = leMes("Mês inválido! Escreva novamente,por favor.", "Mês: "); 
        System.out.println("Para a data do início do período que quer calcular, introduza o ano: ");
        ano1 = leAno("Ano inválido! Escreva novamente,por favor.", "Ano: ");
           
        System.out.println("Para a data do fim do período que quer calcular, introduza o dia: ");
        dia2 = leDia("Dia inválido! Escreva novamente,por favor.", "Dia: ");
        System.out.println("Para a data do fim do período que quer calcular, introduza o mês: ");
        mes2 = leMes("Mês inválido! Escreva novamente,por favor.", "Mês: ");
           
        System.out.println("Para a data do fim do período que quer calcular, introduza o ano: ");
        ano2 = leAno("Ano inválido! Escreva novamente,por favor.", "Ano: ");

        begin = new GregorianCalendar(ano1,mes1,dia1);
        end = new GregorianCalendar(ano2,mes2,dia2);
           
        if (begin.before(end)){
            soma = javaFactura.totalEmpresa(nif,begin,end);
            System.out.println("Total faturado pela empresa: " + soma + " €\n");  
        }
        else
            System.out.println("A segunda data é anterior à primeira!\n");
    }

    
    /**
     * Método que obtém a listagem das faturas de uma determinada empresa, ordenada por valor.
     * Cria-se uma lista das faturas da empresa através do método listagemFaturasEmpresa.
     *  
     */
    public void listagemFaturasDaEmpresa(int nif) {
        List<Fatura> faturas = new ArrayList<Fatura>();
        SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        faturas = javaFactura.listagemFaturasEmpresa(nif);        // faturas emitidas pela empresa
        Collections.sort (faturas, new ComparadorDeFaturas());   //  faturas ordenadas pelo valor
           
        for (Iterator<Fatura> iterator = faturas.iterator(); iterator.hasNext(); ) {  
            Fatura f = iterator.next();  
            System.out.println ("--------------------FATURA--------------------\n");  
            System.out.println ("ID da fatura:  "  + f.getId() + "\n");  
            System.out.println ("Nif do emitente:  " + f.getNifE() + "\n");  
            System.out.println ("Data:  " + form.format(f.getData().getTime()) + "\n");  
            System.out.println ("Designação do emitente:  " + f.getDesignacao() + "\n");  
            System.out.println ("Nif do cliente:  " + f.getNifC() + "\n");  
            System.out.println ("Descrição da despesa:  " + f.getDescricao() + "\n");  
            System.out.println ("Atividade económica a que diz respeito a despesa:  " + f.getAtividade() + "\n");  
            System.out.println ("Valor da despesa:  " + f.getValor() + "\n");  
            System.out.println ("Histórico de alterações: " + f.getHistorico() + "\n");
            System.out.println ("------------------------------------------------\n\n\n");  
        }
    }
    
    /**
     * Método que obtém por parte das empresas, as listagens das faturas por contribuinte num determinado intervalo de datas.
     * É pedido ao utilizador a data do início e do fim do período que quer verificar.
     * Cria-se um map que guarda os nifs dos contribuintes e uma lista com as faturas que estão associadas à empresa através do método listFaturasPorContribuinte.
     *  
     */
    
    public void listagemFaturasPorContribuinte(int nif) throws EmpresaExistenteException {
        GregorianCalendar begin = new GregorianCalendar();
        GregorianCalendar end = new GregorianCalendar();
        GregorianCalendar data = new GregorianCalendar();
        int dia1, dia2, mes1, mes2, ano1, ano2;
        Map<Integer,ArrayList<Fatura>> mapa = new HashMap<Integer,ArrayList<Fatura>>();
        ArrayList<Fatura> faturas = new ArrayList<>();  
        SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            
        System.out.println("Para a data do início do período que quer calcular, introduza o dia: ");
        dia1 = leDia("Dia inválido! Escreva novamente,por favor.", "Dia: ");
        System.out.println("Para a data do início do período que quer calcular, introduza o mês: ");
        mes1 = leMes("Mês inválido! Escreva novamente,por favor.", "Mês: ");
        System.out.println("Para a data do início do período que quer calcular, introduza o ano: ");
        ano1 = leAno("Ano inválido! Escreva novamente,por favor.", "Ano: ");

        System.out.println("Para a data do fim do período que quer calcular, introduza o dia: ");
        dia2 = leDia("Dia inválido! Escreva novamente,por favor.", "Dia: ");
        System.out.println("Para a data do fim do período que quer calcular, introduza o mês: ");
        mes2 = leMes("Mês inválido! Escreva novamente,por favor.", "Mês: ");
        System.out.println("Para a data do fim do período que quer calcular, introduza o ano: ");
        ano2 = leAno("Ano inválido! Escreva novamente,por favor.", "Ano: ");
        
        begin = new GregorianCalendar(ano1,mes1,dia1);
        end = new GregorianCalendar(ano2,mes2,dia2);

        mapa = javaFactura.listFaturasPorContribuinte(javaFactura.getEmpresa(nif),begin,end);  
           
        if (begin.before(end)){
            for(Integer niff : mapa.keySet()) {
                System.out.println("As faturas do contribuinte   " + niff + "  são: \n");
                faturas = mapa.get(niff);
                for (Iterator<Fatura> iterator = faturas.iterator(); iterator.hasNext(); ) {  
                    Fatura f = iterator.next();  
                    System.out.println ("--------------------FATURA--------------------\n");  
                    System.out.println ("ID da fatura:  "  + f.getId() + "\n");  
                    System.out.println ("Nif do emitente:  " + f.getNifE() + "\n");  
                    System.out.println ("Data:  " + form.format(f.getData().getTime()) + "\n");  
                    System.out.println ("Designação do emitente:  " + f.getDesignacao() + "\n");  
                    System.out.println ("Nif do cliente:  " + f.getNifC() + "\n");  
                    System.out.println ("Descrição da despesa:  " + f.getDescricao() + "\n");  
                    System.out.println ("Atividade económica a que diz respeito a despesa:  " + f.getAtividade() + "\n");  
                    System.out.println ("Valor da despesa:  " + f.getValor() + "\n");  
                    System.out.println ("Histórico de alterações: " + f.getHistorico() + "\n");
                    System.out.println ("------------------------------------------------\n\n\n");  
                }
            }
        }
        else
               System.out.println("A segunda data é anterior à primeira!\n");
    }
    
    
     /**
     *  Método que obtém por parte das empresas, as listagens das faturas por contribuinte ordenadas por valor decrescente de despesa.
     *  Cria-se um map que guarda os nifs dos contribuintes e uma lista com as faturas que estão associadas à empresa de forma decrescente através do método
     *  listFaturasPorContribuinteOrdenado. 
     */
    
    public void listagemFaturasPorContribuinteOrdenado(int nif) throws EmpresaExistenteException {
        Map<Integer,ArrayList<Fatura>> mapa = new HashMap<Integer,ArrayList<Fatura>>();
        ArrayList<Fatura> faturas = new ArrayList<>();       
        SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        mapa = javaFactura.listFaturasPorContribuinteOrdenado(javaFactura.getEmpresa(nif));  
           
        for(Integer niff : mapa.keySet()) {
            System.out.println("As faturas do contribuinte  " + niff + "  são: \n");
            faturas = mapa.get(niff);
            for (Iterator<Fatura> iterator = faturas.iterator(); iterator.hasNext(); ) {  
                Fatura f = iterator.next();  
                System.out.println ("--------------------FATURA--------------------\n");  
                System.out.println ("ID da fatura:  "  + f.getId() + "\n");  
                System.out.println ("Nif do emitente:  " + f.getNifE() + "\n");  
                System.out.println ("Data:  " + form.format(f.getData().getTime()) + "\n");  
                System.out.println ("Designação do emitente:  " + f.getDesignacao() + "\n");  
                System.out.println ("Nif do cliente:  " + f.getNifC() + "\n");  
                System.out.println ("Descrição da despesa:  " + f.getDescricao() + "\n");  
                System.out.println ("Atividade económica a que diz respeito a despesa:  " + f.getAtividade() + "\n");  
                System.out.println ("Valor da despesa:  " + f.getValor() + "\n");  
                System.out.println ("Histórico de alterações: " + f.getHistorico() + "\n");
                System.out.println ("------------------------------------------------\n\n\n");  
            }
        }
    }
    
     
    /**
     * Método que cria uma fatura. É pedido as informações necessárias e é registada.
     */
    public void criarFactura (int nifE) throws EmpresaExistenteException {
        Scanner input = new Scanner(System.in);
        Scanner output = new Scanner(System.in);
        Scanner dados = new Scanner(System.in);
        int nifC = 0;
        int estado = 0;
        String atividadeDespesa = "";
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        double valor = 0;
        int ano, mes, dia;
        int opcao = 0;
        String descricao = "";
        String historico = "";
        
        System.out.println("\nNif do Contribuinte do cliente: ");
        while(!flag) {
                try {
                    nifC= input.nextInt();
                    
                    if ((Integer.toString(nifC).length() == 9) && nifC>0) {
                        flag = true;
                    }    
                    else {
                        if (nifC==0) {
                            System.out.println("\nRegisto cancelado!");
                            System.out.println("\n (Pressione para continuar)");
                            output.nextLine();
                            output.close();
                            input.close();
                            return;                        /* go back */
                        }
                        else {
                              if(!javaFactura.containsContribuinteIndividual(nifC)) {
                                  System.out.println("Nif não registado! Por favor tente outra vez.");
                              }
                              
                              else {
                                  System.out.println("Número de agregado inválido. Tente novamente, por favor."); 
                                  System.out.println("Número de agregado:");
                                  input.nextLine(); 
                                }
                        }
                    }
                    
                    
                }
                catch(InputMismatchException e) { 
                    System.out.println("Nif inválido. Tente novamente, por favor."); 
                    System.out.println("Nif:");
                    input.nextLine(); 
                }
            }
        
        
        System.out.println("Data da fatura: ");
        System.out.println("Dia: ");
        dia = leDia("Dia inválido! Escreva novamente,por favor.", "Dia: ");
        System.out.println("Mês:  ");
        mes = leMes("Mês inválido! Escreva novamente,por favor.", "Mês: ");
        System.out.println("Ano: ");
        ano = leAno("Ano inválido! Escreva novamente,por favor.", "Ano: ");
        GregorianCalendar data = new GregorianCalendar(ano,mes,dia);
        
        
        System.out.println("Valor:");
       
        
        while(!flag2) {
            try {
                valor = dados.nextDouble();
               
                if (valor>0 ) {
                       flag2 = true;
                }
                
                else { 
                    if (valor ==0) {
                        System.out.println("\nRegisto cancelado!");
                        System.out.println("\n (Pressione para continuar)");
                        output.nextLine();
                        output.close();
                        input.close();
                        return;                        /* go back */
                    }
                    else {
                        System.out.println("Valor inválido! Escreva novamente,por favor."); 
                        System.out.println("Valor: ");
                        dados.nextLine();
                    }
                }
            }
            catch(InputMismatchException e) {
                System.out.println("Valor inválido! Escreva novamente,por favor."); 
                System.out.println("Valor: ");
                dados.nextLine(); 
            }
        } 
        
        
        
        System.out.println("Descrição:\n (1) - Adicionar Descrição\n (2) - Sem Descrição");
        
            while(!flag1) {
             try {
                 opcao = input.nextInt();
                 if (opcao==1 || opcao ==2) {
                       flag1 = true;
                }
                 else { 
                  if (opcao==0) {
                    System.out.println("\nRegisto cancelado!");
                    System.out.println("\n (Pressione para continuar)");
                    output.nextLine();
                    output.close();
                    input.close();
                    return;                        /* go back */
                  }
                  else {
                      System.out.println("Opção inválida. Tente novamente, por favor."); 
                      System.out.println("Opção:");
                      input.nextLine();
                  }
                }
             }
             catch(InputMismatchException e) { 
                 System.out.println("Opção inválida. Tente novamente, por favor."); 
                 System.out.println("Opção:");
                 input.nextLine(); 
             }
         }
        
        switch(opcao) {
            case 1:
                System.out.println("Descrição: ");
                descricao = leString("Descrição inválida! Escreva novamente,por favor.", "Descrição: ");
                break;
            case 2:
                break;
        }
        
        
        if (javaFactura.getEmpresa(nifE).getAtiv().size() == 1) {
            atividadeDespesa = (javaFactura.getEmpresa(nifE).getAtiv()).get(0); // como sabes que só tem uma atividade, valor à lista buscar a posição 0
            historico = "";
        }
        else {
            atividadeDespesa = "Tem de ser catalogada";
            historico = "";
        }
        
        Fatura f = new Fatura(nifE,javaFactura.getEmpresa(nifE).getNome(),data,nifC,descricao,atividadeDespesa,historico,valor);
        f.setId(javaFactura.getNumFaturas()+1);
        
        try{
            javaFactura.registarFatura(f);
        }
        catch(FaturaExistenteException e){
            System.out.println("Fatura já existente!");
        }
    }
    
    
    /**
     * Método que corrige a atividade económica de uma fatura.
     * Caso a fatura exista, o cliente associado à fatura corresponda ao contribuinte que tenta associar e a empresa tenha mais de uma atividade,
     * o contribuinte em questão pode alterar, deixando em registo que a alterou.
     * Caso contrário, verifica-se se a fatura existe. Se não existir, é imprimido uma mensagem a dizer que não existe. Se existir, é porque já
     * está catalogada.
     */
    public void corrigirAtividade(int nif) throws FaturaExistenteException, EmpresaExistenteException{
        Scanner input = new Scanner(System.in);
        Scanner dados = new Scanner(System.in);
        System.out.println("Insira o ID da fatura que deseja validar: \n");
        
        int idF = leInt("ID da fatura inválido! Escreva novamente,por favor.", "ID da fatura: ");

        if( javaFactura.containsFatura(idF) && ((javaFactura.getFatura(idF)).getNifC()==nif) &&
          ((javaFactura.getEmpresa((javaFactura.getFatura(idF)).getNifE())).getAtiv().size()>1) && !( (javaFactura.getFatura(idF)).getAtividade().equals("Tem de ser catalogada"))){
            Fatura f = new Fatura((javaFactura.getFatura(idF)));
            Empresa empresa = new Empresa (javaFactura.getEmpresa(f.getNifE()));
            System.out.println("Escolha uma das seguintes atividades:\n" + ( (javaFactura.getEmpresa((javaFactura.getFatura(idF)).getNifE())).getAtiv().toString()) );
            String nAtividade = dados.nextLine();
            String a = "Atividade anterior: " + f.getAtividade() + "; Atividade Corrigida: "+ nAtividade;
            String b = nAtividade;
            javaFactura.getFatura(idF).setAtividade(b);
            javaFactura.getFatura(idF).setHistorico(a);
        }
        else {
            if(!(javaFactura.containsFatura(idF))) {
                throw new FaturaExistenteException(String.valueOf(idF));
            }
            else {
                if ( (javaFactura.getFatura(idF)).getAtividade().equals("Tem de ser catalogada")) {
                    System.out.println("A fatura tem de ser catalogada!");
                }
                else {
                System.out.println("A fatura está automaticamente catalogada!");
            }
        }
        }
    }
    
    
    /**
     * Método que associada a atividade económica a uma fatura.
     * Caso a fatura exista, o cliente associado à fatura corresponda ao contribuinte que tenta associar e a empresa tenha mais de uma atividade,
     * verifica-se se já está catalogada. Se estiver,não pode associar. Se não estiver,pode associar.
     */
    public void associarAtividade(int nif) throws FaturaExistenteException, EmpresaExistenteException {
        Scanner input = new Scanner(System.in);
        Scanner dados = new Scanner(System.in);
        System.out.println("Insira o ID da fatura que deseja associar a atividade: \n");
        
        int idF = leInt("ID da fatura inválido! Escreva novamente,por favor.", "ID da fatura: ");
        
        if( javaFactura.containsFatura(idF) && ((javaFactura.getFatura(idF)).getNifC()==nif) &&  ((javaFactura.getEmpresa((javaFactura.getFatura(idF)).getNifE())).getAtiv().size()>1) ){
            Fatura f = new Fatura((javaFactura.getFatura(idF)));
            Empresa empresa = new Empresa (javaFactura.getEmpresa(f.getNifE()));
            
            if ((f.getAtividade().equals("Tem de ser catalogada"))) {
                System.out.println("Escolha uma das seguintes atividades:\n" + ( (javaFactura.getEmpresa((javaFactura.getFatura(idF)).getNifE())).getAtiv().toString()) );
                String nAtividade = dados.nextLine();
                String a = nAtividade;
                javaFactura.getFatura(idF).setAtividade(a);
            }
            else
                System.out.println("A fatura já está catalogada.\n");
        }
        else {
            Fatura fatura = new Fatura((javaFactura.getFatura(idF)));
            Empresa empresa = new Empresa (javaFactura.getEmpresa(fatura.getNifE()));
            if(empresa.getAtiv().size()==1 && ((javaFactura.getFatura(idF)).getNifC()==nif) )
                System.out.println("A fatura já está automaticamente validada.\n");
            else
                throw new FaturaExistenteException(String.valueOf(idF));
        }
    }
    
    
    
    
    // Método que imprime as X empresas com mais faturas e o montante de deduções fiscais que as despesas registadas representam.
    public void relacaoXempresas() throws EmpresaExistenteException {
        System.out.println("Indique o número de empresas:\n");
        int numero = 0;
        Scanner input = new Scanner(System.in);
        Scanner output = new Scanner(System.in);
        boolean flag = false;
        
        numero = leInt("Número inválido! Escreva novamente,por favor.", "Número: ");
        
        javaFactura.xEmpresas(numero);
    }
    
    
    
    
    // Método que lê um mês, tendo este que ser maior que 1 e 12.
    public  int leMes (String erro, String msg) {
        Scanner dados = new Scanner(System.in);
        Scanner output = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        boolean flag = false; 
        int mes = 0;
        
        while(!flag) {
            try {
                mes = dados.nextInt();
                
                 if ( (mes > 0) && (mes <13) ) {
                     flag = true;
                }
                
                else { 
                    if (mes ==0) {
                        System.out.println("\nRegisto cancelado!");
                        System.out.println("\n (Pressione para continuar)");
                        output.nextLine();
                        output.close();
                        input.close();
                        return -1;                        /* go back */
                    }
                    else {
                        System.out.println(erro); 
                        System.out.println(msg);
                        dados.nextLine();
                    }
                }
                
            }
            catch(InputMismatchException e) {
                System.out.println(erro); 
                System.out.println(msg);
                dados.nextLine(); 
            }
        }
        dados.close();
        return mes;
    } 

    
    // Método que lê um dia, tendo este que ser entre 1 e 30.
    public int leDia(String erro, String msg) {
        Scanner dados = new Scanner(System.in);
        Scanner output = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        boolean flag = false; 
        int dia = 0;
        
        while(!flag) {
            try {
                dia = dados.nextInt();
                
                if ( (dia > 0) && (dia <31) ) {
                     flag = true;
                }
                
                else { 
                    if (dia ==0) {
                        System.out.println("\nRegisto cancelado!");
                        System.out.println("\n (Pressione para continuar)");
                        output.nextLine();
                        output.close();
                        input.close();
                        return -1;                        /* go back */
                    }
                    else {
                        System.out.println(erro); 
                        System.out.println(msg);
                        dados.nextLine();
                    }
                }
            }
            catch(InputMismatchException e) {
                System.out.println(erro); 
                System.out.println(msg);
                dados.nextLine(); 
            }
        }
        dados.close();
        return dia;
    } 

    
    
   
   
    
    // Método que lê um ano, tendo este que ser maior que 0 e ter quatro dígitos. 
    public  int leAno(String erro, String msg) {
        Scanner dados = new Scanner(System.in);
        Scanner output = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        boolean flag = false; 
        int ano = 0;
         
        while(!flag) {
            try {
                ano = dados.nextInt();
                
                if ( Integer.toString(ano).length()==4 && ano>0 ) {
                     flag = true;
                }
                
                else { 
                    if (ano ==0) {
                        System.out.println("\nRegisto cancelado!");
                        System.out.println("\n (Pressione para continuar)");
                        output.nextLine();
                        output.close();
                        input.close();
                        return -1;                        /* go back */
                    }
                    else {
                        System.out.println(erro); 
                        System.out.println(msg);
                        dados.nextLine();
                    }
                }
            }
            catch(InputMismatchException e) {
                System.out.println(erro); 
                System.out.println(msg);
                dados.nextLine(); 
            }
        } 
        dados.close();
        return ano;
    } 
     
   
    // Método que lê uma string.
     public String leString(String erro, String msg) {
        Scanner dados = new Scanner(System.in);
        Scanner output = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        
        boolean flag = false; 
        String s = "";
         
        while(!flag) {
                try {
                    s = dados.nextLine();
                    if (s.equals("0")) {
                        System.out.println("\nRegisto cancelado!");
                        System.out.println("\n (Pressione para continuar)");
                        output.nextLine();
                        output.close();
                        return "";                        /* go back */
                    }
                    else
                        flag = true;
                }
                catch(InputMismatchException e) {
                    System.out.println(erro); 
                    System.out.println(msg);
                    dados.nextLine(); 
                }
            }
        dados.close();
        return s;
    } 
     
    
    
    // Método que lê um inteiro, tendo este de ser maior que 0.
    public int leInt(String erro, String msg) {
        Scanner dados = new Scanner(System.in);
        Scanner output = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        boolean flag = false; 
        int inteiro = 0;
         
        while(!flag) {
                try {
                    inteiro= dados.nextInt();
                    if (inteiro>0)
                        flag = true;
                    else {
                        if (inteiro==0) {
                            System.out.println("\nRegisto cancelado!");
                            System.out.println("\n (Pressione para continuar)");
                            output.nextLine();
                            output.close();
                            input.close();
                            return -1;                        /* go back */
                        }
                        else {
                            System.out.println(erro); 
                            System.out.println(msg);
                            dados.nextLine(); 
                        }
                    }
                }
                catch(InputMismatchException e) { 
                    System.out.println(erro); 
                    System.out.println(msg);
                    dados.nextLine(); 
                }
            }
                   
        dados.close();
        return inteiro;
    } 
     
    
    
}