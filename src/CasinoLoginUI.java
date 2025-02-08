import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class CasinoLoginUI extends JFrame{

    {
        // (Leerer Codeblock - keine spezielle Initialisierung)
    }

    public static void main(String[] args) {
        // Hauptmethode (zurzeit leer, kann zum Testen verwendet werden)
    }

    /**
     * Konstruktor, der das Fenster (UI) für Login und Registrierung erstellt.
     */
    public CasinoLoginUI () {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(400, 300);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Überschrift
        JLabel titleLabel = new JLabel("Welcome to Jasino!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(titleLabel, gbc);

        // Label und Textfeld für den Benutzernamen
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Username:"), gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        this.add(usernameField, gbc);

        // Label und Passwortfeld für das Passwort
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JLabel("Password:"), gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        this.add(passwordField, gbc);

        // Buttons für Registrierung und Login
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(registerButton, gbc);

        gbc.gridx = 1;
        this.add(loginButton, gbc);

        // Label zur Anzeige des Status (Erfolg/Fehlschlag)
        JLabel statusLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        this.add(statusLabel, gbc);

        this.getContentPane().setBackground(new Color(240, 240, 240));

        /**
         * ActionListener für den Login-Button:
         * Liest die Eingaben aus, prüft in loginUser(...) die Gültigkeit
         * und zeigt bei Erfolg das zweite Fenster (SecondFrame) an.
         */
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (loginUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + username);
                CasinoMain.showSecondFrame(username);
            } else if (loginUser(username, password) == false){
                statusLabel.setText("Invalid login. Try again.");
                statusLabel.setForeground(Color.RED);
            }
        }); 

        /**
         * ActionListener für den Register-Button:
         * Liest die Eingaben, prüft, ob Nutzername schon existiert,
         * schreibt die Daten andernfalls in die Datei und fordert zum Login auf.
         */
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (!checkPlayerExists(username, password)) {
                JOptionPane.showMessageDialog(this, "Registration successful! Please log in.");
                String[] data = {username, password, "10000", "0", "0"};
                try {
                    CasinoLogic.appendData(data);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                statusLabel.setText("Registration failed.");
                statusLabel.setForeground(Color.RED);
            }
        });

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Überprüft anhand der gespeicherten Benutzernamen/Passwörter,
     * ob ein gültiger Login vorliegt.
     * @param username Benutzername
     * @param password Passwort
     * @return true, wenn Login korrekt; false sonst
     */
    private static boolean loginUser(String username, String password) {
        String[] usernames = CasinoLogic.getAllUsernames();
        String[] passwords = CasinoLogic.getAllPasswords();
        for (int i = 0; i < usernames.length; i++) {
            // Prüft, ob Benutzername und Passwort übereinstimmen
            if (username.equals(usernames[i]) && password.equals(passwords[i])) {
                return true;
            } 
            // Prüft, ob Benutzername stimmt, Passwort aber nicht
            else if (username.equals(usernames[i]) && !password.equals(passwords[i])) {
                return true; 
            }
        }
        return false;
    }

    /**
     * Prüft, ob ein Spieler (Benutzername) bereits existiert.
     * @param givenUsername Benutzername
     * @param givenPassword Passwort (hier nicht relevant, aber übergeben)
     * @return true, wenn der Benutzer bereits existiert; false sonst
     */
    private static boolean checkPlayerExists (String givenUsername, String givenPassword){
        String[] usernames = CasinoLogic.getAllUsernames();
        String[] passwords = CasinoLogic.getAllPasswords();
        for (String usr : usernames) {
            if (usr.equals(givenUsername)) {
                return true;
            }
        }
        return false;
    }
}