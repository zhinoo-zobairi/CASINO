import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;

public class CasinoLoginUI extends JFrame{
    private static final String DB_URL = "jdbc:sqlite:casino_users.db";

    {

    }
    public static void main(String[] args) {
    }

    public CasinoLoginUI () {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(400, 300);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("Welcome to Jasino!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Username:"), gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        this.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JLabel("Password:"), gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        this.add(passwordField, gbc);

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");


        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(registerButton, gbc);

        gbc.gridx = 1;
        this.add(loginButton, gbc);

        JLabel statusLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        this.add(statusLabel, gbc);

        this.getContentPane().setBackground(new Color(240, 240, 240));

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (loginUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + username);
                CasinoMain.showSecondFrame(username);
            } else if (loginUser(username, password)== false){
                statusLabel.setText("Invalid login. Try again.");
                statusLabel.setForeground(Color.RED);
            }
        }); 
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (registerUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Registration successful! Please log in.");
            } else {
                statusLabel.setText("Registration failed.");
                statusLabel.setForeground(Color.RED);
            }
        });

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private static boolean loginUser(String username, String password) {
        String[] usernames = getAllUsernames();
        String[] passwords = getAllPasswords();
        for (int i = 0; i < usernames.length; i++) {
            if (username.equals(usernames[i]) && password.equals(passwords[i])) {
                return true;
            } else if (username.equals(usernames[i]) && !password.equals(passwords[i])) {
                return true;
            }
            
        }
        return false;
    }

    private static boolean registerUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username and password cannot be empty.");
            return false;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String checkUserSQL = "SELECT COUNT(*) FROM users WHERE username=?";
            PreparedStatement checkStmt = conn.prepareStatement(checkUserSQL);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Username already exists!");
                return false;
            }

            byte[] salt = generateSalt();
            String hashedPassword = hashPassword(password, salt);
            String encodedSalt = Base64.getEncoder().encodeToString(salt);

            String insertSQL = "INSERT INTO users (username, password, salt) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSQL);
            insertStmt.setString(1, username);
            insertStmt.setString(2, hashedPassword);
            insertStmt.setString(3, encodedSalt);
            insertStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String[] getAllUsernames (){
        ArrayList<String> usernames = new ArrayList<>();
        String csvFile = "./data.csv";  // Path to your CSV file
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";"); 
                usernames.add(values[0]);
            }
        } catch (IOException e) {
            new Exception("Error: Data hasnt loaded properly!");
        }
        return usernames.stream()
        .map(String::valueOf)
        .toArray(String[]::new);
    }

    private static String[] getAllPasswords (){
        ArrayList<String> passwords = new ArrayList<>();
        String csvFile = "./data.csv";  // Path to your CSV file
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";"); 
                passwords.add(values[1]);
            }
        } catch (IOException e) {
            new Exception("Error: Data hasnt loaded properly!");
        }
        System.out.println("");
        return passwords.stream()
        .map(String::valueOf)
        .toArray(String[]::new);
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }


    private static String hashPassword(String password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }
}