import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class fertility extends JFrame implements ActionListener {

    JLabel lblDate, lblCycle, lblResult;
    JTextField txtDate, txtCycle;
    JButton btnCalculate;

    public fertility() {
        setTitle("Fertility Detection System");
        setSize(500, 250);
        setLayout(new GridLayout(4, 2, 10, 10));

        lblDate = new JLabel("Last Period Date (yyyy-MM-dd):");
        txtDate = new JTextField();

        lblCycle = new JLabel("Cycle Length (Days):");
        txtCycle = new JTextField();

        btnCalculate = new JButton("Calculate");
        btnCalculate.addActionListener(this);

        lblResult = new JLabel("");

        add(lblDate);
        add(txtDate);
        add(lblCycle);
        add(txtCycle);
        add(new JLabel(""));
        add(btnCalculate);
        add(new JLabel("Result:"));
        add(lblResult);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            LocalDate lastPeriod = LocalDate.parse(txtDate.getText());
            int cycleLength = Integer.parseInt(txtCycle.getText());

            int ovulationDay = cycleLength - 14;

            LocalDate fertileStart = lastPeriod.plusDays(ovulationDay - 5);
            LocalDate fertileEnd = lastPeriod.plusDays(ovulationDay + 1);

            lblResult.setText(
                "<html>Fertile Window:<br>" +
                fertileStart + " to " + fertileEnd +
                "</html>"
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter valid data.\nExample Date: 2026-06-01",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        new fertility();
    }
}