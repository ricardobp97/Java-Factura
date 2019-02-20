import java.util.Comparator;

class ComparadorDeFaturasDecrescente implements Comparator<Fatura> {
    public int compare(Fatura f1, Fatura f2) {
        if (f1.getValor() > f2.getValor()) return -1;
        else if (f1.getValor() < f2.getValor()) return +1;
        else return 0;
    }
}