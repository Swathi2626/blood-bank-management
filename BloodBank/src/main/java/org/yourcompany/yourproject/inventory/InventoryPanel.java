package org.yourcompany.yourproject.inventory;

import com.bloodbank.dashboard.DashboardTheme;
import com.bloodbank.dashboard.MainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class InventoryPanel extends JPanel {
    private final JTable table;
    private final InventoryManager inventoryManager;
    private final DefaultTableModel tableModel;
    private final JComboBox<BloodGroup> bloodGroupCombo;
    private final JComboBox<BloodComponent> componentCombo;
    private final JTextField phone;
    private final JTextField collectionDateField;
    private final MainFrame mainFrame;

    public InventoryPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        inventoryManager = new InventoryManager();
        setLayout(new BorderLayout(16, 16));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(DashboardTheme.BACKGROUND);

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setOpaque(false);
        JLabel title = DashboardTheme.createSectionTitle("Inventory Management");
        JButton backButton = DashboardTheme.createSecondaryButton("Back");
        backButton.addActionListener(e -> mainFrame.goBackToDashboard());
        titleBar.add(title, BorderLayout.WEST);
        titleBar.add(backButton, BorderLayout.EAST);
        add(titleBar, BorderLayout.NORTH);

        String[] columns = {"Unit ID", "Blood Group", "Blood Component", "Donor Phone", "Collection Date", "Expiration Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(32);
        table.setGridColor(new Color(230, 234, 239));
        table.setSelectionBackground(DashboardTheme.BLOOD_RED);
        table.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(2, 5, 8, 8));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                new DashboardTheme.RoundedBorder(new Color(226, 232, 238), 18),
                new EmptyBorder(12, 12, 12, 12)
        ));

        bloodGroupCombo = new JComboBox<>(BloodGroup.values());
        componentCombo = new JComboBox<>(BloodComponent.values());
        phone = new JTextField();
        collectionDateField = new JTextField(LocalDate.now().toString());

        JButton addButton = DashboardTheme.createActionButton("Add Blood Unit");

        formPanel.add(new JLabel("Group:"));
        formPanel.add(new JLabel("Component:"));
        formPanel.add(new JLabel("Donor Phone:"));
        formPanel.add(new JLabel("Collected:"));
        formPanel.add(new JLabel(""));

        formPanel.add(bloodGroupCombo);
        formPanel.add(componentCombo);
        formPanel.add(phone);
        formPanel.add(collectionDateField);
        formPanel.add(addButton);

        formPanel.setPreferredSize(new Dimension(800, 120));
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
