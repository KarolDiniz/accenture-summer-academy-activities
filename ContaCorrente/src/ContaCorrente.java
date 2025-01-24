import java.time.LocalDate;

class ContaCorrente {
    private String numero;
    private Cliente cliente;
    private double saldo;
    private LocalDate data;

    public ContaCorrente(String numero, Cliente cliente, double saldoInicial) {
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = saldoInicial;
        this.data = LocalDate.now();
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("\nDepósito de R$" + valor + " realizado com sucesso!");
        } else {
            System.out.println("\nValor de depósito inválido.");
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            System.out.println("\nSaque de R$" + valor + " realizado com sucesso!");
            return true;
        } else {
            System.out.println("\nSaldo insuficiente para o saque de R$" + valor);
            return false;
        }
    }

    public void exibirExtrato() {
        System.out.println("\nExtrato da conta " + numero);
        cliente.exibirDados();
        System.out.println("Saldo atual: R$" + saldo);
        System.out.println("Data: " + data);
        System.out.println("------------------------------");
    }

    public boolean transferir(ContaCorrente destino, double valor) {
        if (this.saldo >= valor) {
            this.saldo -= valor;
            destino.saldo += valor;
            System.out.println("\nTransferência de R$" + valor + " para a conta " + destino.numero + " realizada com sucesso!");
            return true;
        } else {
            System.out.println("\nTransferência não realizada. Saldo insuficiente na conta de origem.");
            return false;
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }
}

