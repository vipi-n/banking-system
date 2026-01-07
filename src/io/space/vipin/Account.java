package io.space.vipin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Account {
    public enum AccountType {
        CHECKING, SAVINGS, CREDIT
    }
    
    private String accountNumber;
    private AccountType accountType;
    private double balance;
    private double interestRate;
    private double creditLimit;
    private Customer customer;
    private LocalDateTime createdDate;
    private boolean isActive;
    private List<Transaction> transactionHistory;
    
    // Constructor for Checking and Savings accounts
    public Account(String accountNumber, AccountType accountType, Customer customer, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.customer = customer;
        this.balance = initialDeposit;
        this.createdDate = LocalDateTime.now();
        this.isActive = true;
        this.transactionHistory = new ArrayList<>();
        
        // Set default interest rates and limits
        switch (accountType) {
            case CHECKING:
                this.interestRate = 0.01; // 1% annual
                this.creditLimit = 0;
                break;
            case SAVINGS:
                this.interestRate = 0.025; // 2.5% annual
                this.creditLimit = 0;
                break;
            case CREDIT:
                this.interestRate = 0.18; // 18% annual
                this.creditLimit = 1000; // Default credit limit
                this.balance = 0; // Credit accounts start with 0 balance
                break;
        }
        
        // Record initial deposit transaction
        if (initialDeposit > 0) {
            addTransaction(new Transaction(Transaction.TransactionType.DEPOSIT, initialDeposit, "Initial deposit"));
        }
    }
    
    // Constructor for Credit accounts with custom credit limit
    public Account(String accountNumber, Customer customer, double creditLimit) {
        this(accountNumber, AccountType.CREDIT, customer, 0);
        this.creditLimit = creditLimit;
    }
    
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return false;
        }
        
        if (!isActive) {
            System.out.println("Account is inactive. Cannot perform deposit.");
            return false;
        }
        
        this.balance += amount;
        addTransaction(new Transaction(Transaction.TransactionType.DEPOSIT, amount, "Deposit"));
        System.out.println("Deposit successful. New balance: $" + String.format("%.2f", balance));
        return true;
    }
    
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }
        
        if (!isActive) {
            System.out.println("Account is inactive. Cannot perform withdrawal.");
            return false;
        }
        
        // Check if withdrawal is allowed based on account type
        if (accountType == AccountType.CREDIT) {
            if (balance - amount < -creditLimit) {
                System.out.println("Insufficient credit limit. Available credit: $" + 
                    String.format("%.2f", creditLimit + balance));
                return false;
            }
        } else {
            if (balance < amount) {
                System.out.println("Insufficient funds. Available balance: $" + String.format("%.2f", balance));
                return false;
            }
        }
        
        this.balance -= amount;
        addTransaction(new Transaction(Transaction.TransactionType.WITHDRAWAL, amount, "Withdrawal"));
        System.out.println("Withdrawal successful. New balance: $" + String.format("%.2f", balance));
        return true;
    }
    
    public boolean transfer(Account targetAccount, double amount) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive.");
            return false;
        }
        
        if (!isActive || !targetAccount.isActive) {
            System.out.println("One or both accounts are inactive. Cannot perform transfer.");
            return false;
        }
        
        if (this.withdraw(amount)) {
            targetAccount.deposit(amount);
            addTransaction(new Transaction(Transaction.TransactionType.TRANSFER_OUT, amount, 
                "Transfer to account " + targetAccount.getAccountNumber()));
            targetAccount.addTransaction(new Transaction(Transaction.TransactionType.TRANSFER_IN, amount, 
                "Transfer from account " + this.accountNumber));
            System.out.println("Transfer successful from " + this.accountNumber + " to " + 
                targetAccount.getAccountNumber());
            return true;
        }
        return false;
    }
    
    public void calculateAndApplyInterest() {
        if (accountType == AccountType.SAVINGS && balance > 0) {
            double interest = balance * (interestRate / 12); // Monthly interest
            balance += interest;
            addTransaction(new Transaction(Transaction.TransactionType.INTEREST, interest, "Monthly interest"));
            System.out.println("Interest applied: $" + String.format("%.2f", interest) + 
                ". New balance: $" + String.format("%.2f", balance));
        }
    }
    
    public double getAvailableBalance() {
        if (accountType == AccountType.CREDIT) {
            return creditLimit + balance; // Available credit
        }
        return balance;
    }
    
    public void printStatement() {
        System.out.println("\n=== ACCOUNT STATEMENT ===");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + accountType);
        System.out.println("Account Holder: " + customer.getName());
        System.out.println("Current Balance: $" + String.format("%.2f", balance));
        if (accountType == AccountType.CREDIT) {
            System.out.println("Credit Limit: $" + String.format("%.2f", creditLimit));
            System.out.println("Available Credit: $" + String.format("%.2f", getAvailableBalance()));
        }
        System.out.println("Account Status: " + (isActive ? "Active" : "Inactive"));
        System.out.println("Created Date: " + createdDate.toLocalDate());
        
        System.out.println("\n--- Recent Transactions ---");
        int count = 0;
        for (int i = transactionHistory.size() - 1; i >= 0 && count < 10; i--, count++) {
            System.out.println(transactionHistory.get(i));
        }
        System.out.println("========================\n");
    }
    
    private void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }
    
    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public AccountType getAccountType() { return accountType; }
    public double getBalance() { return balance; }
    public double getInterestRate() { return interestRate; }
    public double getCreditLimit() { return creditLimit; }
    public Customer getCustomer() { return customer; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public boolean isActive() { return isActive; }
    public List<Transaction> getTransactionHistory() { return new ArrayList<>(transactionHistory); }
    
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
    public void setCreditLimit(double creditLimit) { this.creditLimit = creditLimit; }
    public void setActive(boolean active) { this.isActive = active; }
    
    @Override
    public String toString() {
        return String.format("Account[%s, %s, Balance: $%.2f, Customer: %s]", 
            accountNumber, accountType, balance, customer.getName());
    }
}