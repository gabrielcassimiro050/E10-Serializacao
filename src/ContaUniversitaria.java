public class ContaUniversitaria extends Conta{

    ContaUniversitaria(int numero, int agencia, Cliente dono, double saldo, double limite) {
        super(numero, agencia, dono, saldo, limite);
    }

    public void checkLimite(double limite) throws IllegalArgumentException{
        if(limite < 0 || limite > 500){
            throw new IllegalArgumentException("Valor não está dentro do limite");
        }else{
            setLimite(limite);
        }
    }
    public double calculaTaxas() {
        return 0;
    }
}
