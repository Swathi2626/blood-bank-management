package org.yourcompany.yourproject.donation;

import com.bloodbank.common.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DonationHistoryUI extends JPanel {

    private DefaultTableModel tableModel;
    private JTable donationTable;

    public DonationHistoryUI() {

        setLayout(new BorderLayout());

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

        loadDonationHistory();
    }

    private void loadDonationHistory() {

        String query = """
                SELECT dr.donation_id,
                       d.donor_id,
                       d.donor_name,
                       dr.donation_date,
                       d.blood_group,
                       dr.quantity_ml
                FROM donation_records dr
                JOIN donors d ON dr.donor_id = d.donor_id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            tableModel.setRowCount(0);

            while (resultSet.next()) {

                tableModel.addRow(new Object[]{
                        resultSet.getInt("donation_id"),
                        resultSet.getInt("donor_id"),
                        resultSet.getString("donor_name"),
                        resultSet.getDate("donation_date"),
                        resultSet.getString("blood_group"),
                        resultSet.getInt("quantity_ml") + " ml"
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading donation history:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void addDonation() {

        String donorId = JOptionPane.showInputDialog(
                this, "Enter Donor ID:"
        );

        if (donorId == null || donorId.trim().isEmpty()) {
            return;
        }

        String donationDate = JOptionPane.showInputDialog(
                this, "Enter Donation Date (YYYY-MM-DD):"
        );

        if (donationDate == null || donationDate.trim().isEmpty()) {
            return;
        }

        String quantity = JOptionPane.showInputDialog(
                this, "Enter Quantity (ml):"
        );

        if (quantity == null || quantity.trim().isEmpty()) {
            return;
        }

        String query = """
                INSERT INTO donation_records
                (donor_id, donation_date, quantity_ml)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, Integer.parseInt(donorId));
            statement.setDate(2, java.sql.Date.valueOf(donationDate));
            statement.setInt(3, Integer.parseInt(quantity));

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Donation added successfully!"
            );

            loadDonationHistory();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Donor ID and Quantity must be numbers.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the date in YYYY-MM-DD format.",
                    "Invalid Date",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error saving donation:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}