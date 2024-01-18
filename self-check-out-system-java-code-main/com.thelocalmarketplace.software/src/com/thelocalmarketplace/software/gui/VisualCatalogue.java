package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class VisualCatalogue extends JPanel {

    private Map<String, Integer> itemsQuantity;

    public VisualCatalogue() {
        super(new BorderLayout()); // Use BorderLayout for the main panel
        setBackground(new Color(0x7293A0));
        itemsQuantity = new HashMap<>();

        // Title label
        JLabel titleLabel = new JLabel("Self Checkout Station", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x7293A0));
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel, BorderLayout.NORTH); // Add to NORTH region

        // Items panel in the center
        JPanel itemsPanel = new JPanel(new GridLayout(0, 4, 5, 5)); // GridLayout for item panels
        itemsPanel.setBackground(new Color(0x7293A0));
        String[] items = {"Milk", "Bread", "Cookie", "Candy"};
        for (String item : items) {
            JPanel itemPanel = createItemPanel(item);
            itemsPanel.add(itemPanel);
        }
        this.add(itemsPanel, BorderLayout.CENTER); // Add to CENTER region

        // Add item button at the bottom
        JButton addItemButton = new JButton("Add item");
        addItemButton.addActionListener(this::addItemAction);
        this.add(addItemButton, BorderLayout.SOUTH); // Add to SOUTH region
    }

    private JPanel createItemPanel(String itemName) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(itemName));

        JLabel quantityLabel = new JLabel("0", SwingConstants.CENTER);
        quantityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        itemsQuantity.put(itemName, 0);

        JButton minusButton = new JButton("-");
        minusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        minusButton.addActionListener(e -> updateQuantity(itemName, -1, quantityLabel));

        JButton plusButton = new JButton("+");
        plusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        plusButton.addActionListener(e -> updateQuantity(itemName, 1, quantityLabel));

        panel.add(Box.createVerticalGlue());
        panel.add(quantityLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(minusButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(plusButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private void updateQuantity(String itemName, int delta, JLabel quantityLabel) {
        int currentQuantity = itemsQuantity.get(itemName);
        int newQuantity = Math.max(0, currentQuantity + delta); // Prevent negative quantities
        itemsQuantity.put(itemName, newQuantity);
        quantityLabel.setText(String.valueOf(newQuantity));
    }

    private void addItemAction(ActionEvent e) {
        SwingUtilities.getWindowAncestor(this).dispose();
    
    // Open the AddWindow
        AddWindow.open();
    }

    // Method to open the visual catalog window
    public static void open() {
        JFrame frame = new JFrame("Visual Catalogue");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        VisualCatalogue visualCatalogue = new VisualCatalogue();
        frame.add(visualCatalogue);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}



