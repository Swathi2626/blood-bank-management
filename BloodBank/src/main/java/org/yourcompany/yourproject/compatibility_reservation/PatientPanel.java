package org.yourcompany.yourproject.compatibility_reservation;

import org.yourcompany.yourproject.inventory.BloodGroup;

import javax.swing.*;
import java.awt.*;

public class PatientPanel extends JFrame {

    private JTextField nameField;
    private JTextField contactField;
    private JTextField hospitalField;
    private JComboBox<String> bloodGroupBox;

    private PatientRepository patientRepository;

    public PatientPanel() {

        patientRepository = new MySQLPatientRepository();

        setTitle("Patient Registration");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Patient Registration");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 15));
        formPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        );

        formPanel.add(new JLabel("Patient Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Blood Group:"));

        String[] bloodGroups = {
                "A+", "A-", "B+", "B-",
                "AB+", "AB-", "O+", "O-"
        };

        bloodGroupBox = new JComboBox<>(bloodGroups);
        formPanel.add(bloodGroupBox);

        formPanel.add(new JLabel("Contact Number:"));
        contactField = new JTextField();
        formPanel.add(contactField);

        formPanel.add(new JLabel("Hospital Name:"));
        hospitalField = new JTextField();
        formPanel.add(hospitalField);

        JButton saveButton = new JButton("Register Patient");
        JButton clearButton = new JButton("Clear");

        formPanel.add(saveButton);
        formPanel.add(clearButton);

        add(formPanel, BorderLayout.CENTER);

        saveButton.addActionListener(e -> savePatient());

        clearButton.addActionListener(e -> clearFields());
    }

    private void savePatient() {

        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();
        String hospital = hospitalField.getText().trim();

        if (name.isEmpty() || contact.isEmpty() || hospital.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields."
            );
            return;
        }

        String selectedGroup =
                (String) bloodGroupBox.getSelectedItem();

        BloodGroup bloodGroup =
                convertBloodGroup(selectedGroup);

        Patient patient = new Patient(
                0,
                name,
                bloodGroup,
                contact,
                hospital
        );

        boolean saved = patientRepository.save(patient);

        if (saved) {

            JOptionPane.showMessageDialog(
                    this,
                    "Patient registered successfully!\nPatient ID: "
                            + patient.getPatientId()
            );

            clearFields();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to register patient."
            );
        }
    }

    private BloodGroup convertBloodGroup(String group) {

        switch (group) {

            case "A+": return BloodGroup.A_POSITIVE;
            case "A-": return BloodGroup.A_NEGATIVE;

            case "B+": return BloodGroup.B_POSITIVE;
            case "B-": return BloodGroup.B_NEGATIVE;

            case "AB+": return BloodGroup.AB_POSITIVE;
            case "AB-": return BloodGroup.AB_NEGATIVE;

            case "O+": return BloodGroup.O_POSITIVE;
            case "O-": return BloodGroup.O_NEGATIVE;

            default: return null;
        }
    }

    private void clearFields() {

        nameField.setText("");
        contactField.setText("");
        hospitalField.setText("");
        bloodGroupBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new PatientPanel().setVisible(true);
        });
    }
}