import javax.swing.*;
import java.awt.*;

public class Casino {
    public static void main(String[] args) {
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

        JButton spinButton = new JButton("Spin");
        spinButton.setFont(new Font("Arial", Font.BOLD, 20));

        centerPanel.add(reelsPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(spinButton);

        frame.add(centerPanel, BorderLayout.CENTER);


        frame.setVisible(true);
    }
}