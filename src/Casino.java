import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

class ReelPanel extends JPanel {
    private JLabel[] reels;
    private boolean[] winningRows = new boolean[5];
    private boolean[] winningColumns = new boolean[5];

    public ReelPanel(JLabel[] reels) {
        this.reels = reels;
        setLayout(new GridLayout(5, 5, 10, 10)); // Ensuring consistent spacing
    }

    public void setWinningRows(boolean[] winningRows, boolean[] winningColumns) {
        this.winningRows = winningRows;
        this.winningColumns = winningColumns;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(5));

        // Draw horizontal winning lines
        for (int row = 0; row < 5; row++) {
            if (winningRows[row]) {
                int y = (row * getHeight() / 5) + (getHeight() / 10); // Ensure consistent positioning
                g2.drawLine(10, y, getWidth() - 10, y);
            }
        }

        // Draw vertical winning lines (fixed)
        for (int column = 0; column < 5; column++) {
            if (winningColumns[column]) { // Corrected: Using winningColumns
                int x = (column * getWidth() / 5) + (getWidth() / 10); // Correct position for columns
                g2.drawLine(x, 10, x, getHeight() - 10);
            }
        }
    }
}

class CasinoLogic {
    private int money;

    public CasinoLogic(int initialMoney) {
        this.money = initialMoney;
    }

    public int getAmountMoney() {
        return money;
    }

    public String[] generateReels(int size) {
        String[] symbols = {"🍒", "🍋", "🍉", "🍊", "🍇", "🍓", "🥝", "🍍", "🍌", "🍏"};
        String[] generated = new String[size];
        for (int i = 0; i < size; i++) {
            generated[i] = symbols[(int) (Math.random() * symbols.length)];
        }
        return generated;
    }

    public String spin(int bet, String[] reels) {
        if (bet > money) return "Not enough money!";
        money -= bet;

        return "You spun the reels!";
    }
}

public class Casino {
    public static void main(String[] args) {
        final CasinoLogic casinoLogic = new CasinoLogic(10000);
        JFrame frame = new JFrame("Casino Game");
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

        JLabel[] reels = new JLabel[25];
        ReelPanel reelsPanel = new ReelPanel(reels);
        String[] symbols = {"🍒", "🍋", "🍉", "🍊", "🍇", "🍓", "🥝", "🍍", "🍌", "🍏"};

        for (int i = 0; i < reels.length; i++) {
            reels[i] = new JLabel(symbols[i % symbols.length], JLabel.CENTER);
            reels[i].setFont(new Font("Arial", Font.BOLD, 40));
            reelsPanel.add(reels[i]);
        }

        centerPanel.add(reelsPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel moneyWindow = new JLabel("<html>Your current wealth:<br>" + casinoLogic.getAmountMoney() + "</html>");
        moneyWindow.setFont(new Font("Arial", Font.BOLD, 16));
        moneyWindow.setHorizontalAlignment(SwingConstants.CENTER);

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
                    String[] generatedReels = casinoLogic.generateReels(25);
                    String result = casinoLogic.spin(betAmount, generatedReels);
                    moneyWindow.setText("<html>" + result.replace("\n", "<br>") + "</html>");

                    boolean[] winningRows = new boolean[5];
                    boolean[] winningColumns = new boolean[5];

                    for (int row = 0; row < 5; row++) {
                        boolean match = true;
                        String first = generatedReels[row * 5];
                        for (int col = 1; col < 5; col++) {
                            if (!generatedReels[row * 5 + col].equals(first)) {
                                match = false;
                                break;
                            }
                        }
                        winningRows[row] = match;
                    }

                    for (int column = 0; column < 5; column++) {
                        boolean match = true;
                        String first = generatedReels[column]; 
                        for (int row = 1; row < 5; row++) {
                            if (!generatedReels[column + row * 5].equals(first)) { 
                                match = false;
                                break;
                            }
                        }
                        winningColumns[column] = match;
                    }

                    reelsPanel.setWinningRows(winningRows, winningColumns);

                    for (int i = 0; i < reels.length; i++) {
                        reels[i].setText(generatedReels[i]);
                    }

                    if (casinoLogic.getAmountMoney() <= 0) {
                        spinButton.setEnabled(false);
                    }
                } catch (NumberFormatException ex) {
                    moneyWindow.setText("<html>Enter a valid number!</html>");
                }
            }
        });

        lowerPanel.add(moneyWindow, BorderLayout.WEST);
        lowerPanel.add(spinButton, BorderLayout.CENTER);
        lowerPanel.add(betSizeWindow, BorderLayout.EAST);

        frame.add(lowerPanel, BorderLayout.SOUTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}