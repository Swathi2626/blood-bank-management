package org.yourcompany.yourproject.hospital;

import com.bloodbank.dashboard.DashboardTheme;
import com.bloodbank.dashboard.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HospitalRequestPanel extends JPanel {

    private HospitalRequestRepository repository;
    private DefaultTableModel tableModel;
    private JTable requestTable;
    private final MainFrame mainFrame;

    public HospitalRequestPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        repository =
                HospitalRequestRepositoryProvider.getRepository();

        setLayout(new BorderLayout(10, 10));

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setOpaque(false);
        JLabel title = new JLabel("Hospital Blood Request");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton backButton = DashboardTheme.createSecondaryButton("Back");
        backButton.addActionListener(e -> mainFrame.goBackToDashboard());

        titleBar.add(title, BorderLayout.CENTER);
        titleBar.add(backButton, BorderLayout.EAST);
        add(titleBar, BorderLayout.NORTH);

        JPanel formPanel =
                new JPanel(
                        new GridLayout(5, 2, 10, 10)
                );

        // Hospital Name
        JLabel hospitalLabel =
                new JLabel("Hospital Name:");

        JTextField hospitalField =
                new JTextField();

        // Blood Group
        JLabel bloodLabel =
                new JLabel("Blood Group:");

        String[] bloodGroups = {
                "A_POSITIVE",
                "A_NEGATIVE",
                "B_POSITIVE",
                "B_NEGATIVE",
                "AB_POSITIVE",
                "AB_NEGATIVE",
                "O_POSITIVE",
                "O_NEGATIVE"
        };

        JComboBox<String> bloodGroupBox =
                new JComboBox<>(bloodGroups);

        // Component
        JLabel componentLabel =
                new JLabel("Component:");

        String[] components = {
                "WHOLE_BLOOD",
                "PRBC",
                "PLATELETS",
                "FPP"
        };

        JComboBox<String> componentBox =
                new JComboBox<>(components);

        // Units
        JLabel unitsLabel =
                new JLabel("Units Required:");

        JTextField unitsField =
                new JTextField();

        // Urgency
        JLabel urgencyLabel =
                new JLabel("Urgency:");

        String[] urgencyOptions = {
                "Normal",
                "Urgent",
                "Emergency"
        };

        JComboBox<String> urgencyBox =
                new JComboBox<>(urgencyOptions);

        formPanel.add(hospitalLabel);
        formPanel.add(hospitalField);

        formPanel.add(bloodLabel);
        formPanel.add(bloodGroupBox);

        formPanel.add(componentLabel);
        formPanel.add(componentBox);

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

            String bloodGroup =
                    (String) bloodGroupBox.getSelectedItem();

            String component =
                    (String) componentBox.getSelectedItem();

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

                unitCount =
                        Integer.parseInt(units);

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

            /*
             * requestId is 0 here because MySQL
             * generates the actual ID using
             * AUTO_INCREMENT.
             */
            HospitalRequest request =
                    new HospitalRequest(
                            0,
                            hospitalName,
                            bloodGroup,
                            component,
                            unitCount,
                            urgency
                    );

            repository.save(request);

            // Reload from database so that the
            // generated request ID is displayed.
            loadRequests();

            JOptionPane.showMessageDialog(
                    this,
                    "Request submitted successfully!"
            );

            clearForm(
                    hospitalField,
                    unitsField,
                    bloodGroupBox,
                    componentBox,
                    urgencyBox
            );
        });

        clearButton.addActionListener(e -> {

            clearForm(
                    hospitalField,
                    unitsField,
                    bloodGroupBox,
                    componentBox,
                    urgencyBox
            );
        });

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.add(submitButton);
        bottomPanel.add(clearButton);

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
                new DefaultTableModel(
                        columns,
                        0
                );

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

        // Load existing requests from MySQL
        loadRequests();
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


    private void clearForm(
            JTextField hospitalField,
            JTextField unitsField,
            JComboBox<String> bloodGroupBox,
            JComboBox<String> componentBox,
            JComboBox<String> urgencyBox) {

        hospitalField.setText("");
        unitsField.setText("");

        bloodGroupBox.setSelectedIndex(0);
        componentBox.setSelectedIndex(0);
        urgencyBox.setSelectedIndex(0);
    }
}