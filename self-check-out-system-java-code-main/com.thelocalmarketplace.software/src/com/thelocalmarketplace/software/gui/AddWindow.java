
package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;

public class AddWindow extends JPanel {

    public static void open() {
        JFrame frame = new JFrame("Self Checkout Station");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(0x7293A0));

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel headingLabel = new JLabel("Self Checkout Station");
        headingLabel.setFont(new Font("Serif", Font.BOLD, 33));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(headingLabel, gbc);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        String[] buttons = {"Add Own Bags", "Purchase Bags", "Scan Items", "PLU Code", "Visual Catalogue", "Remove Item", "Membership", "Pay"};
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(e -> {
                if ("PLU Code".equals(text)) {
                    frame.dispose(); // Close the AddWindow
                    PluWindow.open(); // Open the PLU window
                } else if ("Purchase Bags".equals(text)) {
                    frame.dispose(); // Close the AddWindow
                    PurchaseBagsWindow.open(); // Open the PurchaseBagsWindow
                } else if ("Visual Catalogue".equals(text)) {
                    frame.dispose(); // Close the AddWindow
                    VisualCatalogue.open();
                }
                // You can add more else if blocks here for other buttons as needed
            });
            buttonsPanel.add(button);
        }

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(buttonsPanel, gbc);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}



