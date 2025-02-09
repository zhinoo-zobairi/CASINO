import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.*;

class ReelPanel extends JPanel {
    private JLabel[] reels;
    private boolean[] winningRows = new boolean[5];
    private boolean[] winningColumns = new boolean[5];
    private boolean smileyActive = false;
    
    // Eine Referenz zur Spiel-Logik, welche den aktuellen Modus bereitstellt.
    // casinoLogic wird hier erstellt, weil in diesem Projekt gilt Casino Klasse als ein Controller (+ UI fur die Speil window)
    // 
    private CasinoLogic casinologic;
    
    /**
     * Konstruktor, der sowohl die Walzen als auch die Spiel-Logik entgegennimmt.
     * Erzeugt ein ReelPanel mit gegebener Logik und Label-Array.
     */
    public ReelPanel(JLabel[] reels, CasinoLogic logic) {
        this.reels = reels;
        this.casinologic = logic;
        setLayout(new GridLayout(5, 5, 10, 10)); // Sorgt für gleichmäßige Abstände
    }

    /**
     * Aktualisiert die Reihen und Spalten, die im Modus 1 gewonnen haben.
     */
    public void setWinningRows(boolean[] winningRows, boolean[] winningColumns) {
        this.winningRows = winningRows;
        this.winningColumns = winningColumns;
        repaint();
    }

    /**
     * Aktiviert das Smiley-Gesicht und löst das Neuzeichnen aus.
     */
    public void successfulSmiley(){
        this.smileyActive = true;
        repaint();
    }
    
    /**
     * Zeichnet das Panel entsprechend des aktuellen Modus der Spiel-Logik:
     * - Modus 1 und 3: Gewinnerlinien
     * - Modus 2: Smiley-Gesicht
     * Außerdem wird optional ein Gitter gerendert (gelbe Linien).
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Ermittelt, welcher Zeichen-Modus verwendet werden soll.
        if (casinologic.getCurrentMode() == 1 || casinologic.getCurrentMode() == 3 ) {
            // --- Modus 1/3: Zeichnet die Gewinnlinien ---
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(10));
            
            // Zeichnet horizontale Gewinnlinien für jede gewinnende Reihe.
            for (int row = 0; row < 5; row++) {
                if (winningRows[row]) {
                    int y = (row * getHeight() / 5) + (getHeight() / 10);
                    g2.drawLine(10, y, getWidth() - 10, y);
                }
            }
            
            // Zeichnet vertikale Gewinnlinien für jede gewinnende Spalte.
            for (int column = 0; column < 5; column++) {
                if (winningColumns[column]) {
                    int x = (column * getWidth() / 5) + (getWidth() / 10);
                    g2.drawLine(x, 10, x, getHeight() - 10);
                }
            }
        } else if (casinologic.getCurrentMode() == 2 && this.smileyActive) {
            // --- Modus 2: Zeichnet das Smiley-Gesicht (rote Linien) ---
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(10));
            
            int cellWidth = getWidth() / 5;
            int cellHeight = getHeight() / 5;
            
            // ----- Zeichnet den Mund als Polyline -----
            int A_x = 0 * cellWidth + cellWidth / 2;
            int A_y = 3 * cellHeight + cellHeight / 2;
            int B_x = 1 * cellWidth + cellWidth / 2;
            int B_y = 4 * cellHeight + cellHeight / 2;
            int C_x = 2 * cellWidth + cellWidth / 2;
            int C_y = 4 * cellHeight + cellHeight / 2;
            int D_x = 3 * cellWidth + cellWidth / 2;
            int D_y = 4 * cellHeight + cellHeight / 2;
            int E_x = 4 * cellWidth + cellWidth / 2;
            int E_y = 3 * cellHeight + cellHeight / 2;
            
            int[] mouthXPoints = { A_x, B_x, C_x, D_x, E_x };
            int[] mouthYPoints = { A_y, B_y, C_y, D_y, E_y };
            g2.drawPolyline(mouthXPoints, mouthYPoints, 5);
            
            // ----- Zeichnet die Augen als vertikale Linien -----
            // Linkes Auge in Spalte 1 (erste bis zweite Zeile).
            int leftEyeX = 1 * cellWidth + cellWidth / 2;
            int leftEyeY1 = 0 * cellHeight + cellHeight / 2;
            int leftEyeY2 = 1 * cellHeight + cellHeight / 2;
            g2.drawLine(leftEyeX, leftEyeY1, leftEyeX, leftEyeY2);
            
            // Rechtes Auge in Spalte 3 (erste bis zweite Zeile).
            int rightEyeX = 3 * cellWidth + cellWidth / 2;
            int rightEyeY1 = 0 * cellHeight + cellHeight / 2;
            int rightEyeY2 = 1 * cellHeight + cellHeight / 2;
            g2.drawLine(rightEyeX, rightEyeY1, rightEyeX, rightEyeY2);
        }
        
        // Zeichnet ein Gitter-Overlay (zur visuellen Orientierung).
        g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(3));
        int cellWidth = getWidth() / 5;
        int cellHeight = getHeight() / 5;
        // Vertikale Gitterlinien
        for (int i = 0; i <= 5; i++) {
            int x = i * cellWidth;
            g2.drawLine(x, 0, x, getHeight());
        }
        // Horizontale Gitterlinien
        for (int i = 0; i <= 5; i++) {
            int y = i * cellHeight;
            g2.drawLine(0, y, getWidth(), y);
        }
        this.smileyActive = false;
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    /**
     * Konstruktor, der ein Hintergrundbild lädt und im Panel platziert.
     */
    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
        setLayout(new BorderLayout()); // Sorgt dafür, dass Komponenten hinzugefügt werden können
    }

    /**
     * Zeichnet das Hintergrundbild im Panel, inkl. einer roten Debug-Füllung.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);  // Temporäre Debug-Farbe
        g.fillRect(0, 0, getWidth(), getHeight()); // Zum Testen der Sichtbarkeit
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

public class Casino extends JFrame {
    private int betSize;
   
    /**
     * Konstruktor der Hauptklasse Casino, der das Fenster aufbaut, Logik initiiert
     * und die Benutzeroberfläche (GUI) zusammenstellt.
     */
    public Casino (String username) {
        final CasinoLogic casinoLogic = new CasinoLogic(username,1);
        betSize = 100;
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        BackgroundPanel background = new BackgroundPanel("./images/background.jpg");

        // Fügt alle Komponenten dem BackgroundPanel hinzu
        background.setLayout(new BorderLayout());  // Wichtig für ordnungsgemäße Layout-Verwaltung

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        JButton menuButton = new JButton("☰");
        leftPanel.add(menuButton, BorderLayout.NORTH);
        background.add(leftPanel, BorderLayout.WEST);

        JPanel menuPanel = new JPanel();
        JButton modeButton = new JButton("Mode");
        JButton logoutButton = new JButton("Logout");
        JButton instructionsButton = new JButton("Instructions");
        JButton creditsButton = new JButton("Credits");
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        JButton profilButton = new JButton("Profile");
        menuPanel.add(modeButton);
        menuPanel.add(profilButton);
        menuPanel.add(instructionsButton);
        menuPanel.add(creditsButton);
        menuPanel.add(logoutButton);
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

        instructionsButton.addActionListener(e -> {
            CasinoMain.showInstructions(username);
        });

        creditsButton.addActionListener(e -> {
            try {
                // Öffnet die angegebene URL im Browser.
                URI uri = new URI("https://github.com/zhinoo-zobairi/CASINO/tree/main");
        
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
        
                    if (desktop.isSupported(Desktop.Action.BROWSE)) {
                        desktop.browse(uri); 
                    } else {
                        System.out.println("BROWSE action not supported.");
                    }
                } else {
                    System.out.println("Desktop API is not supported on this platform."); // Fehlermeldung furs Testen
                }
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        profilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CasinoMain.showProfil(username);
            }
        });

        modeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                casinoLogic.changeMode();
            }
        });

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel[] reels = new JLabel[25];
        String[] symbols = {"🍒", "🍋", "🍉", "🍊", "🍇", "🍓", "🥝", "🍍", "🍌", "🍏"};
        ReelPanel reelsPanel = new ReelPanel(reels,casinoLogic);
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
        
        // Spin-Button, um Walzen zu drehen
        JButton spinButton = new JButton("Spin - Space bar");
        spinButton.setBackground(Color.DARK_GRAY);
        spinButton.setForeground(Color.YELLOW);
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

                g2.setColor(b.getBackground());
                g2.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), 30, 30);

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

        JPanel rightPanel = new JPanel(new BorderLayout()); 
        rightPanel.setOpaque(false);

        JLabel betCount = new JLabel("BET: 100", SwingConstants.CENTER);
        betCount.setFont(new Font("Arial", Font.BOLD, 16));
        betCount.setForeground(Color.YELLOW);
        betCount.setOpaque(true);
        betCount.setBackground(Color.DARK_GRAY);
        betCount.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel moneyCount = new JLabel("Money: " + casinoLogic.getAmountMoney(), SwingConstants.CENTER);
        moneyCount.setFont(new Font("Arial", Font.BOLD, 16));
        moneyCount.setForeground(Color.YELLOW);
        moneyCount.setOpaque(true);
        moneyCount.setBackground(Color.DARK_GRAY);
        moneyCount.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel buttonContainer = new JPanel(new GridLayout(5, 1, 5, 5));
        buttonContainer.setOpaque(false);

        // Buttons, um den Einsatz zu ändern
        JButton addBetTausend = new JButton("+1000");
        JButton addBetHundred = new JButton("+100");
        JButton setZero = new JButton("Set 0");
        JButton substractBetHundred = new JButton("-100");
        JButton substractBetTausend = new JButton("-1000");

        Dimension buttonSize = new Dimension(150, 50);
        addBetTausend.setPreferredSize(buttonSize);
        addBetHundred.setPreferredSize(buttonSize);
        setZero.setPreferredSize(buttonSize);
        substractBetHundred.setPreferredSize(buttonSize);
        substractBetTausend.setPreferredSize(buttonSize);

        buttonContainer.add(addBetTausend);
        buttonContainer.add(addBetHundred);
        buttonContainer.add(setZero);
        buttonContainer.add(substractBetHundred);
        buttonContainer.add(substractBetTausend);

        rightPanel.add(betCount, BorderLayout.NORTH);
        rightPanel.add(buttonContainer, BorderLayout.CENTER);
        rightPanel.add(moneyCount,BorderLayout.SOUTH);

        JPanel bottomRightPanel = new JPanel(new BorderLayout());
        bottomRightPanel.setOpaque(false);
        bottomRightPanel.add(rightPanel, BorderLayout.SOUTH);

        background.add(bottomRightPanel, BorderLayout.EAST);

        lowerPanel.add(spinButton, BorderLayout.CENTER);
        background.add(lowerPanel, BorderLayout.SOUTH);
        background.add(centerPanel, BorderLayout.CENTER);

        setContentPane(background);
        this.setVisible(true);

        /**
         * ActionListener für den Spin-Button:
         * Ruft die Logik auf, um die Walzen neu zu generieren,
         * prüft den Modus und zeichnet ggf. Gewinnlinien oder ein Smiley.
         */
        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (betSize <= casinoLogic.getAmountMoney()) {
                        String[] generatedReels = casinoLogic.generateReels(25);
        
                        // Modus 1 oder 3: Gewinnlinien prüfen
                        if (casinoLogic.getCurrentMode() == 1 || casinoLogic.getCurrentMode() == 3) {
                            boolean[] winningRows = new boolean[5];
                            boolean[] winningColumns = new boolean[5];
        
                            // Reihen-Check fur winningRows
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
        
                            // Spalten-Check fur winningColumns
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
        
                            reelsPanel.setWinningRows(winningRows, winningColumns);
        
                            try {
                                casinoLogic.spin(betSize, winningRows, winningColumns,false);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
        
                        } 
                        // Modus 2: Prüft das Smiley-Muster
                        else if (casinoLogic.getCurrentMode() == 2) {
                            int indexA = 3 * 5 + 0;  
                            int indexB = 4 * 5 + 1;  
                            int indexC = 4 * 5 + 2;  
                            int indexD = 4 * 5 + 3;  
                            int indexE = 3 * 5 + 4;  
        
                            int leftEyeTop = 0 * 5 + 1;   
                            int leftEyeBottom = 1 * 5 + 1; 
                            int rightEyeTop = 0 * 5 + 3;   
                            int rightEyeBottom = 1 * 5 + 3; 
        
                            boolean smileMatch = generatedReels[indexA].equals(generatedReels[indexB])
                                    && generatedReels[indexB].equals(generatedReels[indexC])
                                    && generatedReels[indexC].equals(generatedReels[indexD])
                                    && generatedReels[indexD].equals(generatedReels[indexE]);
        
                            boolean leftEyeMatch = generatedReels[leftEyeTop].equals(generatedReels[leftEyeBottom]);
                            boolean rightEyeMatch = generatedReels[rightEyeTop].equals(generatedReels[rightEyeBottom]);
                            
                            boolean successfulSmiley = false;
                            if (smileMatch && leftEyeMatch && rightEyeMatch) {
                                reelsPanel.successfulSmiley();
                                successfulSmiley = true;
                            }
                            
                            try {
                                casinoLogic.spin(betSize, null, null,successfulSmiley);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
        
                        // Aktualisiert die Anzeige der Symbole in allen Walzen
                        for (int i = 0; i < reels.length; i++) {
                            reels[i].setText(generatedReels[i]);
                            reels[i].setFont(new Font("Arial", Font.BOLD, 50));
                            reels[i].setOpaque(false);
                        }
        
                        // Deaktiviert den Spin-Button, falls kein Geld mehr übrig ist.
                        if (casinoLogic.getAmountMoney() <= 0) {
                            spinButton.setEnabled(false);
                        }
                        moneyCount.setText("Money: " + casinoLogic.getAmountMoney());
                    }
                } catch (NumberFormatException ex) {
                }
            }
        });

        // Listener für +1000-Button
        addBetTausend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betSize += 1000;
                betCount.setText("BET: " + betSize);
            }
        });

        // Listener für +100-Button
        addBetHundred.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betSize += 100;
                betCount.setText("BET: " + betSize);
            }
        });

        // Setzt den Einsatz auf 0
        setZero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betSize = 0;
                betCount.setText("BET: " + betSize);
            }
        });

        // Listener für -100-Button
        substractBetHundred.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(betSize != 0)
                    betSize -= 100;
                betCount.setText("BET: " + betSize);
            }
        });

        // Listener für -1000-Button
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