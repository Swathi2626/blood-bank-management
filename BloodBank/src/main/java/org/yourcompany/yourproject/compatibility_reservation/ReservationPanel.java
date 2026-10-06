package org.yourcompany.yourproject.compatibility_reservation;

import javax.swing.*;
import java.awt.*;

import org.yourcompany.yourproject.inventory.BloodComponent;
import org.yourcompany.yourproject.inventory.BloodGroup;
import org.yourcompany.yourproject.inventory.BloodUnit;

public class ReservationPanel extends JFrame {

    private JComboBox<String> bloodGroupBox;
    private JComboBox<BloodComponent> componentBox;
    private JTextField patientIdField;

    private JLabel unitIdLabel;
    private JLabel unitGroupLabel;
    private JLabel unitComponentLabel;
    private JLabel unitStatusLabel;

    private Reservation reservation;
    private BloodUnit reservedUnit;

    public ReservationPanel() {

        reservation = new Reservation();

        setTitle("Blood Reservation");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Title
        JLabel title = new JLabel("Blood Reservation");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        add(title, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 50, 20, 50)
        );

        // Patient ID
        formPanel.add(new JLabel("Patient ID:"));
        patientIdField = new JTextField();
        formPanel.add(patientIdField);

        // Blood Group
        JLabel bloodGroupLabel =
                new JLabel("Recipient Blood Group:");

        String[] bloodGroups = {
                "A+", "A-", "B+", "B-",
                "AB+", "AB-", "O+", "O-"
        };

        bloodGroupBox = new JComboBox<>(bloodGroups);

        formPanel.add(bloodGroupLabel);
        formPanel.add(bloodGroupBox);

        // Blood Component
        JLabel componentLabel =
                new JLabel("Blood Component:");

        componentBox = new JComboBox<>(
                BloodComponent.values()
        );

        formPanel.add(componentLabel);
        formPanel.add(componentBox);

        // Buttons
        JButton reserveButton =
                new JButton("Find & Reserve");

        JButton cancelButton =
                new JButton("Cancel Reservation");

        formPanel.add(reserveButton);
        formPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);

        // Reservation details
        JPanel resultPanel = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        resultPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Reservation Details"
                )
        );

        resultPanel.add(new JLabel("Unit ID:"));
        unitIdLabel = new JLabel("-");
        resultPanel.add(unitIdLabel);

        resultPanel.add(new JLabel("Blood Group:"));
        unitGroupLabel = new JLabel("-");
        resultPanel.add(unitGroupLabel);

        resultPanel.add(new JLabel("Component:"));
        unitComponentLabel = new JLabel("-");
        resultPanel.add(unitComponentLabel);

        resultPanel.add(new JLabel("Status:"));
        unitStatusLabel = new JLabel("-");
        resultPanel.add(unitStatusLabel);

        add(resultPanel, BorderLayout.SOUTH);

        // Reserve button
        reserveButton.addActionListener(e -> {

            String patientIdText =
                    patientIdField.getText().trim();

            if (patientIdText.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Patient ID."
                );
                return;
            }

            int patientId;

            try {
                patientId = Integer.parseInt(patientIdText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Patient ID must be a number."
                );
                return;
            }

            String selectedGroup =
                    (String) bloodGroupBox.getSelectedItem();

            BloodGroup recipient =
                    convertBloodGroup(selectedGroup);

            BloodComponent component =
                    (BloodComponent) componentBox.getSelectedItem();

            reservedUnit =
                    reservation.reserveAvailableUnit(
                            recipient,
                            component
                    );

            if (reservedUnit != null) {

                boolean saved =
                        reservation.saveReservation(
                                patientId,
                                reservedUnit
                        );

                if (!saved) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Blood unit found, but reservation could not be saved."
                    );

                    reservedUnit = null;
                    return;
                }

                unitIdLabel.setText(
                        String.valueOf(
                                reservedUnit.getUnitId()
                        )
                );

                unitGroupLabel.setText(
                        reservedUnit.getBloodGroup().toString()
                );

                unitComponentLabel.setText(
                        reservedUnit.getComponent().toString()
                );

                unitStatusLabel.setText(
                        reservedUnit.getStatus().toString()
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Blood unit reserved successfully!"
                );

            } else {

                unitIdLabel.setText("-");
                unitGroupLabel.setText("-");
                unitComponentLabel.setText("-");
                unitStatusLabel.setText("-");

                JOptionPane.showMessageDialog(
                        this,
                        "No compatible blood unit available."
                );
            }
        });

        // Cancel button
        cancelButton.addActionListener(e -> {

            if (reservedUnit == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "There is no reservation to cancel."
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation cancellation will be connected to the database during final integration."
            );
        });
    }

    private BloodGroup convertBloodGroup(String group) {

        switch (group) {

            case "A+":
                return BloodGroup.A_POSITIVE;

            case "A-":
                return BloodGroup.A_NEGATIVE;

            case "B+":
                return BloodGroup.B_POSITIVE;

            case "B-":
                return BloodGroup.B_NEGATIVE;

            case "AB+":
                return BloodGroup.AB_POSITIVE;

            case "AB-":
                return BloodGroup.AB_NEGATIVE;

            case "O+":
                return BloodGroup.O_POSITIVE;

            case "O-":
                return BloodGroup.O_NEGATIVE;

            default:
                return null;
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new ReservationPanel().setVisible(true);
        });
    }
}