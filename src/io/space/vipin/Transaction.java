package io.space.vipin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT, INTEREST, FEE, LOAN_PAYMENT, LOAN_DISBURSEMENT
    }
    
    private String transactionId;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime timestamp;
    private String accountNumber;
    private double balanceAfter;
    private String referenceNumber;
    
    // Constructor for basic transactions
    public Transaction(TransactionType type, double amount, String description) {
        this.transactionId = generateTransactionId();
        this.type = type;
        this.amount = amount;
        this.description = description != null ? description : "";
        this.timestamp = LocalDateTime.now();
        this.referenceNumber = generateReferenceNumber();
    }
    
    // Constructor with account number and balance tracking
    public Transaction(TransactionType type, double amount, String description, 
                      String accountNumber, double balanceAfter) {
        this(type, amount, description);
        this.accountNumber = accountNumber;
        this.balanceAfter = balanceAfter;
    }
    
    // Constructor with custom timestamp (for historical data)
    public Transaction(TransactionType type, double amount, String description, 
                      LocalDateTime timestamp) {
        this.transactionId = generateTransactionId();
        this.type = type;
        this.amount = amount;
        this.description = description != null ? description : "";
        this.timestamp = timestamp;
        this.referenceNumber = generateReferenceNumber();
    }
    
    private String generateTransactionId() {
        return "TXN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private String generateReferenceNumber() {
        return "REF" + System.currentTimeMillis();
    }
    
    public boolean isDebit() {
        return type == TransactionType.WITHDRAWAL || 
               type == TransactionType.TRANSFER_OUT || 
               type == TransactionType.FEE ||
               type == TransactionType.LOAN_PAYMENT;
    }
    
    public boolean isCredit() {
        return type == TransactionType.DEPOSIT || 
               type == TransactionType.TRANSFER_IN || 
               type == TransactionType.INTEREST ||
               type == TransactionType.LOAN_DISBURSEMENT;
    }
    
    public String getFormattedAmount() {
        String prefix = isDebit() ? "-" : "+";
        return prefix + "$" + String.format("%.2f", amount);
    }
    
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }
    
    public void printTransactionDetails() {
        System.out.println("\n=== TRANSACTION DETAILS ===");
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Reference Number: " + referenceNumber);
        System.out.println("Type: " + type);
        System.out.println("Amount: " + getFormattedAmount());
        System.out.println("Description: " + description);
        System.out.println("Date & Time: " + getFormattedTimestamp());
        if (accountNumber != null) {
            System.out.println("Account: " + accountNumber);
            System.out.println("Balance After: $" + String.format("%.2f", balanceAfter));
        }
        System.out.println("===========================\n");
    }
    
    // Getters
    public String getTransactionId() { return transactionId; }
    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getAccountNumber() { return accountNumber; }
    public double getBalanceAfter() { return balanceAfter; }
    public String getReferenceNumber() { return referenceNumber; }
    
    // Setters (limited to maintain transaction integrity)
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setBalanceAfter(double balanceAfter) { this.balanceAfter = balanceAfter; }
    public void setDescription(String description) { 
        this.description = description != null ? description : ""; 
    }
    
    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s", 
            getFormattedTimestamp(),
            type,
            getFormattedAmount(),
            description,
            transactionId);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transaction that = (Transaction) obj;
        return transactionId.equals(that.transactionId);
    }
    
    @Override
    public int hashCode() {
        return transactionId.hashCode();
    }
}