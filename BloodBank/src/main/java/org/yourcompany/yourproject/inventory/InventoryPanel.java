package org.yourcompany.yourproject.inventory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class InventoryPanel extends JPanel {
    private final JTable table;
    private final InventoryManager inventoryManager;
    private final DefaultTableModel tableModel;
    private final JComboBox<BloodGroup> bloodGroupCombo;
    private final JComboBox<BloodComponent> componentCombo;
    private final JTextField phone;
    private final JTextField collectionDateField;

    public InventoryPanel() {
        inventoryManager = new InventoryManager();
        setLayout(new BorderLayout());

        String[] columns = {"Unit ID", "Blood Group", "Blood Component", "Donor Phone", "Collection Date", "Expiration Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new FlowLayout());
        bloodGroupCombo = new JComboBox<>(BloodGroup.values());
        componentCombo = new JComboBox<>(BloodComponent.values());
        phone = new JTextField(12);
        collectionDateField = new JTextField(LocalDate.now().toString(), 10);
        JButton addButton = new JButton("Add Blood Unit");

        formPanel.add(new JLabel("Group:"));
        formPanel.add(bloodGroupCombo);
        formPanel.add(new JLabel("Component:"));
        formPanel.add(componentCombo);
        formPanel.add(new JLabel("Donor phone:"));
        formPanel.add(phone);
        formPanel.add(new JLabel("Collected:"));
        formPanel.add(collectionDateField);
        formPanel.add(addButton);
        add(formPanel, BorderLayout.SOUTH);

        addButton.addActionListener(event -> addUnitAction());
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<BloodUnit> units = inventoryManager.getAllBloodUnits();
        for (BloodUnit unit : units) {
            Object[] row = {
                unit.getUnitId(),
                unit.getBloodGroup(),
                unit.getComponent(),
                unit.getDonorPhone(),
                unit.getCollectionDate(),
                unit.getExpiryDate(),
                unit.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void addUnitAction() {
        try {
            BloodGroup bloodGroup = (BloodGroup) bloodGroupCombo.getSelectedItem();
            BloodComponent component = (BloodComponent) componentCombo.getSelectedItem();
            LocalDate collectionDate = LocalDate.parse(collectionDateField.getText());
            BloodUnit newUnit = new BloodUnit(
                0,
                bloodGroup,
                component,
                phone.getText(),
                collectionDate,
                BloodStatus.AVAILABLE
            );

            if (inventoryManager.addBloodUnit(newUnit)) {
                JOptionPane.showMessageDialog(this, "Blood unit added successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Unable to add blood unit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Invalid input format: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
