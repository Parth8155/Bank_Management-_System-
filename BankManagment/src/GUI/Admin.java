package GUI;

import javax.swing.*;
import javax.swing.border.Border;

import SQL.sql;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin implements ActionListener {

    int sendAccountNo;

    private JButton viewAccountsButton;
    private JButton addAccountButton;
    private JButton logOutButton;
    private JPanel panel;

    private JFrame frame;
    private JPanel homPanel;
    private JPanel sidePanel1;

    private JTextField mobileNo;
    private JTextField email;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField birthText;
    private JTextArea address;
    private JTextField balance;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JButton save;
    private JLabel mobileNoLabel;
    private JLabel emaiLabel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel birthLabel;
    private JLabel genderLabel;
    private JLabel addressLabel;
    private JLabel balanceLabel;
    private String gender;

    private ButtonGroup group;
    private search c;

    private Color backgroundColor = new Color(200, 200, 200); // Background color of the panel

    public Admin() {

        frame = new JFrame();
        frame.setTitle("Admin Panel");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        homPanel = new JPanel();
        homPanel.setBackground(Color.lightGray);
        homPanel.setLayout(null);
        homPanel.setBounds(250, 0, 1300, 840);

        c = new search(homPanel);
        createBankPage();
        c.searchPanel();
        frame.add(homPanel);
        frame.setAlwaysOnTop(false);
        frame.setVisible(true);
    }

    private void createBankPage() {
        sidePanel1 = new JPanel();
        sidePanel1.setBounds(0, 0, 250, 840);
        sidePanel1.setBackground(new Color(230, 230, 230)); // Light color for the top
        sidePanel1.setLayout(null);

        viewAccountsButton = new JButton("View");
        viewAccountsButton.setBounds(50, 150, 150, 80);
        viewAccountsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        viewAccountsButton.setFocusable(false);
        sidePanel1.add(viewAccountsButton);

        addAccountButton = new JButton("Add");
        addAccountButton.setBounds(50, 270, 150, 80);
        addAccountButton.setFont(new Font("Arial", Font.PLAIN, 20));
        addAccountButton.setFocusable(false);
        sidePanel1.add(addAccountButton);

        logOutButton = new JButton("LogOut");
        logOutButton.setBounds(50, 630, 150, 80);
        logOutButton.setFont(new Font("Arial", Font.PLAIN, 20));
        logOutButton.setFocusable(false);
        sidePanel1.add(logOutButton);

        frame.add(sidePanel1);

        viewAccountsButton.addActionListener(this);
        addAccountButton.addActionListener(this);
        logOutButton.addActionListener(this);
    }

    JLabel savingsBalanceLabel;
    JTextField savingsBalance;

    private void addAccountPanel() {
        panel = new JPanel();
        panel.setBounds(0, 1, 1285, 790);
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 10);
        panel.setLayout(null);
        panel.setBorder(border);

        mobileNoLabel = new JLabel(" Mobile No");
        mobileNoLabel.setBounds(50, 90, 250, 50);
        mobileNoLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(mobileNoLabel);

        emaiLabel = new JLabel("Email ID");
        emaiLabel.setBounds(50, 150, 250, 50);
        emaiLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(emaiLabel);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setBounds(50, 210, 250, 50);
        firstNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(firstNameLabel);

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setBounds(50, 270, 250, 50);
        lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(lastNameLabel);

        birthLabel = new JLabel("Date of Birth (YYYY-MM-DD)");
        birthLabel.setBounds(50, 330, 280, 50);
        birthLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(birthLabel);

        genderLabel = new JLabel("Gender");
        genderLabel.setBounds(50, 390, 250, 50);
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(genderLabel);

        addressLabel = new JLabel("Address");
        addressLabel.setBounds(50, 450, 250, 50);
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(addressLabel);

        balanceLabel = new JLabel("Balance");
        balanceLabel.setBounds(50, 520, 250, 50);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(balanceLabel);

        savingsBalanceLabel = new JLabel("Savings Balance");
        savingsBalanceLabel.setBounds(50, 600, 250, 50);
        savingsBalanceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(savingsBalanceLabel);

        mobileNo = new JTextField();
        mobileNo.setBounds(310, 90, 400, 50);
        mobileNo.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(mobileNo);

        email = new JTextField();
        email.setBounds(310, 150, 400, 50);
        email.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(email);

        firstName = new JTextField();
        firstName.setBounds(310, 210, 400, 50);
        firstName.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(firstName);

        lastName = new JTextField();
        lastName.setBounds(310, 270, 400, 50);
        lastName.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(lastName);

        birthText = new JTextField();
        birthText.setBounds(310, 330, 400, 50);
        birthText.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(birthText);

        address = new JTextArea();
        address.setBounds(310, 440, 400, 80);
        address.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(address);

        balance = new JTextField();
        balance.setBounds(310, 520, 400, 50);
        balance.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(balance);

        savingsBalance = new JTextField();
        savingsBalance.setBounds(310, 600, 400, 50);
        savingsBalance.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(savingsBalance);

        maleRadioButton = new JRadioButton("Male");
        maleRadioButton.setBounds(310, 390, 100, 50);
        maleRadioButton.setFont(new Font("Arial", Font.PLAIN, 20));
        maleRadioButton.setBackground(backgroundColor);

        femaleRadioButton = new JRadioButton("Female");
        femaleRadioButton.setBounds(420, 390, 150, 50);
        femaleRadioButton.setFont(new Font("Arial", Font.PLAIN, 20));
        femaleRadioButton.setBackground(backgroundColor);

        group = new ButtonGroup();
        group.add(maleRadioButton);
        group.add(femaleRadioButton);

        panel.add(maleRadioButton);
        panel.add(femaleRadioButton);

        save = new JButton("Save");
        save.setBounds(450, 670, 150, 60);
        save.setFont(new Font("Arial", Font.PLAIN, 20));
        save.setFocusable(false);
        save.setBackground(new Color(0, 204, 153));
        save.setForeground(Color.white);
        panel.add(save);

        save.addActionListener(this);
        homPanel.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        homPanel.removeAll(); // Remove all components from homPanel
        homPanel.revalidate();
        homPanel.repaint();

        if (e.getSource() == viewAccountsButton) {
            c.searchPanel(); // Show the search panel for viewing accounts
        } else if (e.getSource() == addAccountButton) {
            addAccountPanel(); // Show the panel to add accounts
        } else if (e.getSource() == logOutButton) {
            int result = JOptionPane.showConfirmDialog(
                    frame, "Do you want to LogOut?", "Confirm", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                frame.dispose();
                new Login();
            }
        } else if (e.getSource() == save) {
            if (mobileNo.getText().isEmpty() || email.getText().isEmpty()
                    || firstName.getText().isEmpty() || lastName.getText().isEmpty() || birthText.getText().isEmpty()
                    || address.getText().isEmpty() || balance.getText().isEmpty() || group.getSelection() == null
                    || savingsBalance.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter All Data", "Add Account", JOptionPane.PLAIN_MESSAGE);
            } else {
                if (maleRadioButton.isSelected()) {
                    gender = "Male";
                } else {
                    gender = "Female";
                }
                int n = addAccount();
                JOptionPane.showMessageDialog(null, "Added successfully,\n you account Number :- " + n, "Add Account",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    private int addAccount() {
        return sql.addAccount(mobileNo.getText(), email.getText(), firstName.getText(),
                lastName.getText(), gender, address.getText(), balance.getText(), savingsBalance.getText(),
                birthText.getText());
    }
}
