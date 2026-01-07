package io.space.vipin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static BankingSystem bankingSystem;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        bankingSystem = new BankingSystem("First National Bank");
        scanner = new Scanner(System.in);
        
        System.out.println("==============================================");
        System.out.println("    WELCOME TO " + bankingSystem.getBankName().toUpperCase());
        System.out.println("    Comprehensive Banking System");
        System.out.println("==============================================");
        
        // Create some sample data for demonstration
        createSampleData();
        
        // Main menu loop
        while (true) {
            showMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    customerManagementMenu();
                    break;
                case 2:
                    accountManagementMenu();
                    break;
                case 3:
                    transactionMenu();
                    break;
                case 4:
                    loanManagementMenu();
                    break;
                case 5:
                    reportsMenu();
                    break;
                case 6:
                    adminMenu();
                    break;
                case 0:
                    System.out.println("Thank you for using " + bankingSystem.getBankName() + "!");
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void showMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Customer Management");
        System.out.println("2. Account Management");
        System.out.println("3. Transactions");
        System.out.println("4. Loan Management");
        System.out.println("5. Reports");
        System.out.println("6. Admin Functions");
        System.out.println("0. Exit");
        System.out.println("=================");
    }
    
    private static void customerManagementMenu() {
        while (true) {
            System.out.println("\n=== CUSTOMER MANAGEMENT ===");
            System.out.println("1. Create New Customer");
            System.out.println("2. Find Customer by ID");
            System.out.println("3. Search Customers by Name");
            System.out.println("4. Update Customer Information");
            System.out.println("5. View Customer Details");
            System.out.println("6. List All Customers");
            System.out.println("0. Back to Main Menu");
            System.out.println("===========================");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createNewCustomer();
                    break;
                case 2:
                    findCustomerById();
                    break;
                case 3:
                    searchCustomersByName();
                    break;
                case 4:
                    updateCustomerInfo();
                    break;
                case 5:
                    viewCustomerDetails();
                    break;
                case 6:
                    bankingSystem.printAllCustomers();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void accountManagementMenu() {
        while (true) {
            System.out.println("\n=== ACCOUNT MANAGEMENT ===");
            System.out.println("1. Create New Account");
            System.out.println("2. Find Account");
            System.out.println("3. View Account Statement");
            System.out.println("4. View Customer Accounts");
            System.out.println("5. Close Account");
            System.out.println("6. List All Accounts");
            System.out.println("0. Back to Main Menu");
            System.out.println("==========================");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createNewAccount();
                    break;
                case 2:
                    findAccount();
                    break;
                case 3:
                    viewAccountStatement();
                    break;
                case 4:
                    viewCustomerAccounts();
                    break;
                case 5:
                    closeAccount();
                    break;
                case 6:
                    bankingSystem.printAllAccounts();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void transactionMenu() {
        while (true) {
            System.out.println("\n=== TRANSACTIONS ===");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer Between Accounts");
            System.out.println("4. Check Account Balance");
            System.out.println("0. Back to Main Menu");
            System.out.println("====================");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    performDeposit();
                    break;
                case 2:
                    performWithdrawal();
                    break;
                case 3:
                    performTransfer();
                    break;
                case 4:
                    checkBalance();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void loanManagementMenu() {
        while (true) {
            System.out.println("\n=== LOAN MANAGEMENT ===");
            System.out.println("1. Apply for Loan");
            System.out.println("2. View Loan Details");
            System.out.println("3. Make Loan Payment");
            System.out.println("4. View Customer Loans");
            System.out.println("5. Approve Loan");
            System.out.println("6. Activate Loan");
            System.out.println("7. View Loan Payment History");
            System.out.println("8. List All Loans");
            System.out.println("0. Back to Main Menu");
            System.out.println("=======================");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    applyForLoan();
                    break;
                case 2:
                    viewLoanDetails();
                    break;
                case 3:
                    makeLoanPayment();
                    break;
                case 4:
                    viewCustomerLoans();
                    break;
                case 5:
                    approveLoan();
                    break;
                case 6:
                    activateLoan();
                    break;
                case 7:
                    viewLoanPaymentHistory();
                    break;
                case 8:
                    bankingSystem.printAllLoans();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void reportsMenu() {
        while (true) {
            System.out.println("\n=== REPORTS ===");
            System.out.println("1. Bank Summary");
            System.out.println("2. Customer Report");
            System.out.println("3. Overdue Loans");
            System.out.println("4. Inactive Accounts");
            System.out.println("0. Back to Main Menu");
            System.out.println("===============");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    bankingSystem.generateBankSummary();
                    break;
                case 2:
                    generateCustomerReport();
                    break;
                case 3:
                    showOverdueLoans();
                    break;
                case 4:
                    showInactiveAccounts();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void adminMenu() {
        while (true) {
            System.out.println("\n=== ADMIN FUNCTIONS ===");
            System.out.println("1. Apply Monthly Interest");
            System.out.println("2. System Statistics");
            System.out.println("0. Back to Main Menu");
            System.out.println("=======================");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    bankingSystem.applyMonthlyInterest();
                    break;
                case 2:
                    bankingSystem.generateBankSummary();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Customer Management Methods
    private static void createNewCustomer() {
        System.out.println("\n=== CREATE NEW CUSTOMER ===");
        String name = getStringInput("Enter customer name: ");
        String email = getStringInput("Enter email: ");
        String phone = getStringInput("Enter phone number: ");
        String address = getStringInput("Enter address (optional): ");
        
        System.out.print("Enter date of birth (YYYY-MM-DD, optional): ");
        String dobStr = scanner.nextLine().trim();
        LocalDate dob = null;
        if (!dobStr.isEmpty()) {
            try {
                dob = LocalDate.parse(dobStr, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Proceeding without date of birth.");
            }
        }
        
        Customer customer = bankingSystem.createCustomer(name, email, phone, address, dob);
        if (customer != null) {
            customer.printCustomerInfo();
        }
    }
    
    private static void findCustomerById() {
        String customerId = getStringInput("Enter customer ID: ");
        Customer customer = bankingSystem.findCustomer(customerId);
        if (customer != null) {
            customer.printCustomerInfo();
        } else {
            System.out.println("Customer not found.");
        }
    }
    
    private static void searchCustomersByName() {
        String name = getStringInput("Enter name to search: ");
        List<Customer> customers = bankingSystem.findCustomersByName(name);
        if (customers.isEmpty()) {
            System.out.println("No customers found with that name.");
        } else {
            System.out.println("Found " + customers.size() + " customer(s):");
            customers.forEach(System.out::println);
        }
    }
    
    private static void updateCustomerInfo() {
        String customerId = getStringInput("Enter customer ID: ");
        Customer customer = bankingSystem.findCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        
        System.out.println("Current customer info:");
        customer.printCustomerInfo();
        
        String email = getStringInput("Enter new email (or press Enter to keep current): ");
        String phone = getStringInput("Enter new phone (or press Enter to keep current): ");
        String address = getStringInput("Enter new address (or press Enter to keep current): ");
        
        bankingSystem.updateCustomerInfo(customerId, 
            email.isEmpty() ? null : email,
            phone.isEmpty() ? null : phone,
            address.isEmpty() ? null : address);
    }
    
    private static void viewCustomerDetails() {
        String customerId = getStringInput("Enter customer ID: ");
        bankingSystem.generateCustomerReport(customerId);
    }
    
    // Account Management Methods
    private static void createNewAccount() {
        String customerId = getStringInput("Enter customer ID: ");
        Customer customer = bankingSystem.findCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        
        System.out.println("Account Types:");
        System.out.println("1. Checking");
        System.out.println("2. Savings");
        System.out.println("3. Credit");
        
        int typeChoice = getIntInput("Select account type: ");
        Account.AccountType accountType;
        
        switch (typeChoice) {
            case 1:
                accountType = Account.AccountType.CHECKING;
                break;
            case 2:
                accountType = Account.AccountType.SAVINGS;
                break;
            case 3:
                double creditLimit = getDoubleInput("Enter credit limit: ");
                bankingSystem.createCreditAccount(customerId, creditLimit);
                return;
            default:
                System.out.println("Invalid account type.");
                return;
        }
        
        double initialDeposit = getDoubleInput("Enter initial deposit: ");
        bankingSystem.createAccount(customerId, accountType, initialDeposit);
    }
    
    private static void findAccount() {
        String accountNumber = getStringInput("Enter account number: ");
        Account account = bankingSystem.findAccount(accountNumber);
        if (account != null) {
            System.out.println(account);
        } else {
            System.out.println("Account not found.");
        }
    }
    
    private static void viewAccountStatement() {
        String accountNumber = getStringInput("Enter account number: ");
        Account account = bankingSystem.findAccount(accountNumber);
        if (account != null) {
            account.printStatement();
        } else {
            System.out.println("Account not found.");
        }
    }
    
    private static void viewCustomerAccounts() {
        String customerId = getStringInput("Enter customer ID: ");
        List<Account> accounts = bankingSystem.getCustomerAccounts(customerId);
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for this customer.");
        } else {
            System.out.println("Customer accounts:");
            accounts.forEach(System.out::println);
        }
    }
    
    private static void closeAccount() {
        String accountNumber = getStringInput("Enter account number to close: ");
        bankingSystem.closeAccount(accountNumber);
    }
    
    // Transaction Methods
    private static void performDeposit() {
        String accountNumber = getStringInput("Enter account number: ");
        double amount = getDoubleInput("Enter deposit amount: ");
        bankingSystem.deposit(accountNumber, amount);
    }
    
    private static void performWithdrawal() {
        String accountNumber = getStringInput("Enter account number: ");
        double amount = getDoubleInput("Enter withdrawal amount: ");
        bankingSystem.withdraw(accountNumber, amount);
    }
    
    private static void performTransfer() {
        String fromAccount = getStringInput("Enter source account number: ");
        String toAccount = getStringInput("Enter destination account number: ");
        double amount = getDoubleInput("Enter transfer amount: ");
        bankingSystem.transfer(fromAccount, toAccount, amount);
    }
    
    private static void checkBalance() {
        String accountNumber = getStringInput("Enter account number: ");
        Account account = bankingSystem.findAccount(accountNumber);
        if (account != null) {
            System.out.println("Account: " + accountNumber);
            System.out.println("Balance: $" + String.format("%.2f", account.getBalance()));
            if (account.getAccountType() == Account.AccountType.CREDIT) {
                System.out.println("Available Credit: $" + String.format("%.2f", account.getAvailableBalance()));
            }
        } else {
            System.out.println("Account not found.");
        }
    }
    
    // Loan Management Methods
    private static void applyForLoan() {
        String customerId = getStringInput("Enter customer ID: ");
        Customer customer = bankingSystem.findCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        
        System.out.println("Loan Types:");
        System.out.println("1. Personal");
        System.out.println("2. Mortgage");
        System.out.println("3. Auto");
        System.out.println("4. Education");
        System.out.println("5. Business");
        
        int typeChoice = getIntInput("Select loan type: ");
        Loan.LoanType loanType;
        
        switch (typeChoice) {
            case 1: loanType = Loan.LoanType.PERSONAL; break;
            case 2: loanType = Loan.LoanType.MORTGAGE; break;
            case 3: loanType = Loan.LoanType.AUTO; break;
            case 4: loanType = Loan.LoanType.EDUCATION; break;
            case 5: loanType = Loan.LoanType.BUSINESS; break;
            default:
                System.out.println("Invalid loan type.");
                return;
        }
        
        double amount = getDoubleInput("Enter loan amount: ");
        double interestRate = getDoubleInput("Enter annual interest rate (as decimal, e.g., 0.05 for 5%): ");
        int termMonths = getIntInput("Enter loan term in months: ");
        String purpose = getStringInput("Enter loan purpose (optional): ");
        
        bankingSystem.createLoan(customerId, loanType, amount, interestRate, termMonths, purpose);
    }
    
    private static void viewLoanDetails() {
        String loanId = getStringInput("Enter loan ID: ");
        Loan loan = bankingSystem.findLoan(loanId);
        if (loan != null) {
            loan.printLoanDetails();
        } else {
            System.out.println("Loan not found.");
        }
    }
    
    private static void makeLoanPayment() {
        String loanId = getStringInput("Enter loan ID: ");
        double amount = getDoubleInput("Enter payment amount: ");
        bankingSystem.makeLoanPayment(loanId, amount);
    }
    
    private static void viewCustomerLoans() {
        String customerId = getStringInput("Enter customer ID: ");
        List<Loan> loans = bankingSystem.getCustomerLoans(customerId);
        if (loans.isEmpty()) {
            System.out.println("No loans found for this customer.");
        } else {
            System.out.println("Customer loans:");
            loans.forEach(System.out::println);
        }
    }
    
    private static void approveLoan() {
        String loanId = getStringInput("Enter loan ID to approve: ");
        bankingSystem.approveLoan(loanId);
    }
    
    private static void activateLoan() {
        String loanId = getStringInput("Enter loan ID to activate: ");
        bankingSystem.activateLoan(loanId);
    }
    
    private static void viewLoanPaymentHistory() {
        String loanId = getStringInput("Enter loan ID: ");
        Loan loan = bankingSystem.findLoan(loanId);
        if (loan != null) {
            loan.printPaymentHistory();
        } else {
            System.out.println("Loan not found.");
        }
    }
    
    // Report Methods
    private static void generateCustomerReport() {
        String customerId = getStringInput("Enter customer ID: ");
        bankingSystem.generateCustomerReport(customerId);
    }
    
    private static void showOverdueLoans() {
        List<Loan> overdueLoans = bankingSystem.getOverdueLoans();
        if (overdueLoans.isEmpty()) {
            System.out.println("No overdue loans found.");
        } else {
            System.out.println("Overdue loans:");
            for (Loan loan : overdueLoans) {
                System.out.println(loan + " - " + loan.getDaysOverdue() + " days overdue");
            }
        }
    }
    
    private static void showInactiveAccounts() {
        List<Account> inactiveAccounts = bankingSystem.getInactiveAccounts();
        if (inactiveAccounts.isEmpty()) {
            System.out.println("No inactive accounts found.");
        } else {
            System.out.println("Inactive accounts:");
            inactiveAccounts.forEach(System.out::println);
        }
    }
    
    // Sample Data Creation
    private static void createSampleData() {
        System.out.println("Creating sample data for demonstration...\n");
        
        // Create sample customers
        Customer customer1 = bankingSystem.createCustomer("John Doe", "john.doe@email.com", 
            "1234567890", "123 Main St", LocalDate.of(1985, 5, 15));
        Customer customer2 = bankingSystem.createCustomer("Jane Smith", "jane.smith@email.com", 
            "0987654321", "456 Oak Ave", LocalDate.of(1990, 8, 22));
        
        if (customer1 != null && customer2 != null) {
            // Create sample accounts
            bankingSystem.createAccount(customer1.getCustomerId(), Account.AccountType.CHECKING, 1000.0);
            bankingSystem.createAccount(customer1.getCustomerId(), Account.AccountType.SAVINGS, 5000.0);
            bankingSystem.createCreditAccount(customer2.getCustomerId(), 2000.0);
            
            // Create sample loan
            Loan loan = bankingSystem.createLoan(customer1.getCustomerId(), Loan.LoanType.PERSONAL, 
                10000.0, 0.08, 36, "Home improvement");
            if (loan != null) {
                bankingSystem.approveLoan(loan.getLoanId());
                bankingSystem.activateLoan(loan.getLoanId());
            }
        }
        
        System.out.println("Sample data created successfully!\n");
    }
    
    // Utility Methods
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
