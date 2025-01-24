public class ChessGame {
    private final ChessBoard board;
    private int[] selectedPiece = null;

    public ChessGame() {
        this.board = new ChessBoard();
    }

    public ChessBoard getBoard() {
        return board;
    }

    public String handleSelection(int row, int col) {
        if (selectedPiece == null) {
            if (!board.isEmpty(row, col)) {
                selectedPiece = new int[]{row, col};
                return "Peça selecionada em " + board.translateCoordinates(row, col) + ". Clique no destino.";
            }
            return "Selecione uma peça válida para mover.";
        } else {
            int[] destination = new int[]{row, col};
            String piece = board.getPiece(selectedPiece[0], selectedPiece[1]);

            if (board.validateMove(selectedPiece, destination)) {
                board.movePiece(selectedPiece, destination);
                String message = "Movimento realizado: " + board.translateCoordinates(selectedPiece[0], selectedPiece[1])
                        + " -> " + board.translateCoordinates(row, col);
                selectedPiece = null;
                return message;
            } else {
                selectedPiece = null;
                return "Movimento inválido! Tente novamente.";
            }
        }
    }
}
