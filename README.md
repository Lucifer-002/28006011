# Basic Banking System

## Overview

This is a basic banking system implemented in Java using JDBC and MySQL. The system allows for managing customer accounts and performing transactions such as deposits, withdrawals, and transfers.

## Database Setup

1. Install MySQL and create a new database:
    ```sql
    CREATE DATABASE banking_system;
    USE banking_system;

    CREATE TABLE Customer (
        customer_id INT AUTO_INCREMENT PRIMARY KEY,
        customer_name VARCHAR(100) NOT NULL,
        customer_address VARCHAR(255) NOT NULL
    );

    CREATE TABLE Account (
        account_id INT AUTO_INCREMENT PRIMARY KEY,
        customer_id INT,
        account_type ENUM('Savings', 'Checking'),
        balance DECIMAL(10, 2) DEFAULT 0,
        FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
    );

    CREATE TABLE Transaction (
        transaction_id INT AUTO_INCREMENT PRIMARY KEY,
        account_id INT,
        transaction_type ENUM('Deposit', 'Withdrawal', 'Transfer'),
        amount DECIMAL(10, 2),
        transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (account_id) REFERENCES Account(account_id)
    );
    ```

2. Update the MySQL credentials in the `DatabaseConnection.java` file:
    ```java
    private static final String USER = "root";
    private static final String PASSWORD = "password"; // Change to your MySQL password
    ```

## Running the Application

1. Compile the Java files:
    ```sh
    javac -d bin src/com/bank/*.java
    ```

2. Run the application:
    ```sh
    java -cp bin com.bank.BankingSystem
    ```

## Usage

The application provides the following functionalities through a menu-based interface:


1. Add Account
2. View Account
3. Update Account
4. Delete Account
5. Deposit Funds
6. Withdraw Funds
7. Transfer Funds
8. View Transaction History
9. Exit the application

Follow the prompts to perform the desired operations.
