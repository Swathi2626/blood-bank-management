package com.bloodbank.dashboard;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JPanel {

    private MainFrame mainFrame;

    public AdminDashboard(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                "Admin Dashboard",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(title, BorderLayout.NORTH);

        JPanel summaryPanel =
                new JPanel(new GridLayout(1, 3, 10, 10));

        JLabel inventoryLabel =
                new JLabel(
                        "<html><center>Blood Units<br>0</center></html>",
                        SwingConstants.CENTER
                );

        JLabel requestLabel =
                new JLabel(
                        "<html><center>Pending Requests<br>0</center></html>",
                        SwingConstants.CENTER
                );

        JLabel donationLabel =
                new JLabel(
                        "<html><center>Donations<br>0</center></html>",
                        SwingConstants.CENTER
                );

        summaryPanel.add(inventoryLabel);
        summaryPanel.add(requestLabel);
        summaryPanel.add(donationLabel);

        add(summaryPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton inventoryButton =
                new JButton("Inventory");

        JButton donationButton =
                new JButton("Donations");
        JButton requestButton =
                new JButton("Requests");

        buttonPanel.add(inventoryButton);
        buttonPanel.add(donationButton);
        buttonPanel.add(requestButton);

        add(buttonPanel, BorderLayout.SOUTH);

        inventoryButton.addActionListener(e ->
                mainFrame.showScreen("INVENTORY")
        );

        donationButton.addActionListener(e ->
                mainFrame.showScreen("DONATIONS")
        );
        requestButton.addActionListener(e ->
        mainFrame.showScreen("ADMIN_REQUESTS")
        );
    }
}