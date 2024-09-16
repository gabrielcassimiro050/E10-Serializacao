import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public abstract class Conta implements ITaxas, Serializable {

    private static final long serialVersionUID = 1L;

    private int numero, agencia;

    private Cliente dono;

    private double saldo;

    private double limite;

    private ArrayList<Operacao> operacoes;

    private static int totalContas = 0;

    public Conta(int numero, int agencia, Cliente dono, double saldo, double limite) {
        this.numero = numero;
        this.agencia = agencia;
        this.dono = dono;
        this.saldo = saldo;

        this.limite = limite;

        this.operacoes = new ArrayList<Operacao>();

        Conta.totalContas++;
    }

    public Conta() {
    }

    /*private void dobrarOperacao(){
        //Cria um vetor temporário com o valor requerido para armazenar as informações
        Operacao[] aux = new Operacao[this.operacoes.size()*2];
        for(int i = 0; i < this.operacoes.size(); ++i) {
            //Armazena as informações atuais nesse vetor
            aux[i] = this.operacoes[i];
        }
        //Iguala o vetor original ao vetor temporário
        this.operacoes = aux;
    }*/

    public void sacar(double valor) throws ValorNegativoException, SemLimiteException {

        if (valor > limite) {
            throw new SemLimiteException("Valor Fora do Limite: " + valor);
        } else {
            if (valor < 0) {
                throw new ValorNegativoException("Valor Negativo: " + valor);
            } else {
                operacoes.add(new OperacaoSaque(valor));
                this.saldo -= valor;
            }
        }

    }

    public void depositar(double valor) throws ValorNegativoException, SemLimiteException {
        if (valor > limite) {
            throw new SemLimiteException("Valor Fora do Limite: " + valor);
        } else {
            if (valor < 0) {
                throw new ValorNegativoException("Valor Negativo: " + valor);
            } else {
                operacoes.add(new OperacaoDeposito(valor));
                this.saldo += valor;
            }
        }
    }

    public void transferir(Conta destino, double valor) {
        try {
            this.sacar(valor);
            try {
                destino.depositar(valor);
            } catch (Exception b) {
                System.out.printf(b.getMessage());
            }
        } catch (Exception a) {
            System.out.println(a.getMessage());
        }
    }

    public void imprimir() {
        System.out.println("===== Conta " + this.numero + " =====");
        System.out.println("Dono: " + this.dono);
        System.out.println("Saldo: " + this.saldo);
        System.out.println("Limite: " + this.limite);
        System.out.println("====================");
    }

    public void imprimirExtrato(int ordernar) {
        System.out.println("======= Extrato Conta " + this.numero + "======");
        ArrayList<Operacao> aux = (ArrayList<Operacao>) operacoes.clone();
        if (ordernar == 1) Collections.sort(aux);
        for (Operacao op : aux) {
            if (op != null) {
                System.out.println(op.toString());
            }
        }
        System.out.println("====================");
    }


    public int getNumero() {
        return numero;
    }

    public int getAgencia() {
        return agencia;
    }

    public Cliente getDono() {
        return dono;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimite() {
        return limite;
    }

    public static int getTotalContas() {
        return Conta.totalContas;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    public ArrayList getOperacoes() {
        return operacoes;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public abstract void checkLimite(double limite);

    // Method to serialize the Conta object to a file
    public void writeConta() {
        try (FileOutputStream fileOut = new FileOutputStream("contas/" + agencia + "_" + numero + ".ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
        } catch (IOException e) {
            System.err.println("Error while writing the account: " + e.getMessage());
        }
    }

    // Method to deserialize a Conta object from a file
    public static Conta getConta(int agencia, int numero) {
        Conta aux = null;
        try (FileInputStream fileIn = new FileInputStream("contas/" + agencia + "_" + numero + ".ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            aux = (Conta) in.readObject();
        } catch (IOException e) {
            System.err.println("Error while reading the account: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
        }
        return aux;
    }

    public String toString() {
        String contaStr = "Nº" + numero + '\n' + dono.toString() + "Saldo: " + saldo + '\n' + "Limite: " + limite + '\n';
        for (int i = 0; i < operacoes.size(); ++i) {
            if (operacoes.get(i) != null) contaStr = contaStr.concat(operacoes.get(i).toString());
        }
        return contaStr;
    }

    public boolean equals(Object obj) {
        Conta contaComparada = (Conta) obj;
        return contaComparada.getNumero() == this.numero;
    }

    public void imprimirExtratoTaxas() {
        double t = calculaTaxas();
        System.out.println("============Extrato de Taxas============");
        System.out.println("Taxa de Manunteção: " + calculaTaxas());

        for (int i = 0; i < operacoes.size(); ++i) {
            if (operacoes.get(i) != null && operacoes.get(i).getTipo() == 's') {
                System.out.println("Saque: R$" + operacoes.get(i).calculaTaxas());
                t += operacoes.get(i).calculaTaxas();
            }
        }

        System.out.println();
        System.out.printf("Taxa de Saque Total: R$ %.2f", t);
        System.out.println();
    }

}
