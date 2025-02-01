import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

public class Profile extends JFrame {
    private JTextArea textArea;
    private JTextArea compareTextArea;
    private JScrollPane scrollPane;
    private JScrollPane compareScrollPane;
    private boolean isCompareVisible = false;

    public Profile() {
        // Frame settings
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(600, 400);
        this.setLayout(new GridBagLayout());

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Label at the top center
        JLabel titleLabel = new JLabel("Game Statistics", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 0.1;
        this.add(titleLabel, gbc);

        // Uneditable text area with multiple rows
        textArea = new JTextArea(10, 20);
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);

        gbc.gridy = 1;
        gbc.gridx = 1; // Center the text area
        gbc.gridwidth = 1;
        gbc.weighty = 0.8;
        this.add(scrollPane, gbc);

        // Compare text area (initially hidden)
        compareTextArea = new JTextArea(10, 20);
        compareTextArea.setEditable(false);
        compareScrollPane = new JScrollPane(compareTextArea);

        // Panel for buttons to keep their size constant
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton backButton = new JButton("Back");
        JButton compareButton = new JButton("Compare");

        // Set preferred size for buttons to make them bigger
        Dimension buttonSize = new Dimension(150, 40);
        backButton.setPreferredSize(buttonSize);
        compareButton.setPreferredSize(buttonSize);

        buttonPanel.add(backButton);
        buttonPanel.add(compareButton);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(buttonPanel, gbc);

        // Action listener for Compare button
        compareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isCompareVisible) {
                    remove(scrollPane);

                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.gridwidth = 1;
                    gbc.weighty = 0.8;
                    gbc.fill = GridBagConstraints.BOTH;
                    add(scrollPane, gbc);

                    gbc.gridx = 1;
                    add(compareScrollPane, gbc);
                } else {
                    remove(compareScrollPane);
                    remove(scrollPane);

                    gbc.gridx = 1;
                    add(scrollPane, gbc);
                }
                isCompareVisible = !isCompareVisible;

                // Revalidate and repaint to update the layout
                revalidate();
                repaint();
            }
        });

        // Make the window visible
        this.setVisible(true);
    }

    public static void main(String[] args) {
        
        new Profile();
    }
}
