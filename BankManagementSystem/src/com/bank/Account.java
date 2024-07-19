package com.bank;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Account {
	private int accountId;
    private int customerId;
    private String accountType;
    private double balance;
    
    public static void addAccount(int customerId, String accountType , double balance) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Account (customer_id, account_type , balance) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, customerId);
                statement.setString(2, accountType);
                statement.setDouble(3, balance);
                statement.executeUpdate();
                System.out.println("Account added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewAccount(int accountId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Account WHERE account_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, accountId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int customerId = resultSet.getInt("customer_id");
                        System.out.println("Account ID: " + resultSet.getInt("account_id"));
                        System.out.println("Customer ID: " + customerId);
                        System.out.println("Account Type: " + resultSet.getString("account_type"));
                        System.out.println("Balance: " + resultSet.getDouble("balance"));

                        String customerQuery = "SELECT * FROM Customer WHERE customer_id = ?";
                        try (PreparedStatement customerStatement = connection.prepareStatement(customerQuery)) {
                            customerStatement.setInt(1, customerId);
                            try (ResultSet customerResultSet = customerStatement.executeQuery()) {
                                if (customerResultSet.next()) {
                                    System.out.println("Customer Name: " + customerResultSet.getString("customer_name"));
                                    System.out.println("Customer Address: " + customerResultSet.getString("customer_address"));
                                }
                            }
                        }
                    } else {
                        System.out.println("Account not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAccount(int accountId, String accountType) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE Account SET account_type = ? WHERE account_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, accountType);
                statement.setInt(2, accountId);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Account updated successfully.");
                } else {
                    System.out.println("Account not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAccount(int accountId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM Account WHERE account_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, accountId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Account deleted successfully.");
                } else {
                    System.out.println("Account not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deposit(int accountId, double amount) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE Account SET balance = balance + ? WHERE account_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDouble(1, amount);
                statement.setInt(2, accountId);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    String transactionQuery = "INSERT INTO Transaction (account_id, transaction_type, amount) VALUES (?, 'Deposit', ?)";
                    try (PreparedStatement transactionStatement = connection.prepareStatement(transactionQuery)) {
                        transactionStatement.setInt(1, accountId);
                        transactionStatement.setDouble(2, amount);
                        transactionStatement.executeUpdate();
                    }
                    System.out.println("Deposit successful.");
                } else {
                    System.out.println("Account not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void withdraw(int accountId, double amount) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE Account SET balance = balance - ? WHERE account_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDouble(1, amount);
                statement.setInt(2, accountId);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    String transactionQuery = "INSERT INTO Transaction (account_id, transaction_type, amount) VALUES (?, 'Withdrawal', ?)";
                    try (PreparedStatement transactionStatement = connection.prepareStatement(transactionQuery)) {
                        transactionStatement.setInt(1, accountId);
                        transactionStatement.setDouble(2, amount);
                        transactionStatement.executeUpdate();
                    }
                    System.out.println("Withdrawal successful.");
                } else {
                    System.out.println("Account not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void transfer(int fromAccountId, int toAccountId, double amount) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                String withdrawQuery = "UPDATE Account SET balance = balance - ? WHERE account_id = ?";
                try (PreparedStatement withdrawStatement = connection.prepareStatement(withdrawQuery)) {
                    withdrawStatement.setDouble(1, amount);
                    withdrawStatement.setInt(2, fromAccountId);
                    int rowsUpdated = withdrawStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        String depositQuery = "UPDATE Account SET balance = balance + ? WHERE account_id = ?";
                        try (PreparedStatement depositStatement = connection.prepareStatement(depositQuery)) {
                            depositStatement.setDouble(1, amount);
                            depositStatement.setInt(2, toAccountId);
                            rowsUpdated = depositStatement.executeUpdate();
                            if (rowsUpdated > 0) {
                                String transactionQuery = "INSERT INTO Transaction (account_id, transaction_type, amount) VALUES (?, 'Transfer', ?), (?, 'Transfer', ?)";
                                try (PreparedStatement transactionStatement = connection.prepareStatement(transactionQuery)) {
                                    transactionStatement.setInt(1, fromAccountId);
                                    transactionStatement.setDouble(2, amount);
                                    transactionStatement.setInt(3, toAccountId);
                                    transactionStatement.setDouble(4, amount);
                                    transactionStatement.executeUpdate();
                                }
                                connection.commit();
                                System.out.println("Transfer successful.");
                            } else {
                                System.out.println("Recipient account not found.");
                                connection.rollback();
                            }
                        }
                    } else {
                        System.out.println("Sender account not found or insufficient funds.");
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
