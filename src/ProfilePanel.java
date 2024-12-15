import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);

    public ProfilePanel(JPanel cardPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents(cardPanel);
    }

    private void initComponents(JPanel cardPanel) {
        JLabel title = createTitleLabel();
        JPanel profileImagePanel = createProfileImagePanel();
        JPanel statsPanel = createStatsPanel();
        JButton backButton = createBackButton(cardPanel);
    
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(title);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(profileImagePanel);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(statsPanel);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(backButton);
    }

    private JLabel createTitleLabel() {
        JLabel title = new JLabel("User Profile");
        title.setFont(TITLE_FONT);
        title.setAlignmentX(CENTER_ALIGNMENT);
        return title;
    }

    private JPanel createProfileImagePanel() {
        JPanel imagePanel = new JPanel(new BorderLayout());
        JLabel imagePlaceholder = new JLabel("Profile Image", SwingConstants.CENTER);
        imagePlaceholder.setPreferredSize(new Dimension(75, 75));
        imagePlaceholder.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    
        imagePanel.add(imagePlaceholder, BorderLayout.CENTER);
        imagePanel.setAlignmentX(CENTER_ALIGNMENT);
        return imagePanel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        statsPanel.setAlignmentX(CENTER_ALIGNMENT);
        statsPanel.setMaximumSize(new Dimension(250, 100));
    
        statsPanel.add(new JLabel("Total Spins:"));
        statsPanel.add(new JLabel("0"));
        statsPanel.add(new JLabel("Total Credits Won:"));
        statsPanel.add(new JLabel("0"));
        statsPanel.add(new JLabel("Highest Win:"));
        statsPanel.add(new JLabel("0"));
        return statsPanel;
    }

    private JButton createBackButton(JPanel cardPanel) {
        JButton backButton = new JButton("Back to Casino");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) cardPanel.getLayout();
            cl.show(cardPanel, "SlotMachine");
        });
        return backButton;
    }
}