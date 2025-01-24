import javax.swing.JOptionPane;

public class ChessGameCLI {

    private static final String[][] board = {
            {"♖", "♘", "♗", "♕", "♔", "♗", "♘", "♖"},
            {"♙", "♙", "♙", "♙", "♙", "♙", "♙", "♙"},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {"♟", "♟", "♟", "♟", "♟", "♟", "♟", "♟"},
            {"♖", "♘", "♗", "♕", "♔", "♗", "♘", "♖"}
    };

    public static void main(String[] args) {
        boolean gameActive = true;
        while (gameActive) {
            String boardRepresentation = getBoardRepresentation();
            JOptionPane.showMessageDialog(null, boardRepresentation, "Tabuleiro de Xadrez", JOptionPane.INFORMATION_MESSAGE);

            String from = JOptionPane.showInputDialog(null, "Digite a posição da peça para mover (ex: e2):");
            if (from == null) break; // Usuário cancelou

            String to = JOptionPane.showInputDialog(null, "Digite o destino da peça (ex: e4):");
            if (to == null) break; // Usuário cancelou

            try {
                int[] fromCoords = parseCoordinates(from);
                int[] toCoords = parseCoordinates(to);

                if (isValidMove(fromCoords, toCoords)) {
                    movePiece(fromCoords, toCoords);
                } else {
                    JOptionPane.showMessageDialog(null, "Movimento inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static String getBoardRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("    a      b      c      d      e      f      g      h\n");
        sb.append("  +-----+-----+-----+-----+-----+-----+-----+-----+\n");
        for (int i = 0; i < 8; i++) {
            sb.append(8 - i).append(" |");
            for (int j = 0; j < 8; j++) {
                sb.append("  ").append(board[i][j]).append("  |");
            }
            sb.append("\n  +-----+-----+-----+-----+-----+-----+-----+-----+\n");
        }
        sb.append("    a      b      c      d      e      f      g      h\n");
        return sb.toString();
    }

    private static int[] parseCoordinates(String position) {
        if (position.length() != 2) {
            throw new IllegalArgumentException("Posição inválida! Use o formato 'e2'.");
        }

        char column = position.charAt(0);
        char row = position.charAt(1);

        if (column < 'a' || column > 'h' || row < '1' || row > '8') {
            throw new IllegalArgumentException("Posição fora do tabuleiro!");
        }

        return new int[]{8 - (row - '1') - 1, column - 'a'};
    }

    private static boolean isValidMove(int[] from, int[] to) {
        String piece = board[from[0]][from[1]];
        return !piece.equals(" ") && board[to[0]][to[1]].equals(" ");
    }

    private static void movePiece(int[] from, int[] to) {
        board[to[0]][to[1]] = board[from[0]][from[1]];
        board[from[0]][from[1]] = " ";
    }
}
