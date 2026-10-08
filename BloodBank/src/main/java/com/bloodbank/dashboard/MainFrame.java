package com.bloodbank.dashboard;

import com.bloodbank.auth.AuthenticationService;
import com.bloodbank.auth.LoginPanel;
import com.bloodbank.auth.MySQLUserRepository;
import com.bloodbank.auth.User;
import com.bloodbank.common.Role;
import org.yourcompany.yourproject.hospital.AdminRequestPanel;
import org.yourcompany.yourproject.hospital.HospitalRequestPanel;
import org.yourcompany.yourproject.inventory.InventoryPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final LoginPanel loginPanel;
    private String currentDashboardKey;

    public MainFrame() {
        DashboardTheme.apply();

        setTitle("Blood Bank Management System");
        setSize(1200, 760);
        setMinimumSize(new Dimension(980, 620));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(DashboardTheme.BACKGROUND);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(DashboardTheme.BACKGROUND);

        MySQLUserRepository userRepository = new MySQLUserRepository();
        AuthenticationService authenticationService = new AuthenticationService(userRepository);

        loginPanel = new LoginPanel(authenticationService, this);
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(new InventoryPanel(this), "INVENTORY");
        mainPanel.add(new PlaceholderPanel("Donations — Coming Soon"), "DONATIONS");
        mainPanel.add(new HospitalRequestPanel(this), "REQUESTS");
        mainPanel.add(new AdminRequestPanel(this), "ADMIN_REQUESTS");

        add(mainPanel);

        cardLayout.show(mainPanel, "LOGIN");
        setVisible(true);
    }

    public void showDashboard(User user) {
        if (user.getRole() == Role.ADMIN) {
            currentDashboardKey = "ADMIN";
            AdminDashboard dashboard = new AdminDashboard(this);
            mainPanel.add(dashboard, "ADMIN");
            cardLayout.show(mainPanel, "ADMIN");
        } else if (user.getRole() == Role.HOSPITAL) {
            currentDashboardKey = "HOSPITAL";
            HospitalDashboard dashboard = new HospitalDashboard(this);
            mainPanel.add(dashboard, "HOSPITAL");
            cardLayout.show(mainPanel, "HOSPITAL");
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void goBackToDashboard() {
        if (currentDashboardKey != null) {
            cardLayout.show(mainPanel, currentDashboardKey);
        } else {
            showLogin();
        }
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }

    public void showLogin() {
        currentDashboardKey = null;
        loginPanel.clearFields();
        cardLayout.show(mainPanel, "LOGIN");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}