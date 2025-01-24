import javax.swing.SwingUtilities;

public class ChessGameApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGameGUI::new);
    }
}
