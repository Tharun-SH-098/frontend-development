import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OMS extends JFrame {

    JTextField fieldName, location, production;
    DefaultTableModel model;
    JTable table;

    public OMS() {

        setTitle("Oil Management System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Header
        JLabel title = new JLabel(
                "VENEZUELA OIL MANAGEMENT SYSTEM",
                SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 153));
        title.setForeground(Color.WHITE);
        title.setPreferredSize(new Dimension(900, 60));
        add(title, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.setBorder(
                BorderFactory.createTitledBorder("Oil Field Details"));

        fieldName = new JTextField();
        location = new JTextField();
        production = new JTextField();

        formPanel.add(new JLabel("Field Name:"));
        formPanel.add(fieldName);

        formPanel.add(new JLabel("Location:"));
        formPanel.add(location);

        formPanel.add(new JLabel("Production (Barrels):"));
        formPanel.add(production);

        JButton addBtn = new JButton("Add Record");
        JButton clearBtn = new JButton("Clear");

        formPanel.add(addBtn);
        formPanel.add(clearBtn);

        // Table
        String[] columns = {
                "ID",
                "Field Name",
                "Location",
                "Production"
        };

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        // Center Panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Dashboard Panel
        JPanel dashboard = new JPanel(new GridLayout(1, 4, 10, 10));

        dashboard.add(createCard("Oil Fields", "25"));
        dashboard.add(createCard("Refineries", "10"));
        dashboard.add(createCard("Exports", "150"));
        dashboard.add(createCard("Revenue", "$15B"));

        add(dashboard, BorderLayout.SOUTH);

        // Add Button Action
        addBtn.addActionListener(e -> {

            String field = fieldName.getText();
            String loc = location.getText();
            String prod = production.getText();

            if (field.isEmpty() ||
                loc.isEmpty() ||
                prod.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields!");
                return;
            }

            int id = model.getRowCount() + 1;

            model.addRow(new Object[]{
                    id,
                    field,
                    loc,
                    prod
            });

            JOptionPane.showMessageDialog(
                    this,
                    "Record Added Successfully");

            clearFields();
        });

        clearBtn.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private JPanel createCard(String title, String value) {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2));

        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel v = new JLabel(value, SwingConstants.CENTER);
        v.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(t, BorderLayout.NORTH);
        panel.add(v, BorderLayout.CENTER);

        return panel;
    }

    private void clearFields() {
        fieldName.setText("");
        location.setText("");
        production.setText("");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new OMS();
        });
    }
}