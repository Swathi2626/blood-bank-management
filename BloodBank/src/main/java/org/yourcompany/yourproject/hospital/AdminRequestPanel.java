package org.yourcompany.yourproject.hospital;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import org.yourcompany.yourproject.inventory.BloodUnit;
import org.yourcompany.yourproject.inventory.BloodStatus;
import org.yourcompany.yourproject.inventory.InventoryManager;

public class AdminRequestPanel extends JPanel {

    private HospitalRequestRepository repository;
    private DefaultTableModel tableModel;
    private JTable requestTable;

    public AdminRequestPanel() {

        // Use the SAME repository as the Hospital side
        repository = HospitalRequestRepositoryProvider.getRepository();

        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Admin - Hospital Requests");

        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // Low stock alert
        JPanel alertPanel = new JPanel();
        alertPanel.setLayout(
                new BoxLayout(alertPanel, BoxLayout.Y_AXIS)
        );

        JLabel alertTitle =
                new JLabel("LOW STOCK ALERT");

        alertTitle.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        InventoryManager inventoryManager = new InventoryManager();

        int oPositiveCount = 0;

        for (BloodUnit unit : inventoryManager.getAllBloodUnits()) {

                if (unit.getBloodGroup().name().equals("O_POSITIVE")
                        && unit.getStatus() == BloodStatus.AVAILABLE) {

                        oPositiveCount++;
                }
        }

        JLabel alertMessage =
                new JLabel("O+ : " + oPositiveCount + " units available");

        alertPanel.add(alertTitle);
        alertPanel.add(alertMessage);

        JPanel topPanel = new JPanel();

        topPanel.setLayout(
                new BoxLayout(topPanel, BoxLayout.Y_AXIS)
        );

        topPanel.add(title);
        topPanel.add(alertPanel);

        add(topPanel, BorderLayout.NORTH);

        // Request table
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

        loadRequests();

        // Approve button
        JButton approveButton =
                new JButton("Approve Request");

        approveButton.addActionListener(e -> {

            int selectedRow =
                    requestTable.getSelectedRow();

            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a request first."
                );

                return;
            }

            String requestId =
                    (String) tableModel.getValueAt(
                            selectedRow,
                            0
                    );

            HospitalRequest request =
                    repository.findById(requestId);

            if (request != null) {

                request.setStatus("Approved");

                repository.update(request);

                tableModel.setValueAt(
                        "Approved",
                        selectedRow,
                        6
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Request approved successfully."
                );
            }
        });

        // Reject button
        JButton rejectButton =
                new JButton("Reject Request");

        rejectButton.addActionListener(e -> {

            int selectedRow =
                    requestTable.getSelectedRow();

            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a request first."
                );

                return;
            }

            String requestId =
                    (String) tableModel.getValueAt(
                            selectedRow,
                            0
                    );

            HospitalRequest request =
                    repository.findById(requestId);

            if (request != null) {

                request.setStatus("Rejected");

                repository.update(request);

                tableModel.setValueAt(
                        "Rejected",
                        selectedRow,
                        6
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Request rejected."
                );
            }
        });

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);

        JPanel centerPanel =
                new JPanel(new BorderLayout(20, 20));

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        50,
                        30,
                        50
                )
        );

        centerPanel.add(
                tableScrollPane,
                BorderLayout.CENTER
        );

        centerPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(
                centerPanel,
                BorderLayout.CENTER
        );
    }

    private void loadRequests() {

        tableModel.setRowCount(0);

        for (HospitalRequest request :
                repository.findAll()) {

            tableModel.addRow(
                    new Object[]{
                            request.getRequestId(),
                            request.getHospitalName(),
                            request.getPatientName(),
                            request.getBloodGroup(),
                            request.getUnitsRequired(),
                            request.getUrgency(),
                            request.getStatus()
                    }
            );
        }
    }
}