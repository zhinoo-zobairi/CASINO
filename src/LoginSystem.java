import java.sql.*;
import javax.swing.*;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;
import java.security.SecureRandom;

public class LoginSystem {
    private static final String DB_URL = "jdbc:sqlite:casino_users.db";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginSystem::showLoginMenu);
    }

    private static void showLoginMenu() {
        String[] options = {"Login", "Register", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, "Welcome to Casino!\nChoose an option:",
                "Casino Login System", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0 -> loginUser();
            case 1 -> registerUser();
            case 2 -> System.exit(0);
        }
    }

    private static void registerUser() {
        String username = JOptionPane.showInputDialog("Enter a new username:");
        String password = JOptionPane.showInputDialog("Enter a new password:");
    
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username and password cannot be empty.");
            return;
        }
    
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String checkUserSQL = "SELECT COUNT(*) FROM users WHERE username=?";
            PreparedStatement checkStmt = conn.prepareStatement(checkUserSQL);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
    
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Username already exists!");
                return;
            }
    
            byte[] salt = generateSalt();
            String hashedPassword = hashPassword(password, salt);
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
    
            String insertSQL = "INSERT INTO users (username, password, salt) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSQL);
            insertStmt.setString(1, username);
            insertStmt.setString(2, hashedPassword);
            insertStmt.setString(3, encodedSalt);
    
            int rowsAffected = insertStmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "User registered successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Registration failed. No data inserted.");
            }
    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error during registration: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void loginUser() {
        String username = JOptionPane.showInputDialog("Enter your username:");
        String password = JOptionPane.showInputDialog("Enter your password:");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter valid credentials.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT * FROM users WHERE username=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                byte[] storedSalt = Base64.getDecoder().decode(rs.getString("salt"));
                String hashedInput = hashPassword(password, storedSalt);

                if (hashedInput.equals(storedHash)) {
                    JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + username);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid password.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "User not found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error logging in: " + e.getMessage());
        }
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