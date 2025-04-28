package GUI;

import bank.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.Border;

import SQL.sql;
import bank.Account;

public class search implements ActionListener {

    private JPanel homPanel;
    private JTextField accountNo;
    JPanel panel;
    private Account account;
    private Color topColor = new Color(230, 230, 230); // Light color for the top
    private Color bottomColor = new Color(150, 150, 150); // Dark color for the bottom
    private Color backgroundColor = new Color(200, 200, 200); // Background color of the pan
    private Color darkGreenColor = new Color(20, 52, 29); // Background color of the pan

    public search(JPanel homPanel) {
        this.homPanel = homPanel;
    }

    public void searchPanel() {

        homPanel.removeAll();
        homPanel.revalidate();
        homPanel.repaint();

        panel = new JPanel();
        panel.setBounds(0, 1, 1285, 790);
        Border border = BorderFactory.createLineBorder(java.awt.Color.DARK_GRAY, 10);
        panel.setLayout(null);
        panel.setBorder(border);

        JLabel accountNoLabel = new JLabel("Enter Account No");
        accountNoLabel.setBounds(100, 150, 200, 40);
        accountNoLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20));
        panel.add(accountNoLabel);

        accountNo = new JTextField();
        accountNo.setBounds(310, 150, 300, 40);
        accountNo.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20));
        panel.add(accountNo);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(630, 150, 100, 60);
        searchButton.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20));
        searchButton.setFocusable(false);
        searchButton.addActionListener(e -> {
            String accNo = accountNo.getText();
            if (accNo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Account No", "Search Account",
                        JOptionPane.PLAIN_MESSAGE);
            } else if (sql.isInteger(accNo)) {
                if (sql.correctId(Integer.parseInt(accNo))) {
                    printAccounts();
                } else {
                    JOptionPane.showMessageDialog(null, "Account not found!", "Search Account",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        panel.add(searchButton);

        homPanel.add(panel);
    }

    JPanel searchTextArea;

    public void printAccounts() {
        if (searchTextArea != null) {
            panel.remove(searchTextArea);
        }

        JScrollPane existingScrollPane = null;
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JScrollPane) {
                existingScrollPane = (JScrollPane) comp;
                break;
            }
        }

        if (existingScrollPane != null) {
            panel.remove(existingScrollPane);
        }

        Account acc = sql.getAllData(sql.getInteger(accountNo.getText()));

        searchTextArea = new JPanel();
        searchTextArea.setLayout(null);
        searchTextArea.setBackground(Color.white);

        Border border = BorderFactory.createLineBorder(java.awt.Color.DARK_GRAY, 2);

        JLabel searchAccountLabel;
        JButton profileButton;

        searchAccountLabel = new JLabel("            " + acc.getAccNo() + "         " +
                acc.getFirst_Name() + "       " + acc.getSavingBalance() + "           " + acc.getCurrentBalance());
        searchAccountLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        searchAccountLabel.setBounds(10, 20, 800, 50);
        searchAccountLabel.setBorder(border);
        searchAccountLabel.setLayout(null);

        ImageIcon image = new ImageIcon("user.png");
        Image scaledImage = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        profileButton = new JButton(new ImageIcon(scaledImage));
        profileButton.setBounds(20, 10, 30, 30);
        searchAccountLabel.add(profileButton);
        searchTextArea.add(searchAccountLabel);

        profileButton.addActionListener(e -> showProfile(acc));

        JScrollPane scrollPane = new JScrollPane(searchTextArea);
        scrollPane.setBounds(25, 200, 900, 500);
        scrollPane.setBackground(Color.white);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panel.add(scrollPane);
        panel.revalidate();
        panel.repaint();
    }

    private JLabel accountNoLabel, mobileNoLabel, emaiLabel, firstNameLabel, lastNameLabel, birthLabel, genderLabel,
            addressLabel, balanceLabel, savingsBalanceLabel, opaningLabel;
    private JTextField opaningDate, accountNoText, mobileNo, email, firstName, lastName, gender, birthText, balance,
            savingsBalance;
    private JTextArea address;
    private JButton save, Exit, edit, delete;
    private JPanel profilePanel;

    private boolean isEditing = false; // Flag to track if editing is enabled

    private void showProfile(Account acc) {

        profilePanel = new JPanel();
        profilePanel.setBounds(0, 1, 1285, 790);
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 10);
        profilePanel.setLayout(null);
        profilePanel.setBorder(border);

        accountNoLabel = new JLabel("Account No");
        accountNoLabel.setBounds(50, 40, 250, 50);
        accountNoLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        profilePanel.add(accountNoLabel);

        mobileNoLabel = new JLabel("Registered Mobile No");
        mobileNoLabel.setBounds(50, 90, 250, 50);
        mobileNoLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        profilePanel.add(mobileNoLabel);

        emaiLabel = new JLabel("Email ID");
        emaiLabel.setBounds(50, 150, 250, 50);
        emaiLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        profilePanel.add(emaiLabel);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setBounds(50, 210, 250, 50);
        firstNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        profilePanel.add(firstNameLabel);

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setBounds(50, 270, 250, 50);
        lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        profilePanel.add(lastNameLabel);

        birthLabel = new JLabel("Date of Birth (YYYY-MM-DD)");
        birthLabel.setBounds(50, 330, 280, 50);
        birthLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        profilePanel.add(birthLabel);

        genderLabel = new JLabel("Gender");
        genderLabel.setBounds(50, 390, 250, 50);
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        profilePanel.add(genderLabel);

        addressLabel = new JLabel("Address");
        addressLabel.setBounds(50, 450, 250, 50);
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        profilePanel.add(addressLabel);

        balanceLabel = new JLabel("current Balance");
        balanceLabel.setBounds(50, 590, 250, 50);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        profilePanel.add(balanceLabel);

        savingsBalanceLabel = new JLabel("Savings Balance");
        savingsBalanceLabel.setBounds(50, 520, 250, 50);
        savingsBalanceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        profilePanel.add(savingsBalanceLabel);

        opaningLabel = new JLabel("Opaning Date");
        opaningLabel.setBounds(50, 660, 250, 50);
        opaningLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        profilePanel.add(opaningLabel);

        accountNoText = new JTextField("    " + acc.getAccNo());
        accountNoText.setBounds(310, 40, 400, 50);
        accountNoText.setFont(new Font("Arial", Font.PLAIN, 20));
        accountNoText.setEditable(false); // Initially non-editable
        profilePanel.add(accountNoText);

        mobileNo = new JTextField("    " + acc.getMobile_No());
        mobileNo.setBounds(310, 90, 400, 50);
        mobileNo.setFont(new Font("Arial", Font.PLAIN, 20));
        mobileNo.setEditable(false); // Initially non-editable
        profilePanel.add(mobileNo);

        email = new JTextField("    " + acc.getEmail());
        email.setBounds(310, 150, 400, 50);
        email.setFont(new Font("Arial", Font.PLAIN, 20));
        email.setEditable(false); // Initially non-editable
        profilePanel.add(email);

        firstName = new JTextField("    " + acc.getFirst_Name());
        firstName.setBounds(310, 210, 400, 50);
        firstName.setFont(new Font("Arial", Font.PLAIN, 20));
        firstName.setEditable(false); // Initially non-editable
        profilePanel.add(firstName);

        lastName = new JTextField("    " + acc.getLast_Name());
        lastName.setBounds(310, 270, 400, 50);
        lastName.setFont(new Font("Arial", Font.PLAIN, 20));
        lastName.setEditable(false); // Initially non-editable
        profilePanel.add(lastName);

        birthText = new JTextField("    " + acc.getBirth_Date());
        birthText.setBounds(310, 330, 400, 50);
        birthText.setFont(new Font("Arial", Font.PLAIN, 20));
        birthText.setEditable(false); // Initially non-editable
        profilePanel.add(birthText);

        gender = new JTextField("    " + acc.getGender());
        gender.setBounds(310, 390, 400, 50);
        gender.setFont(new Font("Arial", Font.PLAIN, 20));
        gender.setEditable(false); // Initially non-editable
        profilePanel.add(gender);

        address = new JTextArea("    " + acc.getAddress());
        address.setBounds(310, 450, 400, 50);
        address.setFont(new Font("Arial", Font.PLAIN, 20));
        address.setEditable(false); // Initially non-editable
        profilePanel.add(address);

        savingsBalance = new JTextField("    " + acc.getSavingBalance());
        savingsBalance.setBounds(310, 520, 400, 50);
        savingsBalance.setFont(new Font("Arial", Font.PLAIN, 20));
        savingsBalance.setEditable(false);
        profilePanel.add(savingsBalance);

        balance = new JTextField("    " + acc.getCurrentBalance());
        balance.setBounds(310, 590, 400, 50);
        balance.setFont(new Font("Arial", Font.PLAIN, 20));
        balance.setEditable(false); // Initially non-editable
        profilePanel.add(balance);

        opaningDate = new JTextField("    " + acc.getOpeningDate());
        opaningDate.setBounds(310, 660, 400, 50);
        opaningDate.setFont(new Font("Arial", Font.PLAIN, 20));
        opaningDate.setEditable(false); // Initially non-editable
        profilePanel.add(opaningDate);

        Exit = new JButton("Exit");
        Exit.setBounds(50, 730, 150, 40);
        Exit.setFont(new Font("Arial", Font.PLAIN, 20));
        Exit.setFocusable(false);
        profilePanel.add(Exit);

        edit = new JButton("Edit");
        edit.setBounds(250, 730, 150, 40);
        edit.setFont(new Font("Arial", Font.PLAIN, 20));
        edit.setFocusable(false);
        profilePanel.add(edit);

        delete = new JButton("Delete");
        delete.setBounds(450, 730, 150, 40);
        delete.setFont(new Font("Arial", Font.PLAIN, 20));
        delete.setFocusable(false);
        delete.setBackground(new Color(204, 0, 0));
        delete.setForeground(Color.white);
        profilePanel.add(delete);

        edit.addActionListener(this);
        Exit.addActionListener(this);
        delete.addActionListener(this);

        homPanel.removeAll();
        homPanel.revalidate();
        homPanel.repaint();

        homPanel.add(profilePanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == edit) {
            if (!isEditing) {

                mobileNo.setEditable(true);
                email.setEditable(true);
                firstName.setEditable(true);
                lastName.setEditable(true);
                birthText.setEditable(true);
                gender.setEditable(true);
                address.setEditable(true);
                balance.setEditable(true);

                edit.setText("Save");
                isEditing = true;
            } else {
                // Save the changes
                boolean success = 0 < sql.updateAccount(
                        accountNoText.getText().trim(),
                        mobileNo.getText().trim(),
                        email.getText().trim(),
                        firstName.getText().trim(),
                        lastName.getText().trim(),
                        birthText.getText().trim(),
                        gender.getText().trim(),
                        address.getText().trim(),
                        balance.getText().trim(),
                        savingsBalance.getText().trim());

                if (success) {
                    JOptionPane.showMessageDialog(null, "Account details updated successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    edit.setText("Edit");
                    isEditing = false;

                    // Disable editing again
                    accountNoText.setEditable(false);
                    mobileNo.setEditable(false);
                    email.setEditable(false);
                    firstName.setEditable(false);
                    lastName.setEditable(false);
                    birthText.setEditable(false);
                    gender.setEditable(false);
                    address.setEditable(false);
                    balance.setEditable(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update account details.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == Exit) {
            homPanel.removeAll();
            homPanel.revalidate();
            homPanel.repaint();
            searchPanel();
        } else if (e.getSource() == delete) {
            int result = JOptionPane.showConfirmDialog(null, "Do you want to delete account no: " + accountNo.getText(),
                    "Delete Account", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {

                if (0 < sql.deleteAccount(sql.getInteger(accountNoText.getText().trim()))) {
                    JOptionPane.showMessageDialog(null, "Account deleted successfully", "Delete Account",
                            JOptionPane.PLAIN_MESSAGE);
                    searchPanel();
                } else {
                    JOptionPane.showMessageDialog(null, "DELETION FAILED", "delete", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}