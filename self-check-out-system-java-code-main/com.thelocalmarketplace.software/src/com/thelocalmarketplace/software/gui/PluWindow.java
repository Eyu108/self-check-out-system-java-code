package com.thelocalmarketplace.software.GUI;

import javax.swing.*;
import java.awt.*;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;


public class PluWindow {

    private static JTextField inputField; // Text field for displaying the input

    public static void open() {
        JFrame frame = new JFrame("Self Checkout System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(0x7293A0));

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel headingLabel = new JLabel("Enter PLU Code");
        headingLabel.setFont(new Font("Serif", Font.BOLD, 33));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(headingLabel, gbc);

        inputField = new JTextField();
        inputField.setFont(new Font("Serif", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(inputField, gbc);

        // Create a panel for the number buttons
        JPanel numberPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        // Add number buttons 0 to 9 and Del button
        for (int i = 1; i <= 9; i++) {
            addButton(numberPanel, String.valueOf(i));
        }
        addButton(numberPanel, "0");
        addButton(numberPanel, "Del");

        // Add the number panel to the frame
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        frame.add(numberPanel, gbc);

        // Add Submit and Cancel buttons
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Handle submission
            String pluCode = inputField.getText();
            try { 
            	PLUCodedProduct product = ProductDatabases.PLU_PRODUCT_DATABASE.get(pluCode);
            	
            }
            catch(Exception x) {
            	System.out.print("not correct");
            }
            
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose(); // Close the PLU window
            AddWindow.open(); // Open the AddWindow again
        });

        // Add Submit and Back buttons
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        frame.add(submitButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        frame.add(backButton, gbc);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void addButton(JPanel panel, String label) {
        JButton button = new JButton(label);
        button.addActionListener(e -> {
            if ("Del".equals(label)) {
                String currentText = inputField.getText();
                if (!currentText.isEmpty()) {
                    // Remove the last digit from the input field
                    inputField.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else {
                // Add the button's label to the input field
                inputField.setText(inputField.getText() + label);
            }
        });
        panel.add(button);
    }
}