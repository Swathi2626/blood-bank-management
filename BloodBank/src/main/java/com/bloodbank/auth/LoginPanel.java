package com.bloodbank.auth;

import com.bloodbank.dashboard.DashboardTheme;
import com.bloodbank.dashboard.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoginPanel extends JPanel {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JLabel passwordFeedbackLabel;
    private final JButton loginButton;

    private final AuthenticationService authenticationService;
    private final MainFrame mainFrame;

    public LoginPanel(AuthenticationService authenticationService, MainFrame mainFrame) {
        this.authenticationService = authenticationService;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(24, 24));
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setBackground(DashboardTheme.BACKGROUND);

        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setOpaque(true);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new DashboardTheme.RoundedBorder(new Color(232, 237, 242), 24),
                new EmptyBorder(0, 0, 0, 0)
        ));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(168, 22, 35));
        leftPanel.setBorder(new EmptyBorder(40, 32, 40, 32));
        leftPanel.setPreferredSize(new Dimension(360, 0));

        JLabel icon = new JLabel("🩸");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        icon.setForeground(Color.WHITE);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel brandTitle = new JLabel("LifeFlow");
        brandTitle.setFont(new Font("Segoe UI", Font.BOLD, 34));
        brandTitle.setForeground(Color.WHITE);
        brandTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tagLine = new JLabel("<html><center>Safe donors.<br>Faster care.<br>Stronger communities.</center></html>");
        tagLine.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tagLine.setForeground(new Color(255, 235, 238));
        tagLine.setAlignmentX(Component.CENTER_ALIGNMENT);
        tagLine.setHorizontalAlignment(SwingConstants.CENTER);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(icon);
        leftPanel.add(Box.createVerticalStrut(18));
        leftPanel.add(brandTitle);
        leftPanel.add(Box.createVerticalStrut(18));
        leftPanel.add(tagLine);
        leftPanel.add(Box.createVerticalGlue());

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new EmptyBorder(32, 32, 32, 32));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Welcome back");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(DashboardTheme.TEXT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Sign in to manage blood inventory and requests");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(DashboardTheme.MUTED);
        gbc.gridy = 1;
        rightPanel.add(subtitleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(DashboardTheme.LABEL_FONT);
        usernameLabel.setForeground(DashboardTheme.MUTED);
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        rightPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(18);
        usernameField.setPreferredSize(new Dimension(0, 42));
        gbc.gridx = 1;
        rightPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(DashboardTheme.LABEL_FONT);
        passwordLabel.setForeground(DashboardTheme.MUTED);
        gbc.gridx = 0;
        gbc.gridy = 3;
        rightPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(18);
        passwordField.setPreferredSize(new Dimension(0, 42));
        gbc.gridx = 1;
        rightPanel.add(passwordField, gbc);

        passwordFeedbackLabel = new JLabel();
        passwordFeedbackLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        rightPanel.add(passwordFeedbackLabel, gbc);

        loginButton = DashboardTheme.createActionButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 44));
        loginButton.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(loginButton, gbc);

        card.add(leftPanel, BorderLayout.WEST);
        card.add(rightPanel, BorderLayout.CENTER);
        add(card, BorderLayout.CENTER);

        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePasswordFeedback();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePasswordFeedback();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePasswordFeedback();
            }
        });
        updatePasswordFeedback();
        loginButton.addActionListener(e -> login());
    }

    private void updatePasswordFeedback() {
        char[] password = passwordField.getPassword();
        try {
            List<String> issues = getPasswordIssues(password);
            if (issues.isEmpty()) {
                passwordFeedbackLabel.setText("Password meets the requirements.");
                passwordFeedbackLabel.setForeground(new Color(35, 128, 72));
                loginButton.setEnabled(true);
            } else {
                passwordFeedbackLabel.setText("<html>" + String.join("<br>", issues) + "</html>");
                passwordFeedbackLabel.setForeground(new Color(168, 22, 35));
                loginButton.setEnabled(false);
            }
        } finally {
            java.util.Arrays.fill(password, '\0');
        }
    }

    private List<String> getPasswordIssues(char[] password) {
        List<String> issues = new ArrayList<>();
        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        if (password.length < 8) {
            issues.add("Password is too short (minimum 8 characters).");
        }

        for (int index = 0; index < password.length;) {
            int codePoint = Character.codePointAt(password, index, password.length);
            if (Character.isLetter(codePoint)) {
                hasLetter = true;
            } else if (Character.isDigit(codePoint)) {
                hasDigit = true;
            } else if (isSymbol(codePoint)) {
                hasSymbol = true;
            }
            index += Character.charCount(codePoint);
        }

        if (!hasLetter) {
            issues.add("Include at least one letter.");
        }
        if (!hasDigit) {
            issues.add("Include at least one number.");
        }
        if (!hasSymbol) {
            issues.add("Include at least one symbol (for example, !, @, or #).");
        }

        return issues;
    }

    private boolean isSymbol(int codePoint) {
        return switch (Character.getType(codePoint)) {
            case Character.CONNECTOR_PUNCTUATION,
                    Character.DASH_PUNCTUATION,
                    Character.START_PUNCTUATION,
                    Character.END_PUNCTUATION,
                    Character.OTHER_PUNCTUATION,
                    Character.INITIAL_QUOTE_PUNCTUATION,
                    Character.FINAL_QUOTE_PUNCTUATION,
                    Character.MATH_SYMBOL,
                    Character.CURRENCY_SYMBOL,
                    Character.MODIFIER_SYMBOL,
                    Character.OTHER_SYMBOL -> true;
            default -> false;
        };
    }

    private void login() {
        updatePasswordFeedback();
        if (!loginButton.isEnabled()) {
            return;
        }

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = authenticationService.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful!\nWelcome " + user.getUsername());
            mainFrame.showDashboard(user);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}