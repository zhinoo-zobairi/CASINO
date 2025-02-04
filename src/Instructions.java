import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Instructions extends JFrame {
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public Instructions(String username) {
        // Frame settings
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(700, 600);
        this.setLayout(new GridBagLayout());

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Label at the top center
        JLabel titleLabel = new JLabel("Instructions", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        this.add(titleLabel, gbc);

        // Uneditable text area with multiple rows
        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);

        textArea.setText(
                        "\n" +
                        "**1. Spielstart:**  \n" +
                        "Wähle zunächst einen Spielmodus über das Menü in der oberen linken Ecke. " + "\n" +"Nach der Auswahl wirst du direkt ins Spiel weitergeleitet.\n" +
                        "\n" +
                        "**2. Einsatz und Guthaben:**  \n" +
                        "Auf der rechten Seite des Bildschirms siehst du dein aktuelles Guthaben sowie den Einsatzbetrag." +"\n"+" Du kannst den Einsatz anpassen, um deine potenziellen Gewinne zu beeinflussen.\n" +
                        "\n" +
                        "**3. Spielablauf:**  \n" +
                        "Drücke den *Spin-Button*, um die \"Walzen\" zu starten. Die Walzen generieren zufällige Symbole."+"\n" +"Abhängig vom gewählten Spielmodus erzielst du Gewinne, wenn:\n" +
                        "- Symbole in einer Reihe (horizontal oder vertikal) identisch sind.\n" +
                        "- Bestimmte Formen mit identischen Symbolen gebildet werden.\n" +
                        "\n" +
                        "**4. Bonus \"On a Roll\":**  \n" +
                        "Nach dem Erzielen von zwei oder mehr Formen im selben Spiel wird der Bonus *\"On a Roll\"* aktiviert."+"\n" + "Dieser Bonus multipliziert deine Gewinne. Der Multiplikator stapelt sich und erhöht sich weiter, wenn du"+"\n" +"erneut zwei oder mehr Formen in Folge erzielst.\n" +
                        "\n" +
                        "**5. Profil und Statistiken:**  \n" +
                        "Im Profilmenü kannst du deine Spielstatistiken einsehen und deine Erfolge mit anderen Spielern vergleichen."+"\n"+"Falls gewünscht, kannst du hier auch dein Profil zurücksetzen.\n" +
                        "\n" +
                        "Viel Erfolg und viel Spaß beim Spielen!\n");
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.8;
        this.add(scrollPane, gbc);

        // Panel for buttons to keep their size constant
        JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 10, 0));
        JButton backButton = new JButton("Back");

        // Set preferred size for buttons to make them bigger
        Dimension buttonSize = new Dimension(200, 60);
        backButton.setPreferredSize(buttonSize);
        buttonPanel.add(backButton);

        gbc.gridy = 2;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(buttonPanel, gbc);

        // Action listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CasinoMain.showSecondFrame(username);
            }
        });

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Instructions("bartlew");
    }
}
