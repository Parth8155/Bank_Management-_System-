package SQL;

import java.io.BufferedReader;
import java.sql.*;
import GUI.bankPage;
import bank.*;

import java.sql.*;

class getConnection {

    public static Connection con;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String pass = "";
        con = DriverManager.getConnection(URL, user, pass);
        if (con != null) {
            System.out.println("Connection succesfull");
        } else {
            System.out.println("Connection Failed");
        }
        return con;
    }
}

public class sql {

    public static Statement st;
    public static CallableStatement cst;
    public static ResultSet rst;
    public static String sql;
    public static Connection con;
    public static PreparedStatement pst;

    public sql() {
        try {
            con = getConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isInteger(String id) {
        boolean ans = false;
        try {
            Integer.parseInt(id);
            ans = true;
        } catch (Exception e) {
            ans = false;
        }
        return ans;
    }

    public static int getInteger(String id) {
        return Integer.parseInt(id);
    }

    public static boolean correctIdPass(int id, String pass) {
        boolean result = false;
        try {
            sql = "SELECT COUNT(*) FROM login_registration WHERE account_No = ? AND password = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.setString(2, pass);
            rst = pst.executeQuery();

            while (rst.next()) {
                result = rst.getInt(1) > 0;
            }

        } catch (SQLException var6) {
            var6.printStackTrace();
        }
        return result;
    }

    public static boolean correctId(int id) {
        boolean result = false;
        try {
            sql = "SELECT COUNT(*) FROM Account_Database WHERE account_No = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                result = rst.getInt(1) > 0;
            }

        } catch (SQLException var6) {
            var6.printStackTrace();
        }
        return result;
    }

    public static boolean correctPhoneNo(String mobile_No) {
        boolean result = false;
        try {
            sql = "SELECT COUNT(*) FROM  Account_Database WHERE mobile_NO = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, mobile_No);
            rst = pst.executeQuery();

            while (rst.next()) {
                result = rst.getInt(1) > 0;
            }

        } catch (SQLException var6) {
            var6.printStackTrace();
        }
        return result;
    }

    public static boolean correctIdPassAdmin(int id, String pass) {

        boolean result = false;
        try {
            sql = "SELECT COUNT(*) FROM admin WHERE id = ? AND pass= ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.setString(2, pass);
            rst = pst.executeQuery();

            while (rst.next()) {
                result = rst.getInt(1) > 0;
            }

        } catch (SQLException var6) {
            var6.printStackTrace();
        }
        return result;

    }

    public static int registrationSave(int account_No, String mobile_No, String email, String pass) {
        sql = "insert into login_registration (account_No,mobile_No,email,Password)values (?,?,?,?)";

        int n = 0;
        try {
            pst = con.prepareCall(sql);
            pst.setInt(1, account_No);
            pst.setString(2, mobile_No);
            pst.setString(3, email);
            pst.setString(4, pass);
            n = pst.executeUpdate();
        } catch (SQLException var6) {
            System.out.println(var6);
        }
        return n;
    }

    static String mobile_No, email, first_Name, last_Name, gender, address, opaning_Date, date_Of_Birth;
    static double balance, savingBalance;
    static int account_No;

    public static Account getAllData(int accountNo) {
        Account acc = null;
        String sql = "select * from Account_Database INNER Join customer  on Account_Database.customer_Id=customer.customer_Id  where account_No = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, accountNo);
            rst = pst.executeQuery();

            while (rst.next()) {

                acc = new Account(rst.getInt("account_No"), rst.getString("mobile_No"), rst.getString("email"),
                        rst.getString("first_Name"), rst.getString("last_Name"), rst.getString("gender"),
                        rst.getString("address"), rst.getDouble("balance"), rst.getDouble("savingsBalance"),
                        rst.getString("opaning_Date"), rst.getString("date_Of_Birth"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return acc;
    }

    public static ResultSet sendMoney(int receiverAccountNo, double amount, int senderAccountNo, String messageText) {

        try {

            sql = "INSERT INTO transactionHistory  (senderAccountNo, receiverAccountNo, amount, message, transactionDate)VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, senderAccountNo);
            pst.setInt(2, receiverAccountNo);
            pst.setDouble(3, amount);
            pst.setString(4, messageText);
            pst.executeUpdate();

            sql = "select transactionID,transactionDate from  transactionHistory  where senderAccountNo=? AND receiverAccountNo=? AND amount=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, senderAccountNo);
            pst.setInt(2, receiverAccountNo);
            pst.setDouble(3, amount);
            rst = pst.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
        }
        return rst;

    }

    public static int addAccount(String mobile_No, String email, String first_Name, String last_Name, String gender,
            String address, String balance, String SavingBalance, String date_Of_Birth) {
        int acc = 0;
        try {
            // sql=" INSERT INTO customer(first_Name, last_Name,email, `mobile_No`,
            // `date_Of_Birth`, `gender`, `address`) VALUES "

            sql = "call addDataCustomer(?,?,?,?,?,?,?,?)";
            CallableStatement cst = con.prepareCall(sql);
            cst.setString(1, first_Name);
            cst.setString(2, last_Name);
            cst.setString(3, email);
            cst.setString(4, mobile_No);
            cst.setString(5, date_Of_Birth);
            cst.setString(6, gender);
            cst.setString(7, address);
            cst.executeQuery();
            int customer_Id = cst.getInt(8);

            sql = "insert into Account_Database (customer_Id,mobile_No, email, balance , savingsBalance ,opaning_Date) values (?,?,?,?,?,CURRENT_DATE)";

            pst = con.prepareStatement(sql);
            pst.setInt(1, customer_Id);
            pst.setString(2, mobile_No);
            pst.setString(3, email);
            pst.setDouble(4, Double.parseDouble(balance));
            pst.setDouble(5, Double.parseDouble(SavingBalance));
            pst.execute();

            sql = "select account_No from Account_Database where mobile_No=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, mobile_No);
            rst = pst.executeQuery();

            while (rst.next()) {
                acc = rst.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return acc;
    }

    public static int deleteAccount(int accountNo) {
        int n = 0;
        sql = " delete from Account_Database where account_No= ?";
        try {
            pst = con.prepareStatement(sql);

            pst.setInt(1, accountNo);

            n = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    public static ResultSet printTransactionHistory(int accountNo) {
        ResultSet rs = null;
        sql = "SELECT transactionID, senderAccountNo, receiverAccountNo, amount, message, transactionDate FROM transactionHistory WHERE senderAccountNo = ? OR receiverAccountNo = ? ORDER BY TransactionDate ASC";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, accountNo);
            pst.setInt(2, accountNo);

            rs = pst.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static double getBalance(int accNo) {

        double balance = 0;

        sql = "select balance from Account_Database where account_No=?";
        try {

            pst = con.prepareStatement(sql);
            pst.setInt(1, accNo);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {

                balance = rst.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }
    
    public static int updateAccount(String accountNo, String mobileNoText, String emailText, String firstNameText,
            String lastNameText, String birthTextField, String gender, String addressText, String balanceText,
            String savingBalanceText) {

        int n = 0;

        sql = "UPDATE account_database SET mobile_No=?,email=?,balance=?,savingsBalance=? WHERE account_No=? ";
        try {
            pst = con.prepareStatement(sql);

            pst.setString(1, mobileNoText);
            pst.setString(2, emailText);
            pst.setDouble(3, Double.parseDouble(balanceText));
            pst.setDouble(4, Double.parseDouble(savingBalanceText));
            pst.setInt(5, getInteger(accountNo));

            n = pst.executeUpdate();

            sql = "UPDATE customer SET first_Name=?,last_Name=?,email=?,mobile_No=?,date_Of_Birth=?,gender=?,address=?WHERE customer_Id In (select customer_Id from account_database where account_No=?)";
            pst = con.prepareStatement(sql);

            pst.setString(1, firstNameText);
            pst.setString(2, lastNameText);
            pst.setString(3, emailText);
            pst.setString(4, mobileNoText);
            pst.setString(5, date_Of_Birth);
            pst.setString(6, gender);
            pst.setString(7, addressText);
            pst.setInt(8, getInteger(accountNo));
            n = pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;

    }

    public static boolean checkRegistration(int sendAccountNo) {
        int n = 0;
        sql = " select count(*) from login_registration  where account_No= ?";
        try {
            pst = con.prepareStatement(sql);

            pst.setInt(1, sendAccountNo);

            rst = pst.executeQuery();
            while (rst.next()) {
                n = rst.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static void upadateCurrentToSaving(int accountNo, String amountText) {

        double amount = Double.parseDouble(amountText);
        int accNo = accountNo;

        sql = "{call transfeCurrentToSavings(?,?)}";

        try {
            cst = con.prepareCall(sql);
            cst.setInt(1, accNo);
            cst.setDouble(2, amount);
            cst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void upadateSavingToCurrent(int accountNo, String amountText) {

        double amount = Double.parseDouble(amountText);
        int accNo = accountNo;
        sql = "{call transfeSavingsToCurrent(?,?)}";

        try {
            cst = con.prepareCall(sql);
            cst.setInt(1, accNo);
            cst.setDouble(2, amount);
            cst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
