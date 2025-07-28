package io.space.vipin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    // Static variables for bank-wide operations
    private static int nextAccountNumber = 1001;
    private static Map<Integer, Account> accounts = new HashMap<>();
    private static List<Transaction> allTransactions = new ArrayList<>();
    
    // Inner class to represent a bank account
    public static class Account {
        private int accountNumber;
        private double balance;
        private String customerName;
        private String email;
        private long phoneNumber;
        private LocalDateTime accountOpenDate;
        private List<Transaction> transactions;
        
        public Account(String customerName, String email, long phoneNumber, double initialDeposit) {
            this.accountNumber = nextAccountNumber++;
            this.customerName = customerName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.balance = initialDeposit;
            this.accountOpenDate = LocalDateTime.now();
            this.transactions = new ArrayList<>();
            
            // Record initial deposit as first transaction
            if (initialDeposit > 0) {
                Transaction initialTransaction = new Transaction(this.accountNumber, "DEPOSIT", 
                    initialDeposit, this.balance, "Account opening deposit");
                this.transactions.add(initialTransaction);
                allTransactions.add(initialTransaction);
            }
        }
        
        // Getters
        public int getAccountNumber() { return accountNumber; }
        public double getBalance() { return balance; }
        public String getCustomerName() { return customerName; }
        public String getEmail() { return email; }
        public long getPhoneNumber() { return phoneNumber; }
        public LocalDateTime getAccountOpenDate() { return accountOpenDate; }
        public List<Transaction> getTransactions() { return new ArrayList<>(transactions); }
        public int getTransactionCount() { return transactions.size(); }
        
        // Setters
        public void setCustomerName(String customerName) { this.customerName = customerName; }
        public void setEmail(String email) { this.email = email; }
        public void setPhoneNumber(long phoneNumber) { this.phoneNumber = phoneNumber; }
        
        public boolean deposit(double amount, String description) {
            if (amount <= 0) {
                System.out.println("Deposit amount must be positive!");
                return false;
            }
            
            this.balance += amount;
            Transaction transaction = new Transaction(this.accountNumber, "DEPOSIT", 
                amount, this.balance, description);
            this.transactions.add(transaction);
            allTransactions.add(transaction);
            
            System.out.println("Deposit successful! Amount: $" + amount + 
                ", New balance: $" + String.format("%.2f", this.balance));
            return true;
        }
        
        public boolean withdraw(double amount, String description) {
            if (amount <= 0) {
                System.out.println("Withdrawal amount must be positive!");
                return false;
            }
            
            if (this.balance < amount) {
                System.out.println("Insufficient funds! Available balance: $" + 
                    String.format("%.2f", this.balance));
                return false;
            }
            
            this.balance -= amount;
            Transaction transaction = new Transaction(this.accountNumber, "WITHDRAWAL", 
                amount, this.balance, description);
            this.transactions.add(transaction);
            allTransactions.add(transaction);
            
            System.out.println("Withdrawal successful! Amount: $" + amount + 
                ", Remaining balance: $" + String.format("%.2f", this.balance));
            return true;
        }
        
        @Override
        public String toString() {
            return String.format("Account #%d - %s (Balance: $%.2f, Transactions: %d)", 
                accountNumber, customerName, balance, transactions.size());
        }
    }
    
    // Inner class to represent a transaction
    public static class Transaction {
        private int accountNumber;
        private String type;
        private double amount;
        private double balanceAfter;
        private LocalDateTime timestamp;
        private String description;
        
        public Transaction(int accountNumber, String type, double amount, 
                         double balanceAfter, String description) {
            this.accountNumber = accountNumber;
            this.type = type;
            this.amount = amount;
            this.balanceAfter = balanceAfter;
            this.timestamp = LocalDateTime.now();
            this.description = description;
        }
        
        // Getters
        public int getAccountNumber() { return accountNumber; }
        public String getType() { return type; }
        public double getAmount() { return amount; }
        public double getBalanceAfter() { return balanceAfter; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public String getDescription() { return description; }
        
        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return String.format("[%s] %s: $%.2f (Balance: $%.2f) - %s", 
                timestamp.format(formatter), type, amount, balanceAfter, description);
        }
    }
    
    // Bank operations
    public static Account openAccount(String customerName, String email, long phoneNumber, double initialDeposit) {
        if (customerName == null || customerName.trim().isEmpty()) {
            System.out.println("Customer name cannot be empty!");
            return null;
        }
        
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email cannot be empty!");
            return null;
        }
        
        if (initialDeposit < 0) {
            System.out.println("Initial deposit cannot be negative!");
            return null;
        }
        
        Account newAccount = new Account(customerName, email, phoneNumber, initialDeposit);
        accounts.put(newAccount.getAccountNumber(), newAccount);
        
        System.out.println("Account opened successfully!");
        System.out.println("Account Number: " + newAccount.getAccountNumber());
        System.out.println("Customer: " + newAccount.getCustomerName());
        System.out.println("Initial Balance: $" + String.format("%.2f", newAccount.getBalance()));
        
        return newAccount;
    }
    
    public static Account getAccount(int accountNumber) {
        return accounts.get(accountNumber);
    }
    
    public static boolean depositMoney(int accountNumber, double amount, String description) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return false;
        }
        
        return account.deposit(amount, description != null ? description : "Cash deposit");
    }
    
    public static boolean withdrawMoney(int accountNumber, double amount, String description) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return false;
        }
        
        return account.withdraw(amount, description != null ? description : "Cash withdrawal");
    }
    
    public static void displayAccountInfo(int accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }
        
        System.out.println("\n=== Account Information ===");
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Customer Name: " + account.getCustomerName());
        System.out.println("Email: " + account.getEmail());
        System.out.println("Phone: " + account.getPhoneNumber());
        System.out.println("Current Balance: $" + String.format("%.2f", account.getBalance()));
        System.out.println("Account Opened: " + account.getAccountOpenDate().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("Total Transactions: " + account.getTransactionCount());
    }
    
    public static void displayTransactionHistory(int accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }
        
        System.out.println("\n=== Transaction History for Account #" + accountNumber + " ===");
        List<Transaction> transactions = account.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
    
    public static void displayHighActivityCustomers() {
        System.out.println("\n=== High Activity Customers ===");
        
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        
        // Sort accounts by transaction count (descending)
        List<Account> sortedAccounts = new ArrayList<>(accounts.values());
        sortedAccounts.sort((a, b) -> Integer.compare(b.getTransactionCount(), a.getTransactionCount()));
        
        System.out.println("Customers ranked by transaction activity:");
        for (int i = 0; i < sortedAccounts.size(); i++) {
            Account account = sortedAccounts.get(i);
            System.out.printf("%d. %s (Account #%d) - %d transactions\n", 
                i + 1, account.getCustomerName(), account.getAccountNumber(), 
                account.getTransactionCount());
        }
        
        // Show customers with more than average transactions
        double avgTransactions = sortedAccounts.stream()
            .mapToInt(Account::getTransactionCount)
            .average()
            .orElse(0.0);
            
        System.out.println("\nCustomers with above-average activity (>" + 
            String.format("%.1f", avgTransactions) + " transactions):");
        
        boolean hasHighActivity = false;
        for (Account account : sortedAccounts) {
            if (account.getTransactionCount() > avgTransactions) {
                System.out.println("- " + account.getCustomerName() + 
                    " (Account #" + account.getAccountNumber() + "): " + 
                    account.getTransactionCount() + " transactions");
                hasHighActivity = true;
            }
        }
        
        if (!hasHighActivity) {
            System.out.println("No customers with above-average activity found.");
        }
    }
    
    public static void displayAllAccounts() {
        System.out.println("\n=== All Bank Accounts ===");
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        
        for (Account account : accounts.values()) {
            System.out.println(account);
        }
    }
    
    public static void displayBankStatistics() {
        System.out.println("\n=== Bank Statistics ===");
        System.out.println("Total Accounts: " + accounts.size());
        System.out.println("Total Transactions: " + allTransactions.size());
        
        double totalBalance = accounts.values().stream()
            .mapToDouble(Account::getBalance)
            .sum();
        System.out.println("Total Bank Balance: $" + String.format("%.2f", totalBalance));
        
        if (!accounts.isEmpty()) {
            double avgBalance = totalBalance / accounts.size();
            System.out.println("Average Account Balance: $" + String.format("%.2f", avgBalance));
        }
    }
}
