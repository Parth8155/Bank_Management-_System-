package GUI;

import java.util.*;
import javax.swing.*;
import SQL.*;
import bank.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class bankPage implements ActionListener {

    private JFrame frame;
    private JButton dashBoard;
    private JButton transactionHistory;
    private JButton logOut;
    private JButton profileButton;
    private JButton send;
    private JButton accountsButton;

    private JTextArea message;
    private JTextField sendId;
    private JLabel sendResult;
    private JTextField amount;

    private JPanel welcomePanel;
    private JPanel transactionPanel;
    private JPanel accountsPanel;

    private JLayeredPane historyPane;

    JPanel profilePanel;

    private Stack<transaction> stack;

    private Account account;
    private Color topColor = new Color(230, 230, 230); // Light color for the top
    private Color bottomColor = new Color(150, 150, 150); // Dark color for the bottom
    private Color backgroundColor = new Color(200, 200, 200); // Background color of the pan
    private Color darkGreenColor = new Color(20, 52, 29); // Background color of the pan

    public bankPage(String accountNo, Account acc) throws ClassNotFoundException, SQLException {

        this.account = acc;

        stack = new Stack<>();
        storeHistory();
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createBankPage();
    }

    private void storeHistory() {
        ResultSet rs = sql.printTransactionHistory(account.getAccNo());

        try {
            while (rs.next()) {
                int transactionID = rs.getInt("TransactionID");
                String sender = rs.getString("SenderAccountNo");
                String receiver = rs.getString("ReceiverAccountNo");
                double amount = rs.getDouble("Amount");
                String message = rs.getString("Message");
                String date = "" + rs.getTimestamp("TransactionDate");

                transaction t = new transaction(transactionID, Integer.parseInt(sender), Integer.parseInt(receiver),
                        amount, message, date);
                stack.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createBankPage() throws SQLException {
        JPanel panel1 = new JPanel();
        panel1.setBounds(0, 0, 250, 840);
        panel1.setBackground(topColor);
        panel1.setLayout(null);

        ImageIcon image = new ImageIcon("bank.png");
      ImageIcon imageIcon = new ImageIcon("bank.png");
      Image scaledImage = image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

      JLabel bankImage = new JLabel(new ImageIcon(scaledImage));
      bankImage.setBounds(80, 10, 100, 100);
      panel1.add(bankImage);
      bankImage.setVisible(true);

        dashBoard = new JButton(" Dashboard");
        dashBoard.setBounds(50, 150, 150, 80);
        dashBoard.setFont(new Font("Arial", Font.PLAIN, 20));
        dashBoard.setFocusable(false);
        panel1.add(dashBoard);

        transactionHistory = new JButton(" Transaction");
        transactionHistory.setBounds(50, 270, 150, 80);
        transactionHistory.setFont(new Font("Arial", Font.PLAIN, 20));
        transactionHistory.setFocusable(false);
        panel1.add(transactionHistory);

        accountsButton = new JButton(" Accounts ");
        accountsButton.setBounds(50, 390, 150, 80);
        accountsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        accountsButton.setFocusable(false);
        panel1.add(accountsButton);

        profileButton = new JButton(" Profile ");
        profileButton.setBounds(50, 510, 150, 80);
        profileButton.setFont(new Font("Arial", Font.PLAIN, 20));
        profileButton.setFocusable(false);
        panel1.add(profileButton);

        logOut = new JButton(" Log Out");
        logOut.setBounds(50, 600, 150, 80);
        logOut.setFont(new Font("Arial", Font.PLAIN, 20));
        logOut.setFocusable(false);
        panel1.add(logOut);

        setWelcomePanel();
        frame.add(panel1);
        frame.setVisible(true);

        accountsButton.addActionListener(this);
        profileButton.addActionListener(this);
        logOut.addActionListener(this);
        dashBoard.addActionListener(this);
        transactionHistory.addActionListener(this);
    }

    JLabel currentBalancLabel, savingsBalancLabel;

    private void setWelcomePanel() throws SQLException {
        welcomePanel = new JPanel();
        welcomePanel.setBackground(Color.lightGray);
        welcomePanel.setLayout(null);
        welcomePanel.setBounds(250, 0, 1300, 840);

        JLabel welcomeLabel = new JLabel("WELCOME , " + account.getFirst_Name().toUpperCase());
        welcomeLabel.setBounds(50, 20, 800, 100);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 25));
        welcomePanel.add(welcomeLabel);

        JLabel currentAccountLable = new JLabel(" Current Account");
        currentAccountLable.setBounds(50, 160, 300, 75);
        currentAccountLable.setForeground(Color.WHITE);
        currentAccountLable.setFont(new Font("Arial", Font.PLAIN, 20));
        currentAccountLable.setBackground(darkGreenColor);
        currentAccountLable.setOpaque(true);
        welcomePanel.add(currentAccountLable);

        currentBalancLabel = new JLabel(" " + account.getCurrentBalance() + " ₹");
        currentBalancLabel.setBounds(50, 235, 300, 75);
        currentBalancLabel.setForeground(Color.WHITE);
        currentBalancLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        currentBalancLabel.setBackground(darkGreenColor);
        currentBalancLabel.setOpaque(true);
        welcomePanel.add(currentBalancLabel);

        JLabel savingsAccountLabel = new JLabel(" Savings Account ");
        savingsAccountLabel.setBounds(500, 160, 300, 75);
        savingsAccountLabel.setForeground(Color.WHITE);
        savingsAccountLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        savingsAccountLabel.setBackground(darkGreenColor);
        savingsAccountLabel.setOpaque(true);
        welcomePanel.add(savingsAccountLabel);

        savingsBalancLabel = new JLabel(" " + account.getSavingBalance() + " ₹");
        savingsBalancLabel.setBounds(500, 235, 300, 75);
        savingsBalancLabel.setForeground(Color.WHITE);
        savingsBalancLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        savingsBalancLabel.setBackground(darkGreenColor);
        savingsBalancLabel.setOpaque(true);
        welcomePanel.add(savingsBalancLabel);

        JLabel historyLabel = new JLabel(" Recent Transaction ");
        historyLabel.setBounds(50, 300, 300, 100);
        historyLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        welcomePanel.add(historyLabel);

        JLabel sendMoneyJLabel = new JLabel("Send Money ");
        sendMoneyJLabel.setBounds(870, 300, 250, 100);
        sendMoneyJLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        welcomePanel.add(sendMoneyJLabel);

        updateHistory();

        JLayeredPane sendMoneyPane = new JLayeredPane();
        sendMoneyPane.setBounds(860, 380, 370, 390);
        sendMoneyPane.setBackground(bottomColor);
        sendMoneyPane.setOpaque(true);
        sendMoneyPane.setLayout(null);

        
        JLabel sendIdLabel = new JLabel(" Account No");
        sendIdLabel.setBounds(10, 5, 200, 25);
        sendIdLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        sendMoneyPane.add(sendIdLabel);

        sendId = new JTextField();
        sendId.setBounds(10, 30, 330, 40);
        sendId.setBackground(topColor);
        sendId.setFont(new Font("Arial", Font.PLAIN, 20));
        sendMoneyPane.add(sendId);

        JLabel amountJLabel = new JLabel(" Amount");
        amountJLabel.setBounds(10, 80, 200, 25);
        amountJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        sendMoneyPane.add(amountJLabel);

        amount = new JTextField();
        amount.setBounds(10, 115, 330, 40);
        amount.setBackground(topColor);
        amount.setFont(new Font("Arial", Font.PLAIN, 20));
        sendMoneyPane.add(amount);

        JLabel messageLabel = new JLabel(" Message");
        messageLabel.setBounds(10, 160, 200, 40);
        messageLabel.setBackground(topColor);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        sendMoneyPane.add(messageLabel);

        message = new JTextArea(10, 30);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setBounds(10, 200, 330, 80);
        message.setBackground(topColor);
        message.setFont(new Font("Arial", Font.ITALIC, 20));
        sendMoneyPane.add(message);

        send = new JButton(" Send");
        send.setBounds(240, 290, 100, 50);
        send.setFocusable(false);
        sendMoneyPane.add(send);

        sendResult = new JLabel();
        sendResult.setBounds(10, 290, 220, 40);
        sendResult.setBackground(bottomColor);
        sendResult.setFont(new Font("Arial", Font.PLAIN, 20));
        sendMoneyPane.add(sendResult);

        welcomePanel.add(sendMoneyPane);
        frame.add(welcomePanel);

        send.addActionListener(this);
        frame.revalidate();
        frame.repaint();
    }

    private void updateHistory() {

        historyPane = new JLayeredPane();
        historyPane.setBounds(60, 380, 800, 390);
        historyPane.setBackground(bottomColor);
        historyPane.setOpaque(true);

        JLabel names = new JLabel(
                "   TransactionID                Sender Accout          Receiver Account               Amount");
        names.setForeground(Color.white);
        names.setFont(new Font("Arial", Font.PLAIN, 20)); // Font size adjusted for readability
        names.setBounds(20, 10, 760, 50); // Adjust the height and width for better visibility
        names.setBackground(bottomColor);
        names.setOpaque(true);
        historyPane.add(names, JLayeredPane.DEFAULT_LAYER);
        JLabel hLabel;
        int i = 0, H = 70;
        Stack<transaction> tempStack = (Stack<transaction>) stack.clone();

        while (!tempStack.isEmpty() && i != 3) {
            transaction t = tempStack.pop();
            hLabel = new JLabel("        " + t.transactionID + "                                " + t.sender
                    + "                                 " + t.receiver + "                                " + t.amount);
            hLabel.setForeground(Color.white);
            hLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // Font size adjusted for readability
            hLabel.setBounds(20, H, 760, 80); // Adjust the height and width for better visibility
            hLabel.setBackground(darkGreenColor);
            hLabel.setOpaque(true);
            historyPane.add(hLabel, JLayeredPane.DEFAULT_LAYER);
            H += 100; // Adjust the increment to avoid overlap
            i++;
        }

        welcomePanel.add(historyPane);
    }

    private void setTransactionHistory() {

        transactionPanel = new JPanel();
        transactionPanel.setBounds(250, 0, 1300, 840);
        transactionPanel.setBackground(Color.LIGHT_GRAY);
        transactionPanel.setLayout(null);

        JLabel historyLabel = new JLabel("Transaction History");
        historyLabel.setBounds(50, 50, 300, 100);
        historyLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        transactionPanel.add(historyLabel);

        JTextArea historyTextArea = new JTextArea();
        historyTextArea.setEditable(false); // Make the text area read-only
        historyTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
        historyTextArea.setBackground(bottomColor);
        historyTextArea.setForeground(Color.WHITE);

        // Fetch and append the transaction history
        appendTransactionHistory(historyTextArea, account.getAccNo());

        // Wrap JTextArea in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(historyTextArea);
        scrollPane.setBounds(25, 200, 1170, 600);
        scrollPane.setBackground(bottomColor);
        scrollPane.setOpaque(true);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        transactionPanel.add(scrollPane);
        frame.add(transactionPanel);
        frame.revalidate();
        frame.repaint();
    }

    private void appendTransactionHistory(JTextArea textArea, int accountNo) {

        Stack<transaction> tempStack = (Stack<transaction>) stack.clone();

        while (!tempStack.isEmpty()) {
            transaction t = tempStack.pop();
            textArea.append("Transaction id :- " + t.transactionID + "\nReceiver :- " + t.receiver + "\nSender :- "
                    + t.sender + "\nAmount :- " + t.amount + "\nDate :- " + t.date + "\nMessage :- " + t.message
                    + "\n\n");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logOut) {
            int result = JOptionPane.showConfirmDialog(
                    frame, "Do you want to LogOut?", "Confirm", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                frame.dispose();
                new Login();

            }

        } else if (e.getSource() == send) {
            if (!sendId.getText().isEmpty()) {
                double am = Double.parseDouble(amount.getText());
                if (am <= account.getCurrentBalance()) {
                    handleSend();
                } else {
                    JOptionPane.showMessageDialog(null, "INSUFFICIENT BALANCE", "UogOut", JOptionPane.PLAIN_MESSAGE);
                }
            }
        } else if (e.getSource() == dashBoard) {
            try {
                if (transactionPanel != null) {
                    frame.remove(transactionPanel);
                    transactionPanel = null;
                }
                if (accountsPanel != null) {
                    frame.remove(accountsPanel);
                    accountsPanel = null;
                }
                if (profilePanel != null) {
                    frame.remove(profilePanel);
                    profilePanel = null;
                }
                if (welcomePanel == null) {
                    setWelcomePanel();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == transactionHistory) {
            if (welcomePanel != null) {
                frame.remove(welcomePanel);
                welcomePanel = null;
            }
            if (accountsPanel != null) {
                frame.remove(accountsPanel);
                accountsPanel = null;
            }
            if (profilePanel != null) {
                frame.remove(profilePanel);
                profilePanel = null;
            }
            if (transactionPanel == null) {
                setTransactionHistory();
            }
        } else if (e.getSource() == accountsButton) {

            if (welcomePanel != null) {
                frame.remove(welcomePanel);
                welcomePanel = null;
            }
            if (transactionPanel != null) {
                frame.remove(transactionPanel);
                transactionPanel = null;
            }
            if (profilePanel != null) {
                frame.remove(profilePanel);
                profilePanel = null;
            }
            if (accountsPanel == null) {
                setAccountsPanel();
            }
        } else if (e.getSource() == profileButton) {
            if (transactionPanel != null) {
                frame.remove(transactionPanel);
                transactionPanel = null;
            }
            if (accountsPanel != null) {
                frame.remove(accountsPanel);
                accountsPanel = null;
            }
            if (welcomePanel != null) {
                frame.remove(welcomePanel);
                welcomePanel = null;
            }
            if (profilePanel == null) {
                showProfile();
            }
        }
    }

    private void showProfile() {

        profilePanel = new JPanel();
        profilePanel.setBounds(250, 0, 1300, 840);
        profilePanel.setBackground(Color.LIGHT_GRAY);
        profilePanel.setLayout(null);

        JLabel profileLabel = new JLabel("User Profile");
        profileLabel.setBounds(50, 30, 800, 100);
        profileLabel.setFont(new Font("Arial", Font.BOLD, 30));
        profilePanel.add(profileLabel);

        JLabel nameLabel = new JLabel(
                "Name:                      " + account.getFirst_Name() + " " + account.getLast_Name());
        nameLabel.setBounds(100, 150, 800, 50);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        profilePanel.add(nameLabel);

        JLabel accountNoLabel = new JLabel("Account No:                  " + account.getAccNo());
        accountNoLabel.setBounds(100, 220, 800, 50);
        accountNoLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        profilePanel.add(accountNoLabel);

        JLabel emailLabel = new JLabel("Email:                     " + account.getEmail());
        emailLabel.setBounds(100, 290, 800, 50);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        profilePanel.add(emailLabel);

        JLabel phoneLabel = new JLabel("Mobile Number:          " + account.getMobile_No());
        phoneLabel.setBounds(100, 360, 800, 50);
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        profilePanel.add(phoneLabel);

        JLabel balance = new JLabel("savings Balance :        " + account.getSavingBalance());
        balance.setBounds(100, 430, 800, 50);
        balance.setFont(new Font("Arial", Font.PLAIN, 25));
        profilePanel.add(balance);

        JLabel currentbalance = new JLabel("Current Balance :        " + account.getCurrentBalance());
        currentbalance.setBounds(100, 500, 800, 50);
        currentbalance.setFont(new Font("Arial", Font.PLAIN, 25));
        profilePanel.add(currentbalance);

        JLabel gender = new JLabel("Gender:                    " + account.getGender());
        gender.setBounds(100, 570, 800, 50);
        gender.setFont(new Font("Arial", Font.PLAIN, 25));
        profilePanel.add(gender);

        JLabel dob = new JLabel("DOB :                      " + account.getBirth_Date());
        dob.setBounds(100, 640, 800, 50);
        dob.setFont(new Font("Arial", Font.PLAIN, 25));
        profilePanel.add(dob);

        JLabel address = new JLabel("Address :                " + account.getAddress());
        address.setBounds(100, 710, 800, 50);
        address.setFont(new Font("Arial", Font.PLAIN, 25));
        profilePanel.add(address);

        frame.add(profilePanel);
        frame.revalidate();
        frame.repaint();
    }

    private void setAccountsPanel() {
        accountsPanel = new JPanel();
        accountsPanel.setBounds(250, 0, 1300, 840);
        accountsPanel.setBackground(Color.white);
        accountsPanel.setLayout(null);

        // Current Account Section
        JLabel currentAccountJLabel = new JLabel("CURRENT ACCOUNT");
        currentAccountJLabel.setBounds(50, 50, 330, 40);
        currentAccountJLabel.setFont(new Font("Serif", Font.BOLD, 30));
        accountsPanel.add(currentAccountJLabel);

        JLabel moveFundLabel = new JLabel("Move Fund To Saving Account");
        moveFundLabel.setBounds(700, 60, 400, 40);
        moveFundLabel.setFont(new Font("Serif", Font.BOLD, 20));
        accountsPanel.add(moveFundLabel);

        ImageIcon image = new ImageIcon("arrow.png");
        ImageIcon imageIcon = new ImageIcon("arrow.png");
        Image scaledImage = image.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);

        JTextField moveFundTextField = new JTextField();
        moveFundTextField.setBounds(700, 120, 400, 40);
        moveFundTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        accountsPanel.add(moveFundTextField);

        JButton moveFudsButton = new JButton(new ImageIcon(scaledImage));
        moveFudsButton.setBounds(700, 180, 400, 40);
        moveFudsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        accountsPanel.add(moveFudsButton);

        JLabel accountNumberLabel1 = new JLabel("Account Number");
        accountNumberLabel1.setBounds(50, 120, 200, 25);
        accountNumberLabel1.setFont(new Font("Arial", Font.PLAIN, 20));
        accountsPanel.add(accountNumberLabel1);

        JTextField accountNumberField1 = new JTextField("" + account.getAccNo());
        accountNumberField1.setBounds(220, 120, 200, 40);
        accountNumberField1.setFont(new Font("Arial", Font.PLAIN, 20));
        accountNumberField1.setEditable(false); // If this field should be non-editable
        accountsPanel.add(accountNumberField1);

        JLabel currentAmountLabel = new JLabel("Balance");
        currentAmountLabel.setBounds(50, 200, 200, 25); // Fixed bounds
        currentAmountLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        accountsPanel.add(currentAmountLabel);

        JTextField amountField = new JTextField("" + account.getCurrentBalance());
        amountField.setBounds(220, 200, 200, 40);
        amountField.setFont(new Font("Arial", Font.PLAIN, 20));
        amountField.setEditable(false); // If this field should be non-editable
        accountsPanel.add(amountField);

        // Saving Account Section
        JLabel savingAccountJLabel = new JLabel("SAVING ACCOUNT");
        savingAccountJLabel.setBounds(50, 380, 300, 40);
        savingAccountJLabel.setFont(new Font("Serif", Font.BOLD, 30));
        accountsPanel.add(savingAccountJLabel);

        JLabel moveFundLabel2 = new JLabel("Move Fund To current Account");
        moveFundLabel2.setBounds(700, 390, 400, 40);
        moveFundLabel2.setFont(new Font("Serif", Font.BOLD, 20));
        accountsPanel.add(moveFundLabel2);

        ImageIcon image2 = new ImageIcon("arrowUp.png");
        ImageIcon imageIcon2 = new ImageIcon("arrowUp.png");
        Image scaledImage2 = image2.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);

        JTextField moveFundTextField2 = new JTextField();
        moveFundTextField2.setBounds(700, 450, 400, 40);
        moveFundTextField2.setFont(new Font("Arial", Font.PLAIN, 20));
        accountsPanel.add(moveFundTextField2);

        JButton moveFudsButton2 = new JButton(new ImageIcon(scaledImage2));
        moveFudsButton2.setBounds(700, 520, 400, 40);
        moveFudsButton2.setFont(new Font("Arial", Font.PLAIN, 20));
        accountsPanel.add(moveFudsButton2);

        JLabel accountNumberLabel2 = new JLabel("Account Number");
        accountNumberLabel2.setBounds(50, 460, 200, 25);
        accountNumberLabel2.setFont(new Font("Arial", Font.PLAIN, 20));
        accountsPanel.add(accountNumberLabel2);

        JTextField accountNumberField2 = new JTextField("" + account.getAccNo());
        accountNumberField2.setBounds(220, 460, 200, 40);
        accountNumberField2.setFont(new Font("Arial", Font.PLAIN, 20));
        accountNumberField2.setEditable(false); // If this field should be non-editable
        accountsPanel.add(accountNumberField2);

        JLabel savingAmountLabel = new JLabel("Balance");
        savingAmountLabel.setBounds(50, 540, 200, 25);
        savingAmountLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        accountsPanel.add(savingAmountLabel);

        JTextField savingAmountField = new JTextField("" + account.getSavingBalance());
        savingAmountField.setBounds(220, 540, 200, 40);
        savingAmountField.setFont(new Font("Arial", Font.PLAIN, 20));
        savingAmountField.setEditable(false); // If this field should be non-editable
        accountsPanel.add(savingAmountField);

        // Add the accountsPanel to the frame
        moveFudsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!moveFundTextField.getText().isEmpty()) {
                    double bal = Double.parseDouble(moveFundTextField.getText());
                    if (account.balance >= bal) {
                        sql.upadateCurrentToSaving(account.getAccNo(), moveFundTextField.getText());
                        account.balance -= bal;
                        account.savingsBalance += bal;
                        accountsButton.doClick();
                        amountField.setText("" + account.getCurrentBalance());
                        savingAmountField.setText("" + account.getSavingBalance());
                        JOptionPane.showMessageDialog(null, "TRANSFER SUCCESSFUL", "UogOut", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "INSUFFICIENT BALANCE", "UogOut",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });

        moveFudsButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!moveFundTextField2.getText().isEmpty()) {

                    double bal = Double.parseDouble(moveFundTextField2.getText());
                    if (account.savingsBalance >= bal) {
                        sql.upadateSavingToCurrent(account.getAccNo(), moveFundTextField2.getText());
                        account.balance += bal;
                        account.savingsBalance -= bal;
                        amountField.setText("" + account.getCurrentBalance());
                        savingAmountField.setText("" + account.getSavingBalance());
                        JOptionPane.showMessageDialog(null, "TRANSFER SUCCESSFUL", "UogOut", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "INSUFFICIENT BALANCE", "UogOut",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });

        frame.add(accountsPanel);
        frame.revalidate();
        frame.repaint();
    }

    private void handleSend() {
        if (sql.correctId(Integer.parseInt(sendId.getText()))) {

            double amount_ = Double.parseDouble(amount.getText());

            ResultSet rst = sql.sendMoney(Integer.parseInt(sendId.getText()), Double.parseDouble(amount.getText()),
                    account.getAccNo(),
                    message.getText());
            try {
                while (rst.next()) {
                    stack.push(new transaction(rst.getInt(1), account.getAccNo(), Integer.parseInt(sendId.getText()),
                            Double.parseDouble(amount.getText()), message.getText(), rst.getTimestamp(2).toString()));

                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            welcomePanel.remove(historyPane);
            updateCurrentBalanceLable();
            historyPane.removeAll();
            historyPane.repaint();
            updateHistory();

            sendId.setForeground(Color.green);
            sendId.setText("Transaction successful");

        } else {
            sendId.setForeground(Color.RED);
            sendId.setText("Wrong Account Number");
        }
    }

    private void updateCurrentBalanceLable() {

        account.balance = account.getCurrentBalance() - Double.parseDouble(amount.getText());
        currentBalancLabel.repaint();
        currentBalancLabel.setText("" + account.getCurrentBalance());

    }
}
