package com.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Transaction {
	public static void viewTransactionHistory(int accountId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Transaction WHERE account_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, accountId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println("Transaction ID: " + resultSet.getInt("transaction_id"));
                        System.out.println("Transaction Type: " + resultSet.getString("transaction_type"));
                        System.out.println("Amount: " + resultSet.getDouble("amount"));
                        System.out.println("Date: " + resultSet.getTimestamp("transaction_date"));
                        System.out.println("------------------------------");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
