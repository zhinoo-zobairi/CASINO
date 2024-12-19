import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class Casino {
    public static void main(String[] args) {
        final CasinoLogic casinoLogic = new CasinoLogic(10000);
        JFrame frame = new JFrame("Casino");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        JButton menuButton = new JButton("☰");
        leftPanel.add(menuButton, BorderLayout.NORTH);
        frame.add(leftPanel, BorderLayout.WEST);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.add(new JButton("Analysis"));
        menuPanel.add(new JButton("Profile"));
        menuPanel.setVisible(false); 
        leftPanel.add(menuPanel, BorderLayout.CENTER);

        menuButton.addActionListener(e -> {
            menuPanel.setVisible(!menuPanel.isVisible()); 
            frame.revalidate();
            frame.repaint();
        });

        JPanel rightMenu = new JPanel();
        JButton aiButton = new JButton("Croupier");
        rightMenu.add(aiButton);
        frame.add(rightMenu, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(); 
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel reelsPanel = new JPanel(new GridLayout(1, 3));
        JLabel reel1 = new JLabel("🍒", JLabel.CENTER);
        JLabel reel2 = new JLabel("🍋", JLabel.CENTER);
        JLabel reel3 = new JLabel("🍉", JLabel.CENTER);
        reel1.setFont(new Font("Arial", Font.BOLD, 40));
        reel2.setFont(new Font("Arial", Font.BOLD, 40));
        reel3.setFont(new Font("Arial", Font.BOLD, 40));
        reelsPanel.add(reel1);
        reelsPanel.add(reel2);
        reelsPanel.add(reel3);


        centerPanel.add(reelsPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        

        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JLabel moneyWindow = new JLabel("<html>Your current wealth:<br>"+ casinoLogic.getAmountMoney() + "</html>");
        moneyWindow.setFont(new Font("Arial", Font.BOLD, 16));

        JButton spinButton = new JButton("Spin - Space bar");
        spinButton.setFont(new Font("Arial", Font.BOLD, 20));
        spinButton.setPreferredSize(new Dimension(450, 60));
        
    
        JTextField betSizeWindow = new JTextField();
        betSizeWindow.setPreferredSize(new Dimension(150, 60));
        betSizeWindow.setFont(new Font("Arial", Font.BOLD, 30));
 
        ((AbstractDocument) betSizeWindow.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d*")) { 
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d*")) { 
                    super.replace(fb, offset, length, string, attr);
                }
            }
        });

        

        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int betAmount = Integer.parseInt(betSizeWindow.getText());
                    String[] generatedReels = casinoLogic.generateReels();
                    String result = casinoLogic.spin(betAmount,generatedReels);
                    moneyWindow.setText("<html>" + result.replace("\n", "<br>") + "</html>");
                    reel1.setText(generatedReels[0]);
                    reel2.setText(generatedReels[1]);
                    reel3.setText(generatedReels[2]);

                    if (casinoLogic.getAmountMoney() <= 0) {
                        spinButton.setEnabled(false);
                    }
                } catch (NumberFormatException ex) {
                    moneyWindow.setText("<html>Enter a valid number!</html>");
                }
            }
        });


        lowerPanel.add(moneyWindow,BorderLayout.WEST);
        lowerPanel.add(spinButton,BorderLayout.CENTER);
        lowerPanel.add(betSizeWindow,BorderLayout.EAST);
        
        frame.add(lowerPanel, BorderLayout.SOUTH);

        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}