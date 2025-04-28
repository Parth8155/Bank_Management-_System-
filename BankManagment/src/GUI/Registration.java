package GUI;

import SQL.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Registration implements ActionListener {

   int sendAccountNo;
   JFrame frame;
   JTextField accountNo;
   JTextField mobileNo;
   JTextField email;
   JTextField password;
   JButton save;
   JButton Exit;
   JLabel messageTextField;

   public Registration() throws ClassNotFoundException, SQLException {
      frame = new JFrame();
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      frame.setLayout(null);
      frame.getContentPane().setBackground(Color.gray);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setPanel();

   }

   public void setPanel() {
      JPanel panel = new JPanel();
      panel.setBounds(15, 15, 1500, 765);
      Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 10);
      panel.setLayout((LayoutManager) null);
      panel.setBorder(border);
      panel.setBackground(Color.LIGHT_GRAY);
      JLabel accountNoLabel = new JLabel(" Account No ");
      accountNoLabel.setBounds(300, 200, 250, 50);
      accountNoLabel.setFont(new Font("Arial", 0, 20));
      accountNoLabel.setForeground(Color.BLACK);
      panel.add(accountNoLabel);
      JLabel mobileNoLabel = new JLabel(" Registered Mobile No ");
      mobileNoLabel.setBounds(300, 260, 250, 50);
      mobileNoLabel.setFont(new Font("Arial", 0, 20));
      mobileNoLabel.setForeground(Color.BLACK);
      panel.add(mobileNoLabel);
      JLabel emaiLabel = new JLabel(" Email ID");
      emaiLabel.setBounds(300, 320, 250, 50);
      emaiLabel.setFont(new Font("Arial", 0, 20));
      panel.add(emaiLabel);

      JLabel passwordJLabel = new JLabel(" Password ");
      passwordJLabel.setBounds(300, 380, 250, 50);
      passwordJLabel.setFont(new Font("Arial", 0, 20));
      panel.add(passwordJLabel);

      accountNo = new JTextField();
      accountNo.setBounds(560, 200, 400, 50);
      accountNo.setFont(new Font("Arial", 0, 20));
      panel.add(accountNo);
      mobileNo = new JTextField();
      mobileNo.setBounds(560, 260, 400, 50);
      mobileNo.setFont(new Font("Arial", 0, 20));
      panel.add(mobileNo);

      email = new JTextField();
      email.setBounds(560, 320, 400, 50);
      email.setFont(new Font("Arial", 0, 20));
      panel.add(email);

      password = new JTextField();
      password.setBounds(560, 380, 250, 50);
      password.setFont(new Font("Arial", 0, 20));
      panel.add(password);

      save = new JButton("Save");
      save.setBounds(700, 500, 100, 50);
      save.setFont(new Font("Arial", 0, 20));
      save.setFocusable(false);
      panel.add(save);

      Exit = new JButton("Exit");
      Exit.setBounds(400, 500, 100, 50);
      Exit.setFont(new Font("Arial", 0, 20));
      Exit.setFocusable(false);
      panel.add(Exit);

      messageTextField = new JLabel("ENTER ALL DATA");
      messageTextField.setForeground(Color.red);
      messageTextField.setBackground(Color.LIGHT_GRAY);
      messageTextField.setBounds(700, 450, 300, 50);
      messageTextField.setFont(new Font("Arial", 0, 20));
      messageTextField.setVisible(false);
      panel.add(messageTextField);

      save.addActionListener(this);
      Exit.addActionListener(this);
      frame.add(panel);
      frame.setVisible(true);
   }

   public void actionPerformed(ActionEvent e) {

      if (e.getSource() == save) {

         if (sql.isInteger(accountNo.getText())) {
            sendAccountNo = sql.getInteger(accountNo.getText());
            if (accountNo.getText().isEmpty() || mobileNo.getText().isEmpty() || email.getText().isEmpty()
                  || password.getText().isEmpty()) {
               messageTextField.setVisible(true);
            } else if (sql.checkRegistration(sendAccountNo)) {
               JOptionPane.showMessageDialog(null, "YOU ARE  ALREADY REGISTRED ", "REGISTRATION",
                     JOptionPane.PLAIN_MESSAGE);

            } else if (sql.correctId(sendAccountNo) && sql.correctPhoneNo(mobileNo.getText())) {
               int n = sql.registrationSave(sendAccountNo, mobileNo.getText(), email.getText(), password.getText());

               if (n > 0) {
                  JOptionPane.showMessageDialog(null, "YOUR REGISTRATION SUCCESSFUL ", "REGISTRATION",
                        JOptionPane.PLAIN_MESSAGE);
                  frame.dispose();
                  new Login();
               } else {
                  JOptionPane.showMessageDialog(null, "YOUR REGISTRATION FAILED ", "REGISTRATION",
                        JOptionPane.PLAIN_MESSAGE);
               }
            } else {
               JOptionPane.showMessageDialog(null, "WRONG ACCOUNT NUMBER OR PHONE NUMBER", "UogOut",
                     JOptionPane.PLAIN_MESSAGE);
            }
         } else {
            JOptionPane.showMessageDialog(null, "ENTER INTEGER ACCOUNT NUMBER", "Delete", JOptionPane.PLAIN_MESSAGE);
         }
      } else if (e.getSource() == Exit) {

         new Login();
         frame.dispose();
      }

   }
}
