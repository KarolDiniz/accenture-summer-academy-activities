import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ChatbotUI {
    private static final String API_KEY = System.getenv("GOOGLE_API_KEY");
    private static final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:streamGenerateContent";
    private static final ArrayList<String> history = new ArrayList<>();
    private static final int CAPACITY = 100;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                System.err.println("Failed to set LookAndFeel: " + e.getMessage());
            }
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Chatbot - Interface Moderna");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea conversationArea = new JTextArea();
        conversationArea.setEditable(false);
        conversationArea.setLineWrap(true);
        conversationArea.setWrapStyleWord(true);
        conversationArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(conversationArea);

        JTextField inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JButton sendButton = new JButton("Enviar");
        JButton historyButton = new JButton("Histórico");
        JButton clearButton = new JButton("Limpar Contexto");

        sendButton.setBackground(new Color(60, 179, 113));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);

        historyButton.setBackground(new Color(70, 130, 180));
        historyButton.setForeground(Color.WHITE);
        historyButton.setFocusPainted(false);

        clearButton.setBackground(new Color(220, 20, 60));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(historyButton);
        buttonPanel.add(clearButton);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Event Handlers
        sendButton.addActionListener(e -> processInput(inputField, conversationArea, frame));
        historyButton.addActionListener(e -> showHistory(frame));
        clearButton.addActionListener(e -> clearContext(conversationArea, frame));
    }

    private static void processInput(JTextField inputField, JTextArea conversationArea, JFrame frame) {
        String prompt = inputField.getText().trim();
        if (prompt.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Digite uma pergunta válida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        inputField.setText("");
        try {
            String response = sendRequest(prompt);
            conversationArea.append("Você: " + prompt + "\n");
            conversationArea.append("Bot: " + response + "\n\n");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao processar a requisição.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showHistory(JFrame frame) {
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "O histórico está vazio.", "Histórico", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String historyContent = String.join("\n", history);
            JOptionPane.showMessageDialog(frame, historyContent, "Histórico", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void clearContext(JTextArea conversationArea, JFrame frame) {
        history.clear();
        conversationArea.setText("");
        JOptionPane.showMessageDialog(frame, "O contexto foi limpo.", "Limpar Contexto", JOptionPane.INFORMATION_MESSAGE);
    }

    private static String sendRequest(String prompt) throws IOException, InterruptedException {
        String context = history.stream().collect(Collectors.joining("\n"));
        String fullPrompt = context + "return the result as plain text without any formatting, special characters, or backticks. Question:" + prompt;
        String jsonRequest = "{\"contents\":[{\"parts\":[{\"text\":\"" + fullPrompt + "\"}],\"role\":\"user\"}]}";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "?alt=sse&key=" + API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Erro na API: " + response.statusCode());
        }

        StringBuilder answer = new StringBuilder();
        Pattern pattern = Pattern.compile("\\\"text\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"");
        try (BufferedReader reader = new BufferedReader(new StringReader(response.body()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) continue;
                Matcher matcher = pattern.matcher(line.substring(5));
                if (matcher.find()) {
                    answer.append(matcher.group(1));
                }
            }
        }

        String result = (answer.length() > 2) ? answer.substring(0, answer.length() - 2) : "Resposta não encontrada.";
        addToHistory(prompt);
        return result;
    }

    private static void addToHistory(String prompt) {
        if (history.size() == CAPACITY) {
            history.remove(0);
        }
        history.add(prompt);
    }
}
