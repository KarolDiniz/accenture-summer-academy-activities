import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TicketSales {

    enum TicketClass {
        A(50), B(30), C(20);

        private final int price;

        TicketClass(int price) {
            this.price = price;
        }

        public int getPrice() {
            return price;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<TicketClass, Integer> ticketsSold = new HashMap<>();

        for (TicketClass ticketClass : TicketClass.values()) {
            ticketsSold.put(ticketClass, 0);
        }

        System.out.println("==============================");
        System.out.println("      VENDA DE INGRESSOS      ");
        System.out.println("==============================");

        for (TicketClass ticketClass : TicketClass.values()) {
            System.out.printf("Quantos bilhetes da classe %s (R$ %d) foram vendidos? ", ticketClass, ticketClass.getPrice());
            int quantity = scanner.nextInt();
            ticketsSold.put(ticketClass, quantity);
        }

        System.out.println("\n==============================");
        System.out.println("      RELATÓRIO DE VENDAS     ");
        System.out.println("==============================");

        int totalRevenue = 0;
        for (Map.Entry<TicketClass, Integer> entry : ticketsSold.entrySet()) {
            TicketClass ticketClass = entry.getKey();
            int quantity = entry.getValue();
            int revenue = ticketClass.getPrice() * quantity;
            totalRevenue += revenue;
            System.out.printf("Classe %s: %d bilhetes vendidos - R$ %d\n", ticketClass, quantity, revenue);
        }

        System.out.println("------------------------------");
        System.out.printf("Renda total gerada: R$ %d\n", totalRevenue);
        System.out.println("==============================");

        scanner.close();
    }
}
