import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.*;

class ReelPanel extends JPanel {
    private JLabel[] reels;
    private boolean[] winningRows = new boolean[5];
    private boolean[] winningColumns = new boolean[5];
    
    public ReelPanel(JLabel[] reels) {
        this.reels = reels;
        setLayout(new GridLayout(5, 5, 10, 10)); // Ensuring consistent spacing
    }
    public static void main(String[] args) {
        Casino casino = new Casino();
    }
    public void setWinningRows(boolean[] winningRows,boolean[] winningColumns) {
        this.winningRows = winningRows;
        this.winningColumns = winningColumns;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(10));
        Graphics2D g3 = (Graphics2D) g;
        g3.setColor(Color.RED);
        g3.setStroke(new BasicStroke(10));
        for (int row = 0; row < 5; row++) {
            if (winningRows[row]) {
                int y = (row * getHeight() / 5) + (getHeight() / 10); // Ensure consistent positioning
                g2.drawLine(10, y, getWidth() - 10, y);
            }
        }
        
        for (int column = 0; column < 5; column++) {
            if (winningColumns[column]) {
                int x = (column * getWidth() / 5) + (getWidth() / 10);
                g2.drawLine(x, 10, x, getHeight() - 10);
            }
        }
        g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(3));
        int cellWidth = getWidth() / 5;
        int cellHeight = getHeight() / 5;
        
        for (int i = 0; i < 6; i++) {
            int x = i * cellWidth;
            g2.drawLine(x, 0, x, getHeight()); // Vertical lines
        }
        
        for (int i = 0; i < 6; i++) {
            int y = i * cellHeight;
            g2.drawLine(0, y, getWidth(), y); // Horizontal lines
        }
    }
}


class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
        setLayout(new BorderLayout()); // Ensure components can be added
    }

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.RED);  // Temporary debug color
    g.fillRect(0, 0, getWidth(), getHeight()); // Fill with red to check visibility
    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
}
}

public class Casino extends JFrame {
    private int betSize;
   
    public Casino () {
        final CasinoLogic casinoLogic = new CasinoLogic(10000);
        betSize = 100;
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        BackgroundPanel background = new BackgroundPanel("./images/background.jpg");

        // Add all components to the background panel
        background.setLayout(new BorderLayout());  // Important for proper layout management

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        JButton menuButton = new JButton("☰");
        leftPanel.add(menuButton, BorderLayout.NORTH);
        background.add(leftPanel, BorderLayout.WEST);

        JPanel menuPanel = new JPanel();
        JButton logoutButton = new JButton("Logout");
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.add(logoutButton);
        menuPanel.add(new JButton("Profile"));
        menuPanel.setVisible(false); 
        leftPanel.add(menuPanel, BorderLayout.CENTER);

        menuButton.addActionListener(e -> {
            menuPanel.setVisible(!menuPanel.isVisible()); 
            this.revalidate();
            this.repaint();
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CasinoMain.showMainFrame();
            }
        });

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel[] reels = new JLabel[25];
        String[] symbols = {"🍒", "🍋", "🍉", "🍊", "🍇", "🍓", "🥝", "🍍", "🍌", "🍏"};
        ReelPanel reelsPanel = new ReelPanel(reels);
        reelsPanel.setOpaque(false);
        for (int i = 0; i < reels.length; i++) {
            reels[i] = new JLabel(symbols[i % symbols.length], JLabel.CENTER);
            reels[i].setFont(new Font("Arial", Font.BOLD, 50));
            reelsPanel.add(reels[i]);
        }

        centerPanel.add(reelsPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lowerPanel.setOpaque(false);
        

        
        JButton spinButton = new JButton("Spin - Space bar");
        spinButton.setBackground(Color.DARK_GRAY); // Change to your desired color
        spinButton.setForeground(Color.YELLOW); // Change text color if needed
        spinButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));
        
        spinButton.setOpaque(false);
        spinButton.setContentAreaFilled(false);
        spinButton.setFocusPainted(false);

        spinButton.setFont(new Font("Arial", Font.BOLD, 20));
        spinButton.setPreferredSize(new Dimension(600, 80));
        
        spinButton.setUI(new BasicButtonUI() {
    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), 30, 30);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background color
        g2.setColor(b.getBackground());
        g2.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), 30, 30); // 30px corner radius

        super.paint(g, c);
        g2.dispose();
        }
    });

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

        


        JPanel rightPanel = new JPanel(new BorderLayout()); // Allows top label placement
        rightPanel.setOpaque(false);

        // Create label
        JLabel betCount = new JLabel("BET: 100", SwingConstants.CENTER);
        betCount.setFont(new Font("Arial", Font.BOLD, 16));
        betCount.setForeground(Color.YELLOW);
        betCount.setOpaque(true);
        betCount.setBackground(Color.DARK_GRAY); // Set background color
        betCount.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding

        JLabel moneyCount = new JLabel("Money: " + casinoLogic.getAmountMoney(), SwingConstants.CENTER);
        moneyCount.setFont(new Font("Arial", Font.BOLD, 16));
        moneyCount.setForeground(Color.YELLOW);
        moneyCount.setOpaque(true);
        moneyCount.setBackground(Color.DARK_GRAY); // Set background color
        moneyCount.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding

        // Panel for buttons to avoid stretching
        JPanel buttonContainer = new JPanel(new GridLayout(5, 1, 5, 5));
        buttonContainer.setOpaque(false);

        // Buttons
        JButton addBetTausend = new JButton("+1000");
        JButton addBetHundred = new JButton("+100");
        JButton setZero = new JButton("Set 0");
        JButton substractBetHundred = new JButton("-100");
        JButton substractBetTausend = new JButton("-1000");

        // Set fixed button size
        Dimension buttonSize = new Dimension(150, 50);
        addBetTausend.setPreferredSize(buttonSize);
        addBetHundred.setPreferredSize(buttonSize);
        setZero.setPreferredSize(buttonSize);
        substractBetHundred.setPreferredSize(buttonSize);
        substractBetTausend.setPreferredSize(buttonSize);

        // Add buttons to container
        buttonContainer.add(addBetTausend);
        buttonContainer.add(addBetHundred);
        buttonContainer.add(setZero);
        buttonContainer.add(substractBetHundred);
        buttonContainer.add(substractBetTausend);

        // Add components to rightPanel
        rightPanel.add(betCount, BorderLayout.NORTH); // Label on top
        rightPanel.add(buttonContainer, BorderLayout.CENTER); // Buttons below
        rightPanel.add(moneyCount,BorderLayout.SOUTH);

        // Wrap rightPanel in a bottom-right aligned panel
        JPanel bottomRightPanel = new JPanel(new BorderLayout());
        bottomRightPanel.setOpaque(false);
        bottomRightPanel.add(rightPanel, BorderLayout.SOUTH);

        // Add to background
        background.add(bottomRightPanel, BorderLayout.EAST);

        // Lower Panel (if needed)
        lowerPanel.add(spinButton, BorderLayout.CENTER);
        background.add(lowerPanel, BorderLayout.SOUTH);
        background.add(centerPanel, BorderLayout.CENTER);

        setContentPane(background);
        this.setVisible(true);




        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] generatedReels = casinoLogic.generateReels(25);
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
                            if (!generatedReels[row * 5 + column].equals(first)) {
                                match = false;
                                break;
                            }
                        }
                        winningColumns[column] = match;
                    }
                    reelsPanel.setWinningRows(winningRows,winningColumns);
                    
                    for (int i = 0; i < reels.length; i++) {
                        reels[i].setText(generatedReels[i]);
                        reels[i].setFont(new Font("Arial", Font.BOLD, 50));
                        reels[i].setOpaque(false);
                    }
                    if (casinoLogic.getAmountMoney() <= 0) {
                        spinButton.setEnabled(false);
                    }
                    casinoLogic.spin(betSize, winningRows,winningColumns);
                    moneyCount.setText("Money: " + casinoLogic.getAmountMoney());
                    
                } catch (NumberFormatException ex) {
                }
            }
        });


        addBetTausend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betSize += 1000;
                betCount.setText("BET: " + betSize);
            }
        });
        addBetHundred.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betSize += 100;
                betCount.setText("BET: " + betSize);
            }
        });
        setZero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betSize = 0;
                betCount.setText("BET: " + betSize);
            }
        });
        substractBetHundred.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(betSize != 0)
                betSize -= 100;
                betCount.setText("BET: " + betSize);
            }
        });
        substractBetTausend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(betSize-1000 > 0){
                    betSize -= 1000;
                    betCount.setText("BET: " + betSize);
                }
                else {
                    betSize = 0;
                    betCount.setText("BET: " + betSize);
                }
            }
        });
    }
}


