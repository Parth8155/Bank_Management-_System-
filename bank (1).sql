-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 24, 2024 at 06:37 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `addDataCustomer` (IN `first_Name` VARCHAR(10), IN `last_Name` VARCHAR(100), IN `email` VARCHAR(50), IN `mobileNo` VARCHAR(10), IN `date_Of_Birth` DATE, IN `gender` VARCHAR(10), IN `address` VARCHAR(100), OUT `id` INT(10))   BEGIN
    -- Insert data into customer table
    INSERT INTO customer (first_Name, last_Name,email, mobile_No, date_Of_Birth, gender, address)
    VALUES (first_Name, last_Name,email, mobileNo, date_Of_Birth, gender, address);
    
    -- Retrieve the last inserted id and assign it to the OUT parameter
    SELECT LAST_INSERT_ID() INTO id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `transfeCurrentToSavings` (IN `accNo` INT, IN `bal` DECIMAL(20,2))   BEGIN
    -- Subtract from balance
    UPDATE account_database 
    SET balance = balance - bal 
    WHERE account_No = accNo;

    -- Add to savingsBalance
    UPDATE account_database 
    SET savingsBalance = savingsBalance + bal 
    WHERE account_No = accNo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `transfeSavingsToCurrent` (IN `accNo` INT, IN `bal` DECIMAL(20,2))   BEGIN
    -- Subtract from balance
    UPDATE account_database 
    SET balance = balance + bal 
    WHERE account_No = accNo;

    -- Add to savingsBalance
    UPDATE account_database 
    SET savingsBalance = savingsBalance - bal 
    WHERE account_No = accNo;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `account_database`
--

CREATE TABLE `account_database` (
  `account_No` int(11) NOT NULL,
  `customer_Id` int(10) NOT NULL,
  `mobile_No` varchar(10) NOT NULL,
  `email` varchar(25) NOT NULL,
  `balance` double(20,2) NOT NULL,
  `savingsBalance` double(20,2) NOT NULL,
  `opaning_Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account_database`
--

INSERT INTO `account_database` (`account_No`, `customer_Id`, `mobile_No`, `email`, `balance`, `savingsBalance`, `opaning_Date`) VALUES
(123456789, 1001, '', '', 0.00, 0.00, '0000-00-00'),
(123456790, 1002, '9173814394', 'parth@gmail.com', 50000.00, 100000.00, '2024-08-24'),
(123456791, 1003, '8155801865', 'kunal@gmail.com', 25000.00, 90000.00, '2024-08-24'),
(123456792, 1004, '8490067670', 'jay@gmail.com', 60000.00, 28000.00, '2024-08-24'),
(123456793, 1005, '1213141516', 'kunal@gmail.com', 80000.00, 10000.00, '2024-08-24');

--
-- Triggers `account_database`
--
DELIMITER $$
CREATE TRIGGER `trg_delete_account` AFTER DELETE ON `account_database` FOR EACH ROW BEGIN
    DELETE FROM login_registration
    WHERE account_No = OLD.account_No;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(10) NOT NULL,
  `pass` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `pass`) VALUES
(10001, 'admin10001');

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `branch_Id` int(10) NOT NULL,
  `branch_Name` varchar(50) NOT NULL,
  `location` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_Id` int(10) NOT NULL,
  `first_Name` varchar(50) NOT NULL,
  `last_Name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `mobile_No` varchar(10) NOT NULL,
  `date_Of_Birth` date NOT NULL,
  `gender` varchar(10) NOT NULL,
  `address` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_Id`, `first_Name`, `last_Name`, `email`, `mobile_No`, `date_Of_Birth`, `gender`, `address`) VALUES
(1001, '', '', '', '', '0000-00-00', '', ''),
(1002, 'parth', 'maheriya', 'parth@gmail.com', '9173814394', '2006-04-13', 'Male', 'saijpur gopalpur'),
(1003, 'kunal', 'prajapati', 'kunal@gmail.com', '8155801865', '2005-05-14', 'Male', 'sanand'),
(1004, 'jay', 'panchal', 'jay@gmail.com', '8490067670', '2003-12-23', 'Male', 'gota'),
(1005, 'kunal', 'fauzdar', 'kunal@gmail.com', '1213141516', '2003-02-12', 'Male', 'chandkheda');

-- --------------------------------------------------------

--
-- Table structure for table `login_registration`
--

CREATE TABLE `login_registration` (
  `account_No` int(10) NOT NULL,
  `mobile_No` varchar(10) NOT NULL,
  `email` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login_registration`
--

INSERT INTO `login_registration` (`account_No`, `mobile_No`, `email`, `password`) VALUES
(123456790, '9173814394', 'abc.com', '123456790'),
(123456791, '8155801865', 'abc.com', '123456791'),
(123456792, '8490067670', 'abc.com', '123456792');

-- --------------------------------------------------------

--
-- Table structure for table `transactionhistory`
--

CREATE TABLE `transactionhistory` (
  `transactionID` int(100) NOT NULL,
  `senderAccountNo` int(10) NOT NULL,
  `receiverAccountNo` int(10) NOT NULL,
  `amount` double(20,2) NOT NULL,
  `message` longtext NOT NULL,
  `transactionDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Triggers `transactionhistory`
--
DELIMITER $$
CREATE TRIGGER `trg_update_balance_after_transaction` AFTER INSERT ON `transactionhistory` FOR EACH ROW BEGIN
    -- Update sender's account balance
    UPDATE Account_Database
    SET balance = balance - NEW.amount
    WHERE account_No = NEW.senderAccountNo;

    -- Update receiver's account balance
    UPDATE Account_Database
    SET balance = balance + NEW.amount
    WHERE account_No = NEW.receiverAccountNo;
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account_database`
--
ALTER TABLE `account_database`
  ADD PRIMARY KEY (`account_No`);

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`branch_Id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_Id`);

--
-- Indexes for table `login_registration`
--
ALTER TABLE `login_registration`
  ADD PRIMARY KEY (`account_No`),
  ADD UNIQUE KEY `mobile_No` (`mobile_No`);

--
-- Indexes for table `transactionhistory`
--
ALTER TABLE `transactionhistory`
  ADD PRIMARY KEY (`transactionID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account_database`
--
ALTER TABLE `account_database`
  MODIFY `account_No` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123456794;

--
-- AUTO_INCREMENT for table `branch`
--
ALTER TABLE `branch`
  MODIFY `branch_Id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customer_Id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1006;

--
-- AUTO_INCREMENT for table `login_registration`
--
ALTER TABLE `login_registration`
  MODIFY `account_No` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123456793;

--
-- AUTO_INCREMENT for table `transactionhistory`
--
ALTER TABLE `transactionhistory`
  MODIFY `transactionID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1028;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
