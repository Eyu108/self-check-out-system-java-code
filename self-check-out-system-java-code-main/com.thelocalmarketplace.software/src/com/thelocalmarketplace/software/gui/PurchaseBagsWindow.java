package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;

public class PurchaseBagsWindow {

    private static int bagCount = 0; // To keep track of the number of bags
    private static JTextField bagCountField; // To display the number of bags

    public static void open() {
        JFrame frame = new JFrame("Purchase Bags");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(0x7293A0));

        GridBagConstraints gbc = new GridBagConstraints();

        // Heading label
        JLabel headingLabel = new JLabel("Self Checkout Station");
        headingLabel.setFont(new Font("Serif", Font.BOLD, 33));
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(headingLabel, gbc);

        // Button panel for minus, bag count, and plus
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        // Minus button
        JButton minusButton = new JButton("-");
        minusButton.setFont(new Font("Serif", Font.BOLD, 20));

        // Bag count display
        bagCountField = new JTextField("0", 3);
        bagCountField.setHorizontalAlignment(JTextField.CENTER);
        bagCountField.setFont(new Font("Serif", Font.PLAIN, 28));
        bagCountField.setEditable(false);

        // Plus button
        JButton plusButton = new JButton("+");
        plusButton.setFont(new Font("Serif", Font.BOLD, 20));

        // Add components to the button panel
        buttonPanel.add(minusButton);
        buttonPanel.add(bagCountField);
        buttonPanel.add(plusButton);

        // Add button panel to the frame
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(buttonPanel, gbc);

        // Action listeners for minus and plus buttons
        minusButton.addActionListener(e -> {
            if (bagCount > 0) {
                bagCount--;
                bagCountField.setText(String.valueOf(bagCount));
            }
        });
        plusButton.addActionListener(e -> {
            bagCount++;
            bagCountField.setText(String.valueOf(bagCount));
        });

        // Add Bags button
        JButton addBagsButton = new JButton("Add Bags");
        addBagsButton.setFont(new Font("Serif", Font.BOLD, 20));
        // ... Add Bags button logic here

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose(); // Close the PLU window
            AddWindow.open(); // Open the AddWindow again
        });

        // Buttons panel for Add Bags and Back
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(addBagsButton);
        buttonsPanel.add(backButton);

        // Add buttons panel to the frame
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(buttonsPanel, gbc);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}



