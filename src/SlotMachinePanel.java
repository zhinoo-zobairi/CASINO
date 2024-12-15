import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SlotMachinePanel extends JPanel {
    private static final String[] SYMBOLS = {"🍒", "🍋", "🍉", "🍇", "🍊"};
    private static final int SPIN_COST = 10;
    private static final int INITIAL_CREDITS = 100;

    private JLabel symbolsLabel;
    private JButton spinButton;
    private JLabel creditsLabel;

    private int credits = INITIAL_CREDITS;
    private Random random = new Random();

    public SlotMachinePanel(JPanel cardPanel) {
        setLayout(new BorderLayout());

        JPanel leftPanel = createLeftPanel(cardPanel);
        JPanel centerPanel = createCenterPanel();

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createLeftPanel(JPanel cardPanel) {
        JPanel leftPanel = new JPanel(new BorderLayout());

        JButton menuButton = new JButton("☰");
        
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        JButton profileButton = new JButton("Profile");
        profileButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) cardPanel.getLayout();
            cl.show(cardPanel, "Profile");
        });

        menuPanel.add(profileButton);
        menuPanel.setVisible(false);


        menuButton.addActionListener(e -> menuPanel.setVisible(!menuPanel.isVisible()));


        leftPanel.add(menuButton, BorderLayout.NORTH);
        leftPanel.add(menuPanel, BorderLayout.CENTER);

        return leftPanel;
    }


    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);


        symbolsLabel = new JLabel(generateSymbols());
        symbolsLabel.setFont(new Font("Arial", Font.BOLD, 48));
        

        spinButton = new JButton("Spin (" + SPIN_COST + " credits)");
        spinButton.addActionListener(e -> spin());


        creditsLabel = new JLabel("Credits: " + credits);


        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(symbolsLabel, gbc);

        gbc.gridy = 1;
        centerPanel.add(spinButton, gbc);

        gbc.gridy = 2;
        centerPanel.add(creditsLabel, gbc);

        return centerPanel;
    }


    private String generateSymbols() {
        return String.format("%s   %s   %s", 
            SYMBOLS[random.nextInt(SYMBOLS.length)],
            SYMBOLS[random.nextInt(SYMBOLS.length)],
            SYMBOLS[random.nextInt(SYMBOLS.length)]
        );
    }

    private void spin() {
        if (credits >= SPIN_COST) {
            credits -= SPIN_COST;
            
            symbolsLabel.setText(generateSymbols());
            
            creditsLabel.setText("Credits: " + credits);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Not enough credits!", 
                "Insufficient Funds", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
}