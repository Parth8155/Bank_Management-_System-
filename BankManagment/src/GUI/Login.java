package GUI;

import SQL.*;
import bank.Account;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;

public class Login implements ActionListener {

   int sendAccountNo;
   JFrame homePage = new JFrame();
   JComboBox comboLogin;
   JPanel homePageL1;
   JTextField text;
   JTextField textPass;
   JLabel textLable;
   JLabel passLable;
   JLabel messageLabel;
   JButton login;
   JButton register;
   Account acc;

   public Login() {
      homePage.setExtendedState(JFrame.MAXIMIZED_BOTH);
      homePage.setLayout(null);
      HomePage();
   }

   public void HomePage() {
      messageLabel = new JLabel("ENTER ALL DATA");
      messageLabel.setBounds(770, 240, 250, 50);
      messageLabel.setFont(new Font("Arial", 0, 20));
      messageLabel.setForeground(Color.red);
      messageLabel.setVisible(false);
      homePage.add(messageLabel);

      String[] select = new String[] { " Select", "  Admin Login", "  User Login" };
      comboLogin = new JComboBox(select);
      comboLogin.setBounds(770, 300, 250, 50);
      comboLogin.setFont(new Font("Arial", 0, 20));

      homePageL1 = new JPanel();
      homePageL1.setBounds(0, 0, 340, 800);

      ImageIcon image = new ImageIcon("bank.png");
      ImageIcon imageIcon = new ImageIcon("bank.png");

      homePageL1.setLayout(null);
      Image scaledImage = image.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);

      JLabel bankImage = new JLabel(new ImageIcon(scaledImage));
      bankImage.setBounds(25, 210, 300, 300);
      homePageL1.add(bankImage);
      bankImage.setVisible(true);

      text = new JTextField();
      text.setBounds(770, 360, 250, 40);
      text.setFont(new Font("Arial", 0, 20));

      textPass = new JTextField();
      textPass.setBounds(770, 410, 250, 40);
      textPass.setFont(new Font("Arial", 0, 20));

      text.setEnabled(false);
      textPass.setEnabled(false);

      textLable = new JLabel(" User name ");
      textLable.setBounds(650, 360, 150, 40);
      textLable.setFont(new Font("Arial", 0, 20));
      textLable.setForeground(Color.WHITE);

      passLable = new JLabel(" Password ");
      passLable.setBounds(650, 410, 150, 40);
      passLable.setFont(new Font("Arial", 0, 20));
      passLable.setForeground(Color.WHITE);

      login = new JButton("Login");
      login.setBounds(840, 490, 100, 40);
      login.setFont(new Font("Arial", 0, 20));
      login.setFocusable(false);

      register = new JButton("New Registration");
      register.setBounds(795, 550, 200, 40);
      register.setFont(new Font("Arial", 0, 20));
      register.setFocusable(false);

      homePage.add(textLable);
      homePage.add(register);
      homePage.add(login);
      homePage.add(passLable);
      homePage.add(text);
      homePage.add(textPass);
      homePage.add(homePageL1);
      homePage.add(comboLogin);
      homePage.setLayout(null);

      homePage.getContentPane().setBackground(Color.DARK_GRAY);
      homePage.setDefaultCloseOperation(3);
      comboLogin.addActionListener(this);
      register.addActionListener(this);
      login.addActionListener(this);
      homePage.setVisible(true);
   }

   public void actionPerformed(ActionEvent e) {
      String selected = (String) comboLogin.getSelectedItem();
      if (" Select".equals(selected)) {
         text.setEnabled(false);
      } else if ("  Admin Login".equals(selected)) {
         textLable.setText(" Admin ID");
         text.setEnabled(true);
         textPass.setEnabled(true);
      } else if ("  User Login".equals(selected)) {
         textLable.setText(" Acoount No");
         text.setEnabled(true);
         textPass.setEnabled(true);
      }

      if (e.getSource() == register) {
         try {
            new Registration();
         } catch (ClassNotFoundException var5) {
            var5.printStackTrace();
         } catch (SQLException var6) {
            var6.printStackTrace();
         }
         homePage.dispose();
      }

      if (e.getSource() == login) {

         if ("  User Login".equals(selected)) {
            if (text.getText().isEmpty() || textPass.getText().isEmpty()) {
               messageLabel.setVisible(true);
            } else {
               if (!sql.isInteger(text.getText())) {
                  JOptionPane.showMessageDialog(null, "ENTER ONLY INTEGER ACCOUNT NUMBER", "UogOut",
                        JOptionPane.PLAIN_MESSAGE);
               } else {
                  sendAccountNo = sql.getInteger(text.getText());
                  if (!sql.correctIdPass(sendAccountNo, textPass.getText())) {
                     text.setText("");
                     textPass.setText("");
                     JOptionPane.showMessageDialog(null, "WRONG ID OR PASSWORD", "UogOut", JOptionPane.PLAIN_MESSAGE);

                  } else {
                     homePage.dispose();
                     try {
                        acc = sql.getAllData(sendAccountNo);
                        bankPage bk = new bankPage(text.getText(), acc);

                     } catch (ClassNotFoundException | SQLException e1) {
                        e1.printStackTrace();
                     }
                  }
               }
            }
         }

         else if ("  Admin Login".equals(selected)) {
            if (!sql.isInteger(text.getText())) {
               JOptionPane.showMessageDialog(null, "ENTER ONLY INTEGER IN USER ID", "UogOut",
                     JOptionPane.PLAIN_MESSAGE);

            }
            {
               sendAccountNo = sql.getInteger(text.getText());
               if (!sql.correctIdPassAdmin(sendAccountNo, textPass.getText())) {
                  text.setText("");
                  textPass.setText("");
                  JOptionPane.showMessageDialog(null, "WRONG ID OR PASSWORD", "UogOut", JOptionPane.PLAIN_MESSAGE);
               } else {
                  homePage.dispose();
                  new Admin();
               }
            }

         }
      }

   }
}
