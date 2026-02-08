package ui;

import dao.CourierDAO;
import models.Courier;
import javax.swing.*;

public class CourierPanel extends JPanel {

    public CourierPanel() {

        setLayout(null);

        JTextField s = new JTextField();
        JTextField r = new JTextField();
        JTextField a = new JTextField();

        JButton save = new JButton("Save");

        s.setBounds(100,30,150,25);
        r.setBounds(100,70,150,25);
        a.setBounds(100,110,150,25);
        save.setBounds(120,160,100,30);

        add(new JLabel("Sender")).setBounds(20,30,80,25);
        add(new JLabel("Receiver")).setBounds(20,70,80,25);
        add(new JLabel("Address")).setBounds(20,110,80,25);

        add(s); add(r); add(a); add(save);

        save.addActionListener(e -> {
            Courier c = new Courier();
            c.sender=s.getText();
            c.receiver=r.getText();
            c.address=a.getText();
            CourierDAO.addCourier(c);
            JOptionPane.showMessageDialog(this,"Saved");
        });
    }
}
