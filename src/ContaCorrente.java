import java.nio.channels.IllegalChannelGroupException;

public class ContaCorrente extends Conta{

    ContaCorrente(int numero, int agencia, Cliente dono, double saldo, double limite){
        super(numero, agencia, dono, saldo, limite);
    }

    public void checkLimite(double limite) throws IllegalArgumentException{
        if(limite < -100){
            throw new IllegalArgumentException("Valor não está dentro do limite");
        }else{
             setLimite(limite);
        }
    }

    public double calculaTaxas() {
        return getDono().calculaTaxas();
    }
}
