import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class StudentManagementSystem extends JFrame {

    JTextField idField, nameField, deptField;
    JButton addBtn, updateBtn, deleteBtn, clearBtn;
    JTable table;
    DefaultTableModel model;

    public StudentManagementSystem() {

        setTitle("Student Management System");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        inputPanel.add(new JLabel("Student ID"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Student Name"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Department"));
        deptField = new JTextField();
        inputPanel.add(deptField);

        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        clearBtn = new JButton("Clear");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(clearBtn);

        model = new DefaultTableModel();
        model.addColumn("Student ID");
        model.addColumn("Name");
        model.addColumn("Department");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addStudent());

        updateBtn.addActionListener(e -> updateStudent());

        deleteBtn.addActionListener(e -> deleteStudent());

        clearBtn.addActionListener(e -> clearFields());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                idField.setText(model.getValueAt(row, 0).toString());
                nameField.setText(model.getValueAt(row, 1).toString());
                deptField.setText(model.getValueAt(row, 2).toString());
            }
        });

        setVisible(true);
    }

    private void addStudent() {

        String id = idField.getText();
        String name = nameField.getText();
        String dept = deptField.getText();

        if(id.isEmpty() || name.isEmpty() || dept.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields");
            return;
        }

        model.addRow(new Object[]{id, name, dept});
        clearFields();
    }

    private void updateStudent() {

        int row = table.getSelectedRow();

        if(row >= 0) {
            model.setValueAt(idField.getText(), row, 0);
            model.setValueAt(nameField.getText(), row, 1);
            model.setValueAt(deptField.getText(), row, 2);
        } else {
            JOptionPane.showMessageDialog(this, "Select a row");
        }
    }

    private void deleteStudent() {

        int row = table.getSelectedRow();

        if(row >= 0) {
            model.removeRow(row);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        deptField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystem());
    }
}