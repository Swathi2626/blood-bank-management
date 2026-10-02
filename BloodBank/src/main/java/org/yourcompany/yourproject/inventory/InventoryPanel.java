package org.yourcompany.yourproject.inventory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import jdk.vm.ci.meta.Local;

import java.time.LocalDate;
import java.util.List;

public class InventoryPanel extends JPanel{
    private JTable table;
    private InventoryManager inventoryManager;
    private DefaultTableModel tableModel;
    private JComboBox<BloodGroup> bloodGroupCombo;
    private JComboBox<BloodComponent> bloodComponentCombo;
    private JTextField phoneField;
    private JTextField collectionDateField;

    public InventoryPanel(){
        inventoryManager = new InventoryManager();
        setLayout(new BorderLayout());

        String[] columns = {"Unit ID", "Blood Group", "Blood Component", "Donor Phone", "Collection Date", "Expiration Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new FlowLayout());
        bloodComponentCombo = new JComboBox<>(BloodGroup.values());
        collectionDateField = new JTextField(LocalDate.now().toString(), 10);
        expiryDateField = new JTextField(LocalDate.now().plusDays(42).toString(), 10);
        JButton addButton = new JButton("Add Blood Unit");
        formPanel.add(new JLabel("Group:"));
        formPanel.add(bloodGroupCombo);
        formPanel.add(new JLabel("Collected:"));
        formPanel.add(collectionDateField);
        formPanel.add(new JLabel("Expires:"));
        formPanel.add(expiryDateField);
        formPanel.add(addButton);
        add(formPanel, BorderLayout.SOUTH);
    }

    private void refreshTable(){
        tableModel.setRowCount(0);
                List<BloodUnit> units = inventoryManager.getAllBloodUnits();
                for (BloodUnit u : units) {
                    Object[] row = {
                        u.getUnitId(),
                        u.getBloodGroup(),
                        u.getComponent(),
                        u.getDonorPhone(),
                        u.getCollectionDate(),
                        u.getExpiryDate(),
                        u.getStatus()
                    };
                    tableModel.addRow(row);
                }
        }


    private void addUnitAction(){
        try{
            BloodGroup bg = (BloodGroup) bloodGroupCombo.getSelectedItem();
            BloodComponent comp = (BloodComponent) componentCombo.getSelectedItem();
            String phone = donorPhoneField.getText();
            LocalDate collectionDate = LocalDate.parse(collectionDateField.getText());
            BloodUnit newUnit = new BloodUnit(0, bg, comp, phone, collDate, BloodStatus.AVAILABLE);
                        inventoryManager.addBloodUnit(newUnit);
            
                        JOptionPane.showMessageDialog(this, "Blood unit added successfully!");
                        refreshTable();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input format: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
            
        }


        
}

    
