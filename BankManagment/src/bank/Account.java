package bank;
import SQL.*;
public class Account {

    public static String
            mobile_No,
            email,
            first_Name,
            last_Name,
            birth_Date,opening_Date,pass,
            gender,address;
   public static Double balance,savingsBalance;
   public int accNo;

    public Account()
    {
        
    }
    
    public Account(int accNo,
            String mobile_No,
            String email,
            String first_Name,
            String last_Name,
            String gender,
            String address,
            Double balance,
            Double savingsBalance,
            String opening_Date,
            String birth_Date
            
           ) {

        this.accNo = accNo;
        this.mobile_No = mobile_No;
        this.email = email;
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.birth_Date = birth_Date;
        this.opening_Date = opening_Date;
        this.address=address;
        this.balance=balance;
        this.savingsBalance=savingsBalance;
        this.gender = gender;

    }

    public int getAccNo() {
        return accNo;
    }

    public String getMobile_No() {
        return mobile_No;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public  String getFirst_Name() {
        return first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public String getBirth_Date() {
        return birth_Date;
    }

    public String getPass() {
        return pass;
    }

    public String getGender() {
        return gender;
    }

    public  Double getSavingBalance() {
        return savingsBalance;
    } 
    public  Double getCurrentBalance() {
        return balance;
    } 
    public  String getOpeningDate() {
        return opening_Date;
    } 

}
