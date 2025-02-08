import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class Profile extends JFrame {
    private JTextArea textArea;
    private JTextArea compareTextArea;
    private JScrollPane scrollPane;
    private JScrollPane compareScrollPane;
    private boolean isCompareVisible = false;

    /**
     * Konstruktor: Zeigt das Profil eines bestimmten Benutzers (Statistiken, Vergleich usw.).
     * @param username Der Benutzername, dessen Profil angezeigt wird.
     */
    public Profile(String username) {

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(600, 400);
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;


        JLabel titleLabel = new JLabel("Game Statistics", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 0.1;
        this.add(titleLabel, gbc);


        textArea = new JTextArea(10, 20);
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);

        String csvFile = "./data.csv";  
        String line;
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        
        int playerCount = 0;
        float usersGames = 0;
        float usersGamesWon = 0;
        int sumOfPlayersMoney = 0;
        float winrate;
        float averageWealth;

        // Liest Daten aus data.csv und füllt die StringBuilder mit Profil- und Vergleichsdaten
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                playerCount++;
                usersGames += Float.parseFloat(values[3]);
                sumOfPlayersMoney += Integer.parseInt(values[2]);
                usersGamesWon += Float.parseFloat(values[4]);
                if (username.equals(values[0])){
                    sb1.append("username: "+values[0]).append("\n");
                    sb1.append("current wealth: " + Float.parseFloat(values[2])).append("\n");
                    sb1.append("games played: " + Float.parseFloat(values[3])).append("\n");
                    sb1.append("winrate: " + String.valueOf(
                        Float.parseFloat(values[4]) / Float.parseFloat(values[3])*100)).append("% \n");
                }
            }
            winrate = (usersGamesWon / usersGames * 100);
            averageWealth = sumOfPlayersMoney / playerCount;
            sb2.append("player count: " + playerCount).append("\n");
            sb2.append("average wealth: " + averageWealth).append("\n");
            sb2.append("player games: " + usersGames).append("\n");
            sb2.append("winrate: " + winrate).append("% \n");
        } catch (IOException e) {
            new Exception("Error: Data hasnt loaded properly!");
        }

        textArea.setText(sb1.toString());
        textArea.setFont(new Font("Arial", Font.BOLD, 15));

        gbc.gridy = 1;
        gbc.gridx = 1; 
        gbc.gridwidth = 1;
        gbc.weighty = 0.8;
        this.add(scrollPane, gbc);

        // Zweites Textfeld für Vergleichsdaten (zunächst unsichtbar)
        compareTextArea = new JTextArea(10, 20);
        compareTextArea.setEditable(false);
        compareScrollPane = new JScrollPane(compareTextArea);

        compareTextArea.setText(sb2.toString());
        compareTextArea.setFont(new Font("Arial", Font.BOLD, 15));

        // Panel für Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton backButton = new JButton("Back");
        JButton resetButton = new JButton("Reset");
        JButton compareButton = new JButton("Compare");

        // Festgelegte Button-Größen
        Dimension buttonSize = new Dimension(150, 40);
        backButton.setPreferredSize(buttonSize);
        resetButton.setPreferredSize(buttonSize);
        compareButton.setPreferredSize(buttonSize);

        buttonPanel.add(backButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(compareButton);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(buttonPanel, gbc);

        /**
         * ActionListener für den Compare-Button:
         * Zeigt/Hidet die zweite TextArea mit Vergleichsdaten.
         */
        compareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isCompareVisible) {
                    remove(scrollPane);

                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.gridwidth = 1;
                    gbc.weighty = 0.8;
                    gbc.fill = GridBagConstraints.BOTH;
                    add(scrollPane, gbc);

                    gbc.gridx = 1;
                    add(compareScrollPane, gbc);
                } else {
                    remove(compareScrollPane);
                    remove(scrollPane);

                    gbc.gridx = 1;
                    add(scrollPane, gbc);
                }
                isCompareVisible = !isCompareVisible;

                revalidate();
                repaint();
            }
        });

        /**
         * ActionListener für den Back-Button: Kehrt zum Spielbildschirm zurück.
         */
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CasinoMain.showSecondFrame(username);
            }
        });

        /**
         * ActionListener für den Reset-Button:
         * Setzt das Geld des Spielers in der CSV-Datei auf 10000 und aktualisiert das Textfeld.
         */
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CasinoLogic.resetPlayerMoney(username);
                    String[] lines = sb1.toString().split("\n");
                    lines[1] = "current wealth: 10000.0";
                    sb1.setLength(0);
                    for (int i = 0; i < lines.length; i++) {
                        sb1.append(lines[i]);
                        if (i < lines.length - 1) {
                            sb1.append("\n");
                        }
                    }
                    textArea.setText(sb1.toString());
                    revalidate();
                    repaint();
                } catch (IOException e1) {
                }
            }
        });

        this.setVisible(true);
    }

 
    public static void main(String[] args) {
        new Profile("bartlew");
    }
}