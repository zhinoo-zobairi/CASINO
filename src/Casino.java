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


        JPanel centerPanel = new JPanel(new BorderLayout());
        JLabel slotMachineLabel = new JLabel("Slot Machine Area");
        centerPanel.add(slotMachineLabel);
        frame.add(centerPanel, BorderLayout.CENTER);


        frame.setVisible(true);
    }
}