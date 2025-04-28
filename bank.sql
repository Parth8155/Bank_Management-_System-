-- Create the database if it does not exist and use it
CREATE DATABASE IF NOT EXISTS bank;
USE bank;

-- Set session variables as in the original dump
SET SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO';
SET time_zone = '+00:00';

-- Set character set
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 /*!40101 SET NAMES utf8mb4 */;

-- ================================
-- DROP & CREATE Procedures
-- ================================

-- Drop procedure if it exists
DROP PROCEDURE IF EXISTS addDataCustomer;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addDataCustomer` (
    IN first_Name VARCHAR(10),
    IN last_Name VARCHAR(100),
    IN email VARCHAR(50),
    IN mobileNo VARCHAR(10),
    IN date_Of_Birth DATE,
    IN gender VARCHAR(10),
    IN address VARCHAR(100),
    OUT id INT
)
BEGIN
    -- Insert data into customer table
    INSERT INTO customer (first_Name, last_Name, email, mobile_No, date_Of_Birth, gender, address)
    VALUES (first_Name, last_Name, email, mobileNo, date_Of_Birth, gender, address);
    
    -- Retrieve the last inserted id and assign it to the OUT parameter
    SET id = LAST_INSERT_ID();
END$$

DROP PROCEDURE IF EXISTS transfeCurrentToSavings;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `transfeCurrentToSavings` (
    IN accNo INT,
    IN bal DECIMAL(20,2)
)
BEGIN
    -- Subtract from current balance
    UPDATE account_database 
    SET balance = balance - bal 
    WHERE account_No = accNo;

    -- Add to savingsBalance
    UPDATE account_database 
    SET savingsBalance = savingsBalance + bal 
    WHERE account_No = accNo;
END$$
DELIMITER ;



DROP PROCEDURE IF EXISTS transfeSavingsToCurrent;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `transfeSavingsToCurrent` (
    IN accNo INT,
    IN bal DECIMAL(20,2)
)
BEGIN
    -- Add to current balance
    UPDATE account_database 
    SET balance = balance + bal 
    WHERE account_No = accNo;

    -- Subtract from savingsBalance
    UPDATE account_database 
    SET savingsBalance = savingsBalance - bal 
    WHERE account_No = accNo;
END$$
DELIMITER ;

-- ================================
-- CREATE Tables and Insert Data
-- ================================

-- account_database table
CREATE TABLE IF NOT EXISTS account_database (
  account_No INT(11) NOT NULL AUTO_INCREMENT,
  customer_Id INT(10) NOT NULL,
  mobile_No VARCHAR(10) NOT NULL,
  email VARCHAR(25) NOT NULL,
  balance DOUBLE(20,2) NOT NULL,
  savingsBalance DOUBLE(20,2) NOT NULL,
  opaning_Date DATE NOT NULL,
  PRIMARY KEY (account_No)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Insert data into account_database table
INSERT IGNORE INTO account_database (account_No, customer_Id, mobile_No, email, balance, savingsBalance, opaning_Date) VALUES
(123456789, 1001, '', '', 0.00, 0.00, '0000-00-00'),
(123456790, 1002, '9173814394', 'parth@gmail.com', 50000.00, 100000.00, '2024-08-24'),
(123456791, 1003, '8155801865', 'kunal@gmail.com', 25000.00, 90000.00, '2024-08-24'),
(123456792, 1004, '8490067670', 'jay@gmail.com', 60000.00, 28000.00, '2024-08-24'),
(123456793, 1005, '1213141516', 'kunal@gmail.com', 80000.00, 10000.00, '2024-08-24');

-- admin table
CREATE TABLE IF NOT EXISTS admin (
  id INT(10) NOT NULL,
  pass VARCHAR(25) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT IGNORE INTO admin (id, pass) VALUES
(10001, 'admin10001');

-- branch table
CREATE TABLE IF NOT EXISTS branch (
  branch_Id INT(10) NOT NULL AUTO_INCREMENT,
  branch_Name VARCHAR(50) NOT NULL,
  location VARCHAR(100) NOT NULL,
  PRIMARY KEY (branch_Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- customer table
CREATE TABLE IF NOT EXISTS customer (
  customer_Id INT(10) NOT NULL AUTO_INCREMENT,
  first_Name VARCHAR(50) NOT NULL,
  last_Name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  mobile_No VARCHAR(10) NOT NULL,
  date_Of_Birth DATE NOT NULL,
  gender VARCHAR(10) NOT NULL,
  address VARCHAR(100) NOT NULL,
  PRIMARY KEY (customer_Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT IGNORE INTO customer (customer_Id, first_Name, last_Name, email, mobile_No, date_Of_Birth, gender, address) VALUES
(1001, '', '', '', '', '0000-00-00', '', ''),
(1002, 'parth', 'maheriya', 'parth@gmail.com', '9173814394', '2006-04-13', 'Male', 'saijpur gopalpur'),
(1003, 'kunal', 'prajapati', 'kunal@gmail.com', '8155801865', '2005-05-14', 'Male', 'sanand'),
(1004, 'jay', 'panchal', 'jay@gmail.com', '8490067670', '2003-12-23', 'Male', 'gota'),
(1005, 'kunal', 'fauzdar', 'kunal@gmail.com', '1213141516', '2003-02-12', 'Male', 'chandkheda');

-- login_registration table
CREATE TABLE IF NOT EXISTS login_registration (
  account_No INT(10) NOT NULL AUTO_INCREMENT,
  mobile_No VARCHAR(10) NOT NULL,
  email VARCHAR(25) NOT NULL,
  password VARCHAR(25) NOT NULL,
  PRIMARY KEY (account_No),
  UNIQUE KEY mobile_No (mobile_No)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT IGNORE INTO login_registration (account_No, mobile_No, email, password) VALUES
(123456790, '9173814394', 'abc.com', '123456790'),
(123456791, '8155801865', 'abc.com', '123456791'),
(123456792, '8490067670', 'abc.com', '123456792');

-- transactionhistory table
CREATE TABLE IF NOT EXISTS transactionhistory (
  transactionID INT(100) NOT NULL AUTO_INCREMENT,
  senderAccountNo INT(10) NOT NULL,
  receiverAccountNo INT(10) NOT NULL,
  amount DOUBLE(20,2) NOT NULL,
  message LONGTEXT NOT NULL,
  transactionDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (transactionID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ================================
-- DROP & CREATE Triggers
-- ================================

-- Trigger for account_database deletion
DROP TRIGGER IF EXISTS trg_delete_account;
DELIMITER $$
CREATE TRIGGER trg_delete_account
AFTER DELETE ON account_database
FOR EACH ROW
BEGIN
    DELETE FROM login_registration
    WHERE account_No = OLD.account_No;
END$$
DELIMITER ;

-- Trigger for transactionhistory insertions
DROP TRIGGER IF EXISTS trg_update_balance_after_transaction;
DELIMITER $$
CREATE TRIGGER trg_update_balance_after_transaction
AFTER INSERT ON transactionhistory
FOR EACH ROW
BEGIN
    -- Update sender's account balance
    UPDATE account_database
    SET balance = balance - NEW.amount
    WHERE account_No = NEW.senderAccountNo;

    -- Update receiver's account balance
    UPDATE account_database
    SET balance = balance + NEW.amount
    WHERE account_No = NEW.receiverAccountNo;
END$$
DELIMITER ;

-- ================================
-- Adjust AUTO_INCREMENT values
-- ================================

ALTER TABLE account_database AUTO_INCREMENT = 123456794;
ALTER TABLE branch AUTO_INCREMENT = 1; -- Change as needed
ALTER TABLE customer AUTO_INCREMENT = 1006;
ALTER TABLE login_registration AUTO_INCREMENT = 123456793;
ALTER TABLE transactionhistory AUTO_INCREMENT = 1028;

-- Commit the transaction if using one
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
