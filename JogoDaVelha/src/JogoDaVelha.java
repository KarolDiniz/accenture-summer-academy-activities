import java.util.Scanner;

public class JogoDaVelha {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[][] tabuleiro = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
        String jogadorAtual = "X";
        boolean jogoAtivo = true;

        while (jogoAtivo) {
            imprimirTabuleiro(tabuleiro);
            System.out.println("\nVez do jogador: " + jogadorAtual);
            System.out.print("Digite a linha (0, 1, 2): ");
            int linha = scanner.nextInt();
            System.out.print("Digite a coluna (0, 1, 2): ");
            int coluna = scanner.nextInt();

            if (linha < 0 || linha > 2 || coluna < 0 || coluna > 2 || !tabuleiro[linha][coluna].equals(" ")) {
                System.out.println("\nMovimento inválido, tente novamente.");
                continue;
            }

            tabuleiro[linha][coluna] = jogadorAtual;
            if (verificarVencedor(tabuleiro, jogadorAtual)) {
                imprimirTabuleiro(tabuleiro);
                System.out.println("\nParabéns, jogador " + jogadorAtual + " venceu!");
                jogoAtivo = false;
            } else if (tabuleiroCompleto(tabuleiro)) {
                imprimirTabuleiro(tabuleiro);
                System.out.println("\nEmpate! O tabuleiro está completo.");
                jogoAtivo = false;
            } else {
                jogadorAtual = jogadorAtual.equals("X") ? "O" : "X";
            }
        }
        scanner.close();
    }

    private static void imprimirTabuleiro(String[][] tabuleiro) {
        System.out.println("\n  0   1   2");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(tabuleiro[i][j]);
                if (j < 2) System.out.print(" | ");
            }
            System.out.println();
            if (i < 2) System.out.println(" ---|---|---");
        }
    }

    private static boolean verificarVencedor(String[][] tabuleiro, String jogador) {
        for (int i = 0; i < 3; i++) {
            if ((tabuleiro[i][0].equals(jogador) && tabuleiro[i][1].equals(jogador) && tabuleiro[i][2].equals(jogador)) ||
                    (tabuleiro[0][i].equals(jogador) && tabuleiro[1][i].equals(jogador) && tabuleiro[2][i].equals(jogador))) {
                return true;
            }
        }
        return (tabuleiro[0][0].equals(jogador) && tabuleiro[1][1].equals(jogador) && tabuleiro[2][2].equals(jogador)) ||
                (tabuleiro[0][2].equals(jogador) && tabuleiro[1][1].equals(jogador) && tabuleiro[2][0].equals(jogador));
    }

    private static boolean tabuleiroCompleto(String[][] tabuleiro) {
        for (String[] linha : tabuleiro) {
            for (String celula : linha) {
                if (celula.equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }
}
