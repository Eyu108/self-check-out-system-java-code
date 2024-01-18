package com.thelocalmarketplace.software.gui;
import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        // Create and set up the main window
        JFrame frame = new JFrame("Self Checkout Station");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        // Set background color
        frame.getContentPane().setBackground(new Color(0x7293A0));

        GridBagConstraints gbc = new GridBagConstraints();

        // Heading label with increased font size and bold style
        JLabel headingLabel = new JLabel("Self Checkout Station");
        headingLabel.setFont(new Font("Serif", Font.BOLD, 33)); // Setting font size to 33 and style to bold
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0); // Top, left, bottom, right padding
        frame.add(headingLabel, gbc);

        // Start session button
        JButton startButton = new JButton("Start Session");
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.setMaximumSize(new Dimension(150, 50));
        startButton.addActionListener(e -> {
            frame.dispose(); // Close the main window
            AddWindow.open(); // Open the checkout window
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(startButton, gbc);

        // Display the window
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }
}
