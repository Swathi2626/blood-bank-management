package org.yourcompany.yourproject.hospital;

import com.bloodbank.dashboard.DashboardTheme;
import com.bloodbank.dashboard.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminRequestPanel extends JPanel {

    private HospitalRequestRepository repository;
    private DefaultTableModel tableModel;
    private JTable requestTable;
    private final MainFrame mainFrame;

    public AdminRequestPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        repository =
                HospitalRequestRepositoryProvider.getRepository();

        setLayout(new BorderLayout(10, 10));

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setOpaque(false);
        JLabel title = new JLabel("Admin - Hospital Requests");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton backButton = DashboardTheme.createSecondaryButton("Back");
        backButton.addActionListener(e -> mainFrame.goBackToDashboard());

        titleBar.add(title, BorderLayout.CENTER);
        titleBar.add(backButton, BorderLayout.EAST);
        add(titleBar, BorderLayout.NORTH);

        String[] columns = {
                "ID",
                "Hospital",
                "Blood Group",
                "Component",
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

        JButton approveButton =
                new JButton("Approve Request");

        approveButton.addActionListener(e ->
                updateSelectedRequest("APPROVED")
        );

        JButton rejectButton =
                new JButton("Reject Request");

        rejectButton.addActionListener(e ->
                updateSelectedRequest("REJECTED")
        );

        JButton refreshButton =
                new JButton("Refresh");

        refreshButton.addActionListener(e ->
                loadRequests()
        );

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(refreshButton);

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
                            request.getBloodGroup(),
                            request.getComponent(),
                            request.getUnitsRequired(),
                            request.getUrgency(),
                            request.getStatus()
                    }
            );
        }
    }


    private void updateSelectedRequest(String newStatus) {

        int selectedRow =
                requestTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a request first."
            );

            return;
        }

        int requestId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        HospitalRequest request =
                repository.findById(requestId);

        if (request == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Request not found."
            );

            return;
        }

        request.setStatus(newStatus);

        repository.update(request);

        loadRequests();

        JOptionPane.showMessageDialog(
                this,
                "Request " +
                newStatus.toLowerCase() +
                " successfully."
        );
    }
}
