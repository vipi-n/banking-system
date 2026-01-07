package io.space.vipin;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BankingSystem {
    private Map<String, Customer> customers;
    private Map<String, Account> accounts;
    private Map<String, Loan> loans;
    private List<Transaction> allTransactions;
    private String bankName;
    private int nextAccountNumber;
    private int nextCustomerId;
    private int nextLoanId;
    
    public BankingSystem(String bankName) {
        this.bankName = bankName;
        this.customers = new HashMap<>();
        this.accounts = new HashMap<>();
        this.loans = new HashMap<>();
        this.allTransactions = new ArrayList<>();
        this.nextAccountNumber = 100001;
        this.nextCustomerId = 1001;
        this.nextLoanId = 5001;
    }
    
    // Customer Management
    public Customer createCustomer(String name, String email, String phoneNumber, 
                                 String address, LocalDate dateOfBirth) {
        try {
            String customerId = "CUST" + nextCustomerId++;
            Customer customer = new Customer(customerId, name, email, phoneNumber, address, dateOfBirth);
            customers.put(customerId, customer);
            System.out.println("Customer created successfully with ID: " + customerId);
            return customer;
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating customer: " + e.getMessage());
            return null;
        }
    }
    
    public Customer findCustomer(String customerId) {
        return customers.get(customerId);
    }
    
    public List<Customer> findCustomersByName(String name) {
        return customers.values().stream()
            .filter(c -> c.getName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    public boolean updateCustomerInfo(String customerId, String email, String phoneNumber, String address) {
        Customer customer = customers.get(customerId);
        if (customer != null) {
            customer.updateContactInfo(email, phoneNumber, address);
            System.out.println("Customer information updated successfully.");
            return true;
        }
        System.out.println("Customer not found.");
        return false;
    }
    
    // Account Management
    public Account createAccount(String customerId, Account.AccountType accountType, double initialDeposit) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            System.out.println("Customer not found. Cannot create account.");
            return null;
        }
        
        String accountNumber = String.valueOf(nextAccountNumber++);
        Account account = new Account(accountNumber, accountType, customer, initialDeposit);
        accounts.put(accountNumber, account);
        customer.addAccount(accountNumber);
        
        System.out.println("Account created successfully.");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + accountType);
        System.out.println("Initial Deposit: $" + String.format("%.2f", initialDeposit));
        
        return account;
    }
    
    public Account createCreditAccount(String customerId, double creditLimit) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            System.out.println("Customer not found. Cannot create account.");
            return null;
        }
        
        String accountNumber = String.valueOf(nextAccountNumber++);
        Account account = new Account(accountNumber, customer, creditLimit);
        accounts.put(accountNumber, account);
        customer.addAccount(accountNumber);
        
        System.out.println("Credit account created successfully.");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Credit Limit: $" + String.format("%.2f", creditLimit));
        
        return account;
    }
    
    public Account findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
    
    public List<Account> getCustomerAccounts(String customerId) {
        Customer customer = customers.get(customerId);
        if (customer == null) return new ArrayList<>();
        
        return customer.getAccountNumbers().stream()
            .map(accounts::get)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
    
    public boolean closeAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return false;
        }
        
        if (Math.abs(account.getBalance()) > 0.01) {
            System.out.println("Cannot close account with non-zero balance: $" + 
                String.format("%.2f", account.getBalance()));
            return false;
        }
        
        account.setActive(false);
        account.getCustomer().removeAccount(accountNumber);
        System.out.println("Account " + accountNumber + " has been closed.");
        return true;
    }
    
    // Transaction Operations
    public boolean deposit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return false;
        }
        return account.deposit(amount);
    }
    
    public boolean withdraw(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return false;
        }
        return account.withdraw(amount);
    }
    
    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = accounts.get(fromAccountNumber);
        Account toAccount = accounts.get(toAccountNumber);
        
        if (fromAccount == null) {
            System.out.println("Source account not found.");
            return false;
        }
        if (toAccount == null) {
            System.out.println("Destination account not found.");
            return false;
        }
        
        return fromAccount.transfer(toAccount, amount);
    }
    
    // Interest Calculations
    public void applyMonthlyInterest() {
        System.out.println("Applying monthly interest to all savings accounts...");
        int count = 0;
        for (Account account : accounts.values()) {
            if (account.getAccountType() == Account.AccountType.SAVINGS && account.isActive()) {
                account.calculateAndApplyInterest();
                count++;
            }
        }
        System.out.println("Interest applied to " + count + " savings accounts.");
    }
    
    // Loan Management
    public Loan createLoan(String customerId, Loan.LoanType loanType, double principalAmount,
                          double interestRate, int termInMonths, String purpose) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            System.out.println("Customer not found. Cannot create loan.");
            return null;
        }
        
        String loanId = "LOAN" + nextLoanId++;
        Loan loan = new Loan(loanId, loanType, customer, principalAmount, 
            interestRate, termInMonths, purpose);
        loans.put(loanId, loan);
        
        System.out.println("Loan application created successfully.");
        System.out.println("Loan ID: " + loanId);
        System.out.println("Amount: $" + String.format("%.2f", principalAmount));
        System.out.println("Monthly Payment: $" + String.format("%.2f", loan.getMonthlyPayment()));
        
        return loan;
    }
    
    public Loan findLoan(String loanId) {
        return loans.get(loanId);
    }
    
    public List<Loan> getCustomerLoans(String customerId) {
        return loans.values().stream()
            .filter(loan -> loan.getCustomer().getCustomerId().equals(customerId))
            .collect(Collectors.toList());
    }
    
    public boolean approveLoan(String loanId) {
        Loan loan = loans.get(loanId);
        if (loan == null) {
            System.out.println("Loan not found.");
            return false;
        }
        loan.approveLoan();
        return true;
    }
    
    public boolean activateLoan(String loanId) {
        Loan loan = loans.get(loanId);
        if (loan == null) {
            System.out.println("Loan not found.");
            return false;
        }
        loan.activateLoan();
        return true;
    }
    
    public boolean makeLoanPayment(String loanId, double paymentAmount) {
        Loan loan = loans.get(loanId);
        if (loan == null) {
            System.out.println("Loan not found.");
            return false;
        }
        return loan.makePayment(paymentAmount);
    }
    
    // Reporting and Analytics
    public void generateBankSummary() {
        System.out.println("\n=== " + bankName.toUpperCase() + " BANK SUMMARY ===");
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Accounts: " + accounts.size());
        System.out.println("Total Loans: " + loans.size());
        
        // Account type breakdown
        Map<Account.AccountType, Long> accountTypeCount = accounts.values().stream()
            .collect(Collectors.groupingBy(Account::getAccountType, Collectors.counting()));
        
        System.out.println("\nAccount Breakdown:");
        for (Map.Entry<Account.AccountType, Long> entry : accountTypeCount.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        
        // Total deposits
        double totalDeposits = accounts.values().stream()
            .filter(account -> account.getAccountType() != Account.AccountType.CREDIT)
            .mapToDouble(Account::getBalance)
            .sum();
        
        // Total credit extended
        double totalCreditExtended = accounts.values().stream()
            .filter(account -> account.getAccountType() == Account.AccountType.CREDIT)
            .mapToDouble(account -> account.getCreditLimit() + account.getBalance())
            .sum();
        
        // Total loans outstanding
        double totalLoansOutstanding = loans.values().stream()
            .filter(loan -> loan.getStatus() == Loan.LoanStatus.ACTIVE)
            .mapToDouble(Loan::getCurrentBalance)
            .sum();
        
        System.out.println("\nFinancial Summary:");
        System.out.println("Total Deposits: $" + String.format("%.2f", totalDeposits));
        System.out.println("Total Credit Extended: $" + String.format("%.2f", totalCreditExtended));
        System.out.println("Total Loans Outstanding: $" + String.format("%.2f", totalLoansOutstanding));
        
        // Loan status breakdown
        Map<Loan.LoanStatus, Long> loanStatusCount = loans.values().stream()
            .collect(Collectors.groupingBy(Loan::getStatus, Collectors.counting()));
        
        System.out.println("\nLoan Status Breakdown:");
        for (Map.Entry<Loan.LoanStatus, Long> entry : loanStatusCount.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        
        System.out.println("===============================\n");
    }
    
    public void generateCustomerReport(String customerId) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        
        customer.printCustomerInfo();
        
        List<Account> customerAccounts = getCustomerAccounts(customerId);
        System.out.println("=== CUSTOMER ACCOUNTS ===");
        if (customerAccounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for (Account account : customerAccounts) {
                System.out.println(account);
            }
        }
        
        List<Loan> customerLoans = getCustomerLoans(customerId);
        System.out.println("\n=== CUSTOMER LOANS ===");
        if (customerLoans.isEmpty()) {
            System.out.println("No loans found.");
        } else {
            for (Loan loan : customerLoans) {
                System.out.println(loan);
            }
        }
        System.out.println("=========================\n");
    }
    
    public List<Loan> getOverdueLoans() {
        return loans.values().stream()
            .filter(Loan::isOverdue)
            .collect(Collectors.toList());
    }
    
    public List<Account> getInactiveAccounts() {
        return accounts.values().stream()
            .filter(account -> !account.isActive())
            .collect(Collectors.toList());
    }
    
    // Utility Methods
    public void printAllCustomers() {
        System.out.println("\n=== ALL CUSTOMERS ===");
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            customers.values().forEach(System.out::println);
        }
        System.out.println("=====================\n");
    }
    
    public void printAllAccounts() {
        System.out.println("\n=== ALL ACCOUNTS ===");
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            accounts.values().forEach(System.out::println);
        }
        System.out.println("====================\n");
    }
    
    public void printAllLoans() {
        System.out.println("\n=== ALL LOANS ===");
        if (loans.isEmpty()) {
            System.out.println("No loans found.");
        } else {
            loans.values().forEach(System.out::println);
        }
        System.out.println("=================\n");
    }
    
    // Getters
    public String getBankName() { return bankName; }
    public Map<String, Customer> getCustomers() { return new HashMap<>(customers); }
    public Map<String, Account> getAccounts() { return new HashMap<>(accounts); }
    public Map<String, Loan> getLoans() { return new HashMap<>(loans); }
    public List<Transaction> getAllTransactions() { return new ArrayList<>(allTransactions); }
}