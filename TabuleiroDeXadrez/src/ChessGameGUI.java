import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChessGameGUI {
    private final ChessBoard chessBoard;
    private final JLabel statusLabel;
    private int[] selectedPiece = null;

    public ChessGameGUI() {
        chessBoard = new ChessBoard();
        statusLabel = new JLabel("Bem-vindo ao Xadrez! Clique em uma peça para movê-la.");
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Jogo de Xadrez");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30));

        // Painel de tabuleiro
        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        boardPanel.setBackground(new Color(40, 40, 40));

        // Adiciona os botões do tabuleiro
        JButton[][] boardButtons = new JButton[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton(chessBoard.getPiece(row, col));
                styleBoardButton(button, row, col);
                final int currentRow = row;
                final int currentCol = col;
                button.addActionListener(e -> handleButtonClick(currentRow, currentCol, boardButtons));
                boardButtons[row][col] = button;
                boardPanel.add(button);
            }
        }

        // Status label estilizado
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Adiciona o título superior
        JLabel titleLabel = new JLabel("Jogo de Xadrez");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(200, 200, 200));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Adiciona os componentes ao painel principal
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void styleBoardButton(JButton button, int row, int col) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.setBackground((row + col) % 2 == 0 ? new Color(240, 217, 181) : new Color(181, 136, 99));
        button.setForeground(Color.BLACK);
    }

    private void handleButtonClick(int row, int col, JButton[][] boardButtons) {
        if (selectedPiece == null) {
            // Seleciona a peça
            if (!chessBoard.isEmpty(row, col)) {
                selectedPiece = new int[]{row, col};
                statusLabel.setText("Peça selecionada em " + chessBoard.translateCoordinates(row, col) + ". Escolha o destino.");
                boardButtons[row][col].setBackground(Color.YELLOW); // Destaque
            } else {
                statusLabel.setText("Selecione uma peça válida.");
            }
        } else {
            // Move a peça
            int[] destination = new int[]{row, col};
            if (chessBoard.validateMove(selectedPiece, destination)) {
                chessBoard.movePiece(selectedPiece, destination);
                updateBoard(boardButtons);
                statusLabel.setText("Movimento realizado: " + chessBoard.translateCoordinates(selectedPiece[0], selectedPiece[1]) + " -> " + chessBoard.translateCoordinates(row, col));
            } else {
                statusLabel.setText("Movimento inválido. Tente novamente.");
            }
            resetButtonColors(boardButtons); // Reseta as cores
            selectedPiece = null;
        }
    }

    private void resetButtonColors(JButton[][] boardButtons) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardButtons[row][col].setBackground((row + col) % 2 == 0 ? new Color(240, 217, 181) : new Color(181, 136, 99));
            }
        }
    }

    private void updateBoard(JButton[][] boardButtons) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardButtons[row][col].setText(chessBoard.getPiece(row, col));
            }
        }
    }
}
