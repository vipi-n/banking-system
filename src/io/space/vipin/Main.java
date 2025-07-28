package io.space.vipin;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Welcome to Advanced Banking System ===\n");
        
        // Open multiple accounts
        System.out.println("1. Opening new accounts...\n");
        
        Bank.Account account1 = Bank.openAccount("John Doe", "john.doe@email.com", 1234567890L, 1000.0);
        Bank.Account account2 = Bank.openAccount("Jane Smith", "jane.smith@email.com", 9876543210L, 500.0);
        Bank.Account account3 = Bank.openAccount("Bob Johnson", "bob.johnson@email.com", 5555555555L, 2000.0);
        Bank.Account account4 = Bank.openAccount("Alice Brown", "alice.brown@email.com", 1111111111L, 750.0);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Perform various transactions to create different activity levels
        System.out.println("2. Performing transactions...\n");
        
        // John Doe - High activity (many transactions)
        Bank.depositMoney(account1.getAccountNumber(), 200.0, "Salary deposit");
        Bank.withdrawMoney(account1.getAccountNumber(), 50.0, "ATM withdrawal");
        Bank.depositMoney(account1.getAccountNumber(), 300.0, "Bonus payment");
        Bank.withdrawMoney(account1.getAccountNumber(), 100.0, "Grocery shopping");
        Bank.depositMoney(account1.getAccountNumber(), 150.0, "Freelance payment");
        Bank.withdrawMoney(account1.getAccountNumber(), 75.0, "Gas payment");
        Bank.depositMoney(account1.getAccountNumber(), 400.0, "Investment return");
        
        // Jane Smith - Medium activity
        Bank.depositMoney(account2.getAccountNumber(), 300.0, "Monthly salary");
        Bank.withdrawMoney(account2.getAccountNumber(), 120.0, "Rent payment");
        Bank.depositMoney(account2.getAccountNumber(), 250.0, "Side job payment");
        Bank.withdrawMoney(account2.getAccountNumber(), 80.0, "Utilities");
        
        // Bob Johnson - Low activity
        Bank.depositMoney(account3.getAccountNumber(), 500.0, "Business income");
        Bank.withdrawMoney(account3.getAccountNumber(), 200.0, "Equipment purchase");
        
        // Alice Brown - Very high activity
        Bank.depositMoney(account4.getAccountNumber(), 100.0, "Cash deposit");
        Bank.withdrawMoney(account4.getAccountNumber(), 50.0, "Coffee shop");
        Bank.depositMoney(account4.getAccountNumber(), 200.0, "Gift money");
        Bank.withdrawMoney(account4.getAccountNumber(), 30.0, "Lunch");
        Bank.depositMoney(account4.getAccountNumber(), 150.0, "Refund");
        Bank.withdrawMoney(account4.getAccountNumber(), 25.0, "Bus fare");
        Bank.depositMoney(account4.getAccountNumber(), 300.0, "Part-time job");
        Bank.withdrawMoney(account4.getAccountNumber(), 100.0, "Online shopping");
        Bank.depositMoney(account4.getAccountNumber(), 75.0, "Cashback");
        Bank.withdrawMoney(account4.getAccountNumber(), 40.0, "Movie tickets");
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Display all accounts
        System.out.println("3. Current account status...\n");
        Bank.displayAllAccounts();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Display high activity customers
        System.out.println("4. Customer activity analysis...\n");
        Bank.displayHighActivityCustomers();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Display detailed account information
        System.out.println("5. Detailed account information...\n");
        Bank.displayAccountInfo(account1.getAccountNumber());
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Display transaction history for high-activity account
        System.out.println("6. Transaction history for most active customer...\n");
        Bank.displayTransactionHistory(account4.getAccountNumber());
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Display bank statistics
        System.out.println("7. Bank statistics...\n");
        Bank.displayBankStatistics();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Test error handling
        System.out.println("8. Testing error handling...\n");
        System.out.println("Attempting invalid operations:");
        
        // Try to withdraw more than balance
        Bank.withdrawMoney(account2.getAccountNumber(), 10000.0, "Large withdrawal attempt");
        
        // Try to deposit negative amount
        Bank.depositMoney(account1.getAccountNumber(), -100.0, "Invalid deposit");
        
        // Try to access non-existent account
        Bank.displayAccountInfo(9999);
        
        System.out.println("\n=== Banking System Demo Complete ===");
    }
}
