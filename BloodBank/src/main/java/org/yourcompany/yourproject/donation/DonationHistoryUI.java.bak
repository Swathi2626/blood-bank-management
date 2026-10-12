package org.yourcompany.yourproject.donation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DonationHistoryUI extends JFrame {

    private DefaultTableModel tableModel;
    private JTable donationTable;

    public DonationHistoryUI() {

        setTitle("Donation History");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Donation History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        String[] columns = {
                "Donation ID",
                "Donor ID",
                "Donor Name",
                "Donation Date",
                "Blood Group",
                "Quantity"
        };

        tableModel = new DefaultTableModel(columns, 0);
        donationTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(donationTable);

        JButton addButton = new JButton("Add Donation");

        addButton.addActionListener(e -> addDonation());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addButton);

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addDonation() {

        String donationId = JOptionPane.showInputDialog(
                this, "Enter Donation ID:"
        );

        if (donationId == null || donationId.trim().isEmpty()) {
            return;
        }

        String donorId = JOptionPane.showInputDialog(
                this, "Enter Donor ID:"
        );

        if (donorId == null || donorId.trim().isEmpty()) {
            return;
        }

        String donorName = JOptionPane.showInputDialog(
                this, "Enter Donor Name:"
        );

        if (donorName == null || donorName.trim().isEmpty()) {
            return;
        }

        String donationDate = JOptionPane.showInputDialog(
                this, "Enter Donation Date:"
        );

        if (donationDate == null || donationDate.trim().isEmpty()) {
            return;
        }

        String bloodGroup = JOptionPane.showInputDialog(
                this, "Enter Blood Group:"
        );

        if (bloodGroup == null || bloodGroup.trim().isEmpty()) {
            return;
        }

        String quantity = JOptionPane.showInputDialog(
                this, "Enter Quantity (ml):"
        );

        if (quantity == null || quantity.trim().isEmpty()) {
            return;
        }

        tableModel.addRow(new Object[]{
                donationId,
                donorId,
                donorName,
                donationDate,
                bloodGroup,
                quantity + " ml"
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DonationHistoryUI ui = new DonationHistoryUI();
            ui.setVisible(true);
        });
    }
}