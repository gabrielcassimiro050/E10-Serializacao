public class Main {
    public static void main(String[] args) {
        final int ORDERNAR = 1, TIPO = 0;
        Cliente joao = PessoaFisica.criarCliente("João", "123.456.789-09", "Av. Antonio Carlos, 6627", 35, 'f');
        Cliente maninho = PessoaFisica.criarCliente("Maninho 2", "123.456.789-09", "Av. Antonio Carlos, 6627", 35, 'm');

        if (joao == null) {
            System.out.println("Cliente não foi criado");
        } else {
            System.out.println("Client foi criado com sucesso!");
        }

        Conta conta1 = new ContaCorrente(1234, 1, joao, 0, 1500);
        Conta conta2 = new ContaCorrente(12121, 1, maninho, 10000, 1500);

        try {
            conta1.depositar(3000);
        } catch (Exception a) {
            System.out.println(a.getMessage());
        }
        try {
            conta1.sacar(500);
        } catch (Exception a) {
            System.out.println(a.getMessage());
        }

        try {
            conta1.sacar(500);
        } catch (Exception a) {
            System.out.println(a.getMessage());
        }

        try {
            conta1.sacar(500);
        } catch (Exception a) {
            System.out.println(a.getMessage());
        }
        try {
            conta1.sacar(500);
        } catch (Exception a) {
            System.out.println(a.getMessage());
        }

        conta2.transferir(conta1, 666);
        try {
            conta2.depositar(234);
        }catch(Exception a){
            System.out.println(a.getMessage());
        }
        conta1.imprimirExtrato(ORDERNAR);
        conta1.imprimirExtrato(TIPO);
        conta2.imprimirExtrato(ORDERNAR);
        System.out.println(conta1.toString());
        int media = Operacao.getTotalOperacoes() / Conta.getTotalContas();
        System.out.println("Média de operações por conta aberta: " + media);

        conta1.imprimirExtratoTaxas();
        conta1.writeConta();
        conta2.writeConta();
        Conta contaRetomada = Conta.getConta(conta1.getAgencia(), conta1.getNumero());
        System.out.println(conta1.getSaldo());
        System.out.println(contaRetomada.getSaldo());
    }
}
