import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu {
    private List<String> opcoes;
    private int op;
    
    public Menu (String[] o) {
        this.opcoes = Arrays.asList(o);
        this.op = 0;
    }
    
    public int getOpcao () {
        return this.op;
    }
    
    public void executa () {
        do {
            showMenu();
            this.op = lerOpcao();
        } while (this.op == -1);
    }
    
    private void showMenu () {
        System.out.println("\n-----------------------------------------\n");
        for(int i=0; i<this.opcoes.size(); i++){
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
        System.out.println("\n0 - Sair/Voltar\n");
    }
    
    private int lerOpcao () {
        int op;
        Scanner sc = new Scanner(System.in);
        System.out.print("Opcao: ");
        try {
            op = sc.nextInt();
        }
        catch (InputMismatchException e) {
            op = -1;
        }
        if (op < 0 || op > this.opcoes.size()) {
            System.out.println("\nOpcao Invalida!!");
            op = -1;
        }
        return op;
    }
}