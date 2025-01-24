import java.util.Scanner;

public class LocalizaNumero {
    public static void main(String[] args) {
        int vetor[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        Scanner scanner = new Scanner(System.in);
        System.out.println("==============================");
        System.out.println("        LOCALIZA NÚMERO       ");
        System.out.println("==============================");

        System.out.print("Digite o número que deseja localizar: ");
        int numero = scanner.nextInt();

        boolean achou = false;
        int posicao = -1;

        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i] == numero) {
                achou = true;
                posicao = i;
                break;
            }
        }

        System.out.println("------------------------------");
        if (achou) {
            System.out.printf("Achei! Na posição %d está localizado o número %d.%n", posicao, numero);
        } else {
            System.out.printf("O número %d NÃO foi encontrado no vetor.%n", numero);
        }
        System.out.println("==============================");

        scanner.close();
    }
}
