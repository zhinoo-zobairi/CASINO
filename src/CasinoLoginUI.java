import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Base64;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class CasinoLoginUI {
    private static final String DB_URL = "jdbc:sqlite:casino_users.db";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CasinoLoginUI::showLoginFrame);
    }

    private static void showLoginFrame() {
        JFrame frame = new JFrame("Jasino Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("Welcome to Jasino!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(new JLabel("Username:"), gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        frame.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(new JLabel("Password:"), gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        frame.add(passwordField, gbc);

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");


        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(registerButton, gbc);

        gbc.gridx = 1;
        frame.add(loginButton, gbc);

        JLabel statusLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        frame.add(statusLabel, gbc);

        frame.getContentPane().setBackground(new Color(240, 240, 240));

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (loginUser(username, password)) {
                JOptionPane.showMessageDialog(frame, "Login successful! Welcome, " + username);
            } else {
                statusLabel.setText("Invalid login. Try again.");
                statusLabel.setForeground(Color.RED);
            }
        });

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (registerUser(username, password)) {
                JOptionPane.showMessageDialog(frame, "Registration successful! Please log in.");
            } else {
                statusLabel.setText("Registration failed.");
                statusLabel.setForeground(Color.RED);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static boolean loginUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT * FROM users WHERE username=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                byte[] storedSalt = Base64.getDecoder().decode(rs.getString("salt"));
                String hashedInput = hashPassword(password, storedSalt);
                return hashedInput.equals(storedHash);
            }
        } catch (Exception e) {
            e.printStackTrace();
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