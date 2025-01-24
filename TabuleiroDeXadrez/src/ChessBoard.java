public class ChessBoard {
    private final String[][] boardState;

    private static final String[][] INITIAL_BOARD = {
            {"R", "N", "B", "Q", "K", "B", "N", "R"},
            {"P", "P", "P", "P", "P", "P", "P", "P"},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {"p", "p", "p", "p", "p", "p", "p", "p"},
            {"r", "n", "b", "q", "k", "b", "n", "r"}
    };

    public ChessBoard() {
        boardState = new String[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(INITIAL_BOARD[i], 0, boardState[i], 0, 8);
        }
    }

    public String getPiece(int row, int col) {
        return boardState[row][col];
    }

    public boolean isEmpty(int row, int col) {
        return " ".equals(boardState[row][col]);
    }

    public void movePiece(int[] from, int[] to) {
        boardState[to[0]][to[1]] = boardState[from[0]][from[1]];
        boardState[from[0]][from[1]] = " ";
    }

    public boolean validateMove(int[] from, int[] to) {
        String piece = boardState[from[0]][from[1]].toLowerCase();
        switch (piece) {
            case "p": return validatePawnMove(from, to);
            case "r": return validateRookMove(from, to);
            case "n": return validateKnightMove(from, to);
            case "b": return validateBishopMove(from, to);
            case "q": return validateQueenMove(from, to);
            case "k": return validateKingMove(from, to);
            default: return false;
        }
    }

    private boolean validatePawnMove(int[] from, int[] to) {
        int direction = boardState[from[0]][from[1]].equals("p") ? 1 : -1;  // Direção
        if (from[1] == to[1]) {
            // Movimento para frente
            if (to[0] == from[0] + direction && isEmpty(to[0], to[1])) {
                return true;
            }
            // Movimento duplo no primeiro turno
            if (to[0] == from[0] + 2 * direction && isEmpty(to[0], to[1]) && isEmpty(from[0] + direction, to[1]) &&
                    ((direction == 1 && from[0] == 6) || (direction == -1 && from[0] == 1))) {
                return true;
            }
        } else if (Math.abs(from[1] - to[1]) == 1 && to[0] == from[0] + direction && !isEmpty(to[0], to[1])) {
            // Captura na diagonal
            return true;
        }
        return false;
    }

    private boolean validateRookMove(int[] from, int[] to) {
        if (from[0] != to[0] && from[1] != to[1]) return false;
        return isPathClear(from, to);
    }

    private boolean validateKnightMove(int[] from, int[] to) {
        int dx = Math.abs(from[0] - to[0]);
        int dy = Math.abs(from[1] - to[1]);
        return dx * dy == 2;  // Produto será 2 para movimentos em "L"
    }

    private boolean validateBishopMove(int[] from, int[] to) {
        if (Math.abs(from[0] - to[0]) != Math.abs(from[1] - to[1])) return false;
        return isPathClear(from, to);
    }

    private boolean validateQueenMove(int[] from, int[] to) {
        return validateRookMove(from, to) || validateBishopMove(from, to);
    }

    private boolean validateKingMove(int[] from, int[] to) {
        int dx = Math.abs(from[0] - to[0]);
        int dy = Math.abs(from[1] - to[1]);
        return dx <= 1 && dy <= 1;
    }

    private boolean isPathClear(int[] from, int[] to) {
        int dx = Integer.compare(to[0], from[0]);
        int dy = Integer.compare(to[1], from[1]);
        int x = from[0] + dx, y = from[1] + dy;

        while (x != to[0] || y != to[1]) {
            if (!isEmpty(x, y)) return false;
            x += dx;
            y += dy;
        }
        return true;
    }

    public String translateCoordinates(int row, int col) {
        char column = (char) ('a' + col);
        int line = 8 - row;
        return "" + column + line;
    }
}
