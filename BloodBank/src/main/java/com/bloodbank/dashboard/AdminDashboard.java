package com.bloodbank.dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminDashboard extends JPanel {

    private final MainFrame mainFrame;

    public AdminDashboard(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(24, 24, 24, 24));
        setBackground(DashboardTheme.BACKGROUND);

        JLabel title = DashboardTheme.createSectionTitle("Admin Dashboard");
        add(title, BorderLayout.NORTH);

        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 18, 18));
        summaryPanel.setOpaque(false);
        summaryPanel.add(DashboardTheme.createStatCard("Blood Units", "0", new Color(182, 29, 48)));
        summaryPanel.add(DashboardTheme.createStatCard("Pending Requests", "0", new Color(42, 130, 95)));
        summaryPanel.add(DashboardTheme.createStatCard("Donations", "0", new Color(98, 93, 178)));
        add(summaryPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonPanel.setOpaque(false);

        JButton inventoryButton = DashboardTheme.createSecondaryButton("Inventory");
        JButton donationButton = DashboardTheme.createSecondaryButton("Donations");
        JButton requestButton = DashboardTheme.createActionButton("Requests");
        JButton logoutButton = DashboardTheme.createSecondaryButton("Logout");

        buttonPanel.add(inventoryButton);
        buttonPanel.add(donationButton);
        buttonPanel.add(requestButton);
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        inventoryButton.addActionListener(e -> mainFrame.showScreen("INVENTORY"));
        donationButton.addActionListener(e -> mainFrame.showScreen("DONATIONS"));
        requestButton.addActionListener(e -> mainFrame.showScreen("ADMIN_REQUESTS"));
        logoutButton.addActionListener(e -> mainFrame.showLogin());
    }
}