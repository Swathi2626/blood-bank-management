package com.bloodbank.dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HospitalDashboard extends JPanel {

    private final MainFrame mainFrame;

    public HospitalDashboard(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(24, 24, 24, 24));
        setBackground(DashboardTheme.BACKGROUND);

        JLabel title = DashboardTheme.createSectionTitle("Hospital Dashboard");
        add(title, BorderLayout.NORTH);

        JPanel summaryPanel = new JPanel(new GridLayout(1, 2, 18, 18));
        summaryPanel.setOpaque(false);
        summaryPanel.add(DashboardTheme.createStatCard("Available Blood Units", "0", new Color(173, 35, 57)));
        summaryPanel.add(DashboardTheme.createStatCard("My Requests", "0", new Color(36, 110, 154)));
        add(summaryPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonPanel.setOpaque(false);

        JButton inventoryButton = DashboardTheme.createSecondaryButton("View Inventory");
        JButton requestButton = DashboardTheme.createActionButton("Request Blood");
        JButton logoutButton = DashboardTheme.createSecondaryButton("Logout");

        buttonPanel.add(inventoryButton);
        buttonPanel.add(requestButton);
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        inventoryButton.addActionListener(e -> mainFrame.showScreen("INVENTORY"));
        requestButton.addActionListener(e -> mainFrame.showScreen("REQUESTS"));
        logoutButton.addActionListener(e -> mainFrame.showLogin());
    }
}