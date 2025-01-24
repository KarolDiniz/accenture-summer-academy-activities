public class Principal {
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("João", "Silva", "123.456.789-00");
        Cliente cliente2 = new Cliente("Maria", "Oliveira", "987.654.321-00");

        ContaCorrente conta1 = new ContaCorrente("12345-6", cliente1, 500.00);
        ContaCorrente conta2 = new ContaCorrente("65432-1", cliente2, 200.00);

        conta1.exibirExtrato();

        conta1.sacar(100.00);
        System.out.println("Saldo após saque: R$" + conta1.getSaldo());

        System.out.println("\nTentando transferir R$50.00 de conta1 para conta2...");
        boolean transferido = conta1.transferir(conta2, 50.00);

        if (transferido) {
            System.out.println("\nSaldo da conta1 após transferência: R$" + conta1.getSaldo());
            System.out.println("Saldo da conta2 após transferência: R$" + conta2.getSaldo());
        }

        System.out.println("\nSaldo da conta2 antes do depósito: R$" + conta2.getSaldo());
        conta2.depositar(100.00);
        System.out.println("Saldo da conta2 após depósito: R$" + conta2.getSaldo());

        System.out.println("\nO nome do cliente da conta1 é: " + conta1.getCliente().getNome() + " " + conta1.getCliente().getSobrenome());

        System.out.println("\nTentando transferir R$600.00 de conta1 para conta2...");
        boolean transferenciaCancelada = conta1.transferir(conta2, 600.00);
        if (!transferenciaCancelada) {
            System.out.println("Transferência cancelada. Saldo insuficiente.");
        }

        conta1.exibirExtrato();
        conta2.exibirExtrato();
    }
}
