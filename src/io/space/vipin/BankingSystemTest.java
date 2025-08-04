package io.space.vipin;

import java.time.LocalDate;

public class BankingSystemTest {
    public static void main(String[] args) {
        System.out.println("=== BANKING SYSTEM DEMONSTRATION ===");
        
        // Create banking system
        BankingSystem bank = new BankingSystem("Demo National Bank");
        
        // Create customers
        System.out.println("\n1. Creating customers...");
        Customer john = bank.createCustomer("John Doe", "john@email.com", "1234567890", 
            "123 Main St", LocalDate.of(1985, 5, 15));
        Customer jane = bank.createCustomer("Jane Smith", "jane@email.com", "0987654321", 
            "456 Oak Ave", LocalDate.of(1990, 8, 22));
        
        if (john == null || jane == null) {
            System.out.println("Failed to create customers");
            return;
        }
        
        // Create accounts
        System.out.println("\n2. Creating accounts...");
        Account johnChecking = bank.createAccount(john.getCustomerId(), Account.AccountType.CHECKING, 1000.0);
        Account johnSavings = bank.createAccount(john.getCustomerId(), Account.AccountType.SAVINGS, 5000.0);
        Account janeCredit = bank.createCreditAccount(jane.getCustomerId(), 2000.0);
        
        // Perform transactions
        System.out.println("\n3. Performing transactions...");
        bank.deposit(johnChecking.getAccountNumber(), 500.0);
        bank.withdraw(johnChecking.getAccountNumber(), 200.0);
        bank.transfer(johnSavings.getAccountNumber(), johnChecking.getAccountNumber(), 1000.0);
        
        // Create and manage loan
        System.out.println("\n4. Creating and managing loan...");
        Loan loan = bank.createLoan(john.getCustomerId(), Loan.LoanType.PERSONAL, 
            10000.0, 0.08, 36, "Home improvement");
        if (loan != null) {
            bank.approveLoan(loan.getLoanId());
            bank.activateLoan(loan.getLoanId());
            bank.makeLoanPayment(loan.getLoanId(), loan.getMonthlyPayment());
        }
        
        // Apply interest
        System.out.println("\n5. Applying monthly interest...");
        bank.applyMonthlyInterest();
        
        // Generate reports
        System.out.println("\n6. Generating reports...");
        bank.generateBankSummary();
        bank.generateCustomerReport(john.getCustomerId());
        
        // Show account statements
        System.out.println("\n7. Account statements...");
        johnChecking.printStatement();
        johnSavings.printStatement();
        
        // Show loan details
        if (loan != null) {
            System.out.println("\n8. Loan details...");
            loan.printLoanDetails();
        }
        
        System.out.println("\n=== DEMONSTRATION COMPLETE ===");
        System.out.println("The banking system successfully demonstrates:");
        System.out.println("✓ Customer management");
        System.out.println("✓ Multiple account types (Checking, Savings, Credit)");
        System.out.println("✓ Banking transactions (Deposit, Withdrawal, Transfer)");
        System.out.println("✓ Loan management with payment tracking");
        System.out.println("✓ Interest calculations");
        System.out.println("✓ Comprehensive reporting");
        System.out.println("✓ Transaction history tracking");
    }
}