package org.yourcompany.yourproject.hospital;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HospitalRequestPanel extends JPanel {

    private HospitalRequestRepository repository;
    private DefaultTableModel tableModel;
    private JTable requestTable;

    public HospitalRequestPanel() {

        repository = HospitalRequestRepositoryProvider.getRepository();

        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Hospital Blood Request");

        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(
                new GridLayout(5, 2, 10, 10)
        );

        JLabel hospitalLabel = new JLabel("Hospital Name:");
        JTextField hospitalField = new JTextField();

        JLabel patientLabel = new JLabel("Patient Name:");
        JTextField patientField = new JTextField();

        JLabel bloodLabel = new JLabel("Blood Group:");

        String[] bloodGroups = {
                "A+", "A-", "B+", "B-",
                "AB+", "AB-", "O+", "O-"
        };

        JComboBox<String> bloodGroupBox =
                new JComboBox<>(bloodGroups);

        JLabel unitsLabel = new JLabel("Units Required:");
        JTextField unitsField = new JTextField();

        JLabel urgencyLabel = new JLabel("Urgency:");

        String[] urgencyOptions = {
                "Normal", "Urgent", "Emergency"
        };

        JComboBox<String> urgencyBox =
                new JComboBox<>(urgencyOptions);

        formPanel.add(hospitalLabel);
        formPanel.add(hospitalField);

        formPanel.add(patientLabel);
        formPanel.add(patientField);

        formPanel.add(bloodLabel);
        formPanel.add(bloodGroupBox);

        formPanel.add(unitsLabel);
        formPanel.add(unitsField);

        formPanel.add(urgencyLabel);
        formPanel.add(urgencyBox);

        JButton submitButton =
                new JButton("Submit Request");

        JButton clearButton =
                new JButton("Clear Form");

        submitButton.addActionListener(e -> {

            String hospitalName =
                    hospitalField.getText().trim();

            String patientName =
                    patientField.getText().trim();

            String bloodGroup =
                    (String) bloodGroupBox.getSelectedItem();

            String units =
                    unitsField.getText().trim();

            String urgency =
                    (String) urgencyBox.getSelectedItem();

            // Validate hospital name
            if (hospitalName.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the hospital name."
                );

                return;
            }

            // Validate patient name
            if (patientName.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the patient name."
                );

                return;
            }

            // Validate units
            if (units.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the number of units."
                );

                return;
            }

            int unitCount;

            try {

                unitCount = Integer.parseInt(units);

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Units must be a number."
                );

                return;
            }

            if (unitCount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Units must be greater than 0."
                );

                return;
            }

            String requestId =
                    "R" + (tableModel.getRowCount() + 1);

            // Create request
            HospitalRequest request =
                    new HospitalRequest(
                            requestId,
                            hospitalName,
                            patientName,
                            bloodGroup,
                            unitCount,
                            urgency
                    );

            // Save request
            repository.save(request);

            // Add request to table
            tableModel.addRow(
                    new Object[]{
                            requestId,
                            hospitalName,
                            patientName,
                            bloodGroup,
                            unitCount,
                            urgency,
                            "Pending"
                    }
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Request submitted successfully!"
            );

            // Clear form
            hospitalField.setText("");
            patientField.setText("");
            unitsField.setText("");
            bloodGroupBox.setSelectedIndex(0);
            urgencyBox.setSelectedIndex(0);
        });

        clearButton.addActionListener(e -> {

            hospitalField.setText("");
            patientField.setText("");
            unitsField.setText("");

            bloodGroupBox.setSelectedIndex(0);
            urgencyBox.setSelectedIndex(0);
        });

        JPanel bottomPanel = new JPanel();

        bottomPanel.add(submitButton);
        bottomPanel.add(clearButton);

        String[] columns = {
                "ID",
                "Hospital",
                "Patient",
                "Blood Group",
                "Units",
                "Urgency",
                "Status"
        };

        tableModel =
                new DefaultTableModel(columns, 0);

        requestTable =
                new JTable(tableModel);

        JScrollPane tableScrollPane =
                new JScrollPane(requestTable);

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(20, 20)
                );

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        50,
                        30,
                        50
                )
        );

        centerPanel.add(
                formPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                tableScrollPane,
                BorderLayout.CENTER
        );

        centerPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(
                centerPanel,
                BorderLayout.CENTER
        );
    }
}