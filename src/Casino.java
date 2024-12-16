import javax.swing.*;
import java.awt.*;

public class Casino {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = createMainFrame();
            JPanel cardPanel = createCardPanel(frame);
            frame.setVisible(true);
        });
    }

    private static JFrame createMainFrame() {
        JFrame frame = new JFrame("Casino Game");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); 
        frame.setLayout(new BorderLayout());
        return frame;
    }

    private static JPanel createCardPanel(JFrame frame) {
        JPanel cardPanel = new JPanel(new CardLayout());

        ProfilePanel profilePage = new ProfilePanel(cardPanel);

        cardPanel.add(profilePage, "Profile");

        frame.add(cardPanel, BorderLayout.CENTER);
        return cardPanel;
    }
}