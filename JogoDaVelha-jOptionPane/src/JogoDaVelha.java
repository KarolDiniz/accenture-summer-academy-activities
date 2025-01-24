import javax.swing.JOptionPane;

public class JogoDaVelha {

    public static void main(String[] args) {
        String[][] tabuleiro = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
        String jogadorAtual = "❌"; // Usando emojis para representar os jogadores
        boolean jogoAtivo = true;

        while (jogoAtivo) {
            exibirTabuleiro(tabuleiro);
            String inputLinha = JOptionPane.showInputDialog(null, jogadorAtual + " é sua vez! Digite a linha (0, 1, 2):", "Jogo da Velha", JOptionPane.QUESTION_MESSAGE);
            String inputColuna = JOptionPane.showInputDialog(null, jogadorAtual + " é sua vez! Digite a coluna (0, 1, 2):", "Jogo da Velha", JOptionPane.QUESTION_MESSAGE);

            if (inputLinha == null || inputColuna == null) {
                JOptionPane.showMessageDialog(null, "Jogo encerrado. Até a próxima!", "Jogo da Velha", JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            try {
                int linha = Integer.parseInt(inputLinha);
                int coluna = Integer.parseInt(inputColuna);

                if (linha < 0 || linha > 2 || coluna < 0 || coluna > 2 || !tabuleiro[linha][coluna].equals(" ")) {
                    JOptionPane.showMessageDialog(null, "Movimento inválido! Tente novamente.", "Jogo da Velha", JOptionPane.WARNING_MESSAGE);
                    continue;
                }

                tabuleiro[linha][coluna] = jogadorAtual;

                if (verificarVencedor(tabuleiro, jogadorAtual)) {
                    exibirTabuleiro(tabuleiro);
                    JOptionPane.showMessageDialog(null, "🎉 Parabéns, " + jogadorAtual + " venceu! 🎉", "Jogo da Velha", JOptionPane.INFORMATION_MESSAGE);
                    jogoAtivo = false;
                } else if (tabuleiroCompleto(tabuleiro)) {
                    exibirTabuleiro(tabuleiro);
                    JOptionPane.showMessageDialog(null, "⚖️ Empate! O tabuleiro está completo. ⚖️", "Jogo da Velha", JOptionPane.INFORMATION_MESSAGE);
                    jogoAtivo = false;
                } else {
                    jogadorAtual = jogadorAtual.equals("❌") ? "⭕" : "❌"; // Alterna entre os jogadores
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida! Insira números entre 0 e 2.", "Jogo da Velha", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void exibirTabuleiro(String[][] tabuleiro) {
        StringBuilder sb = new StringBuilder();
        sb.append("  0   1   2\n");
        for (int i = 0; i < 3; i++) {
            sb.append(i).append(" ");
            for (int j = 0; j < 3; j++) {
                sb.append(tabuleiro[i][j].isBlank() ? "⬜" : tabuleiro[i][j]); // Mostra emojis no tabuleiro
                if (j < 2) sb.append(" | ");
            }
            sb.append("\n");
            if (i < 2) sb.append(" ---|---|---\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Tabuleiro", JOptionPane.INFORMATION_MESSAGE);
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
