package com.bank;
import java.util.Scanner;

public class BankingSystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		while (true) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("1. Add Account");
	        System.out.println("2. View Account");
	        System.out.println("3. Update Account");
	        System.out.println("4. Delete Account");
	        System.out.println("5. Deposit Funds");
	        System.out.println("6. Withdraw Funds");
	        System.out.println("7. Transfer Funds");
	        System.out.println("8. View Transaction History");
	        System.out.println("9. Exit");
	        System.out.print("Enter your choice: ");
	        int choice = scanner.nextInt();
	        scanner.nextLine();
	        if(choice == 1) {
				System.out.print("Enter customer name: ");
                String name = scanner.nextLine();
                //scanner.nextLine(); 
                System.out.print("Enter customer address: ");
                String address = scanner.nextLine();
                int c_id = Customer.addCustomer(name, address);
                //System.out.println(c_id);
                System.out.print("Enter account type (Savings/Checking): ");
                String accountType = scanner.nextLine();
                System.out.print("Enter Initial balance : ");
                double balance = scanner.nextDouble();
                Account.addAccount(c_id, accountType , balance);
           }
	        else if(choice == 2) {
				System.out.print("Enter account ID: ");
                int accountId = scanner.nextInt();
                Account.viewAccount(accountId);
			}
	        else if(choice == 3) {
				System.out.print("Enter account ID: ");
                int accountId = scanner.nextInt();
                scanner.nextLine(); 
                System.out.print("Enter new account type (Savings/Checking): ");
                String accountType = scanner.nextLine();
                
                Account.updateAccount(accountId, accountType);
			}
	        else if(choice == 4) {
				System.out.print("Enter account ID: ");
                int accountId = scanner.nextInt();
                Account.deleteAccount(accountId);
			}
	        else if(choice == 5) {
				System.out.print("Enter account ID: ");
                int accountId = scanner.nextInt();
                System.out.print("Enter deposit amount: ");
                double depositAmount = scanner.nextDouble();
                Account.deposit(accountId, depositAmount);
			}
	        else if(choice == 6) {
				System.out.print("Enter account ID: ");
                int accountId = scanner.nextInt();
                System.out.print("Enter withdrawal amount: ");
                double withdrawAmount = scanner.nextDouble();
                Account.withdraw(accountId, withdrawAmount);
			}
	        else if(choice == 7) {
				System.out.print("Enter source account ID: ");
                int fromAccountId = scanner.nextInt();
                System.out.print("Enter destination account ID: ");
                int toAccountId = scanner.nextInt();
                System.out.print("Enter transfer amount: ");
                double transferAmount = scanner.nextDouble();
                Account.transfer(fromAccountId, toAccountId, transferAmount);
			}
	        else if(choice == 8) {
				System.out.print("Enter account ID: ");
                int accountId = scanner.nextInt();
                Transaction.viewTransactionHistory(accountId);
			}
	        else if(choice == 9) {
				System.out.println("Bye Bye ....");
				System.out.println("Thanks for using the App ...");
				System.out.println("See you Soon .....");
				scanner.close();
				System.exit(0);
			}
			else {
				System.out.println("Invalid Input Enter your choice again ... ");
			}
			
		}
		
	}

}
