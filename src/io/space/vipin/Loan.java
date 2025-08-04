package io.space.vipin;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Loan {
    public enum LoanType {
        PERSONAL, MORTGAGE, AUTO, EDUCATION, BUSINESS
    }
    
    public enum LoanStatus {
        PENDING, APPROVED, ACTIVE, PAID_OFF, DEFAULTED, REJECTED
    }
    
    private String loanId;
    private LoanType loanType;
    private LoanStatus status;
    private Customer customer;
    private double principalAmount;
    private double currentBalance;
    private double interestRate; // Annual interest rate
    private int termInMonths;
    private double monthlyPayment;
    private LocalDate startDate;
    private LocalDate nextPaymentDate;
    private LocalDate maturityDate;
    private List<LoanPayment> paymentHistory;
    private String purpose;
    private double totalInterestPaid;
    private int paymentsRemaining;
    
    public Loan(String loanId, LoanType loanType, Customer customer, double principalAmount,
                double interestRate, int termInMonths, String purpose) {
        this.loanId = loanId;
        this.loanType = loanType;
        this.customer = customer;
        this.principalAmount = principalAmount;
        this.currentBalance = principalAmount;
        this.interestRate = interestRate;
        this.termInMonths = termInMonths;
        this.purpose = purpose != null ? purpose : "";
        this.status = LoanStatus.PENDING;
        this.paymentHistory = new ArrayList<>();
        this.totalInterestPaid = 0.0;
        this.paymentsRemaining = termInMonths;
        
        // Calculate monthly payment using standard loan formula
        calculateMonthlyPayment();
    }
    
    private void calculateMonthlyPayment() {
        if (interestRate == 0) {
            // Interest-free loan
            monthlyPayment = principalAmount / termInMonths;
        } else {
            double monthlyRate = interestRate / 12;
            monthlyPayment = principalAmount * 
                (monthlyRate * Math.pow(1 + monthlyRate, termInMonths)) /
                (Math.pow(1 + monthlyRate, termInMonths) - 1);
        }
    }
    
    public void approveLoan() {
        if (status == LoanStatus.PENDING) {
            status = LoanStatus.APPROVED;
            System.out.println("Loan " + loanId + " has been approved.");
        }
    }
    
    public void activateLoan() {
        if (status == LoanStatus.APPROVED) {
            status = LoanStatus.ACTIVE;
            startDate = LocalDate.now();
            nextPaymentDate = startDate.plusMonths(1);
            maturityDate = startDate.plusMonths(termInMonths);
            System.out.println("Loan " + loanId + " has been activated and funds disbursed.");
        }
    }
    
    public boolean makePayment(double paymentAmount) {
        if (status != LoanStatus.ACTIVE) {
            System.out.println("Cannot make payment. Loan is not active.");
            return false;
        }
        
        if (paymentAmount <= 0) {
            System.out.println("Payment amount must be positive.");
            return false;
        }
        
        if (paymentAmount > currentBalance) {
            System.out.println("Payment amount exceeds remaining balance. Adjusting to remaining balance.");
            paymentAmount = currentBalance;
        }
        
        // Calculate interest and principal portions
        double monthlyInterestRate = interestRate / 12;
        double interestPortion = currentBalance * monthlyInterestRate;
        double principalPortion = paymentAmount - interestPortion;
        
        // Ensure principal portion is not negative
        if (principalPortion < 0) {
            principalPortion = paymentAmount;
            interestPortion = 0;
        }
        
        // Update balances
        currentBalance -= principalPortion;
        totalInterestPaid += interestPortion;
        paymentsRemaining--;
        
        // Record payment
        LoanPayment payment = new LoanPayment(paymentAmount, principalPortion, 
            interestPortion, currentBalance);
        paymentHistory.add(payment);
        
        // Update next payment date
        if (currentBalance > 0.01) { // Small threshold for floating point precision
            nextPaymentDate = nextPaymentDate.plusMonths(1);
        } else {
            // Loan is paid off
            status = LoanStatus.PAID_OFF;
            currentBalance = 0;
            nextPaymentDate = null;
            System.out.println("Congratulations! Loan " + loanId + " has been paid off.");
        }
        
        System.out.println("Payment processed successfully.");
        System.out.println("Principal: $" + String.format("%.2f", principalPortion) + 
            ", Interest: $" + String.format("%.2f", interestPortion));
        System.out.println("Remaining balance: $" + String.format("%.2f", currentBalance));
        
        return true;
    }
    
    public double calculateTotalInterest() {
        return (monthlyPayment * termInMonths) - principalAmount;
    }
    
    public double calculateRemainingInterest() {
        return (monthlyPayment * paymentsRemaining) - currentBalance;
    }
    
    public boolean isOverdue() {
        return status == LoanStatus.ACTIVE && 
               nextPaymentDate != null && 
               LocalDate.now().isAfter(nextPaymentDate);
    }
    
    public long getDaysOverdue() {
        if (!isOverdue()) return 0;
        return ChronoUnit.DAYS.between(nextPaymentDate, LocalDate.now());
    }
    
    public void printLoanDetails() {
        System.out.println("\n=== LOAN DETAILS ===");
        System.out.println("Loan ID: " + loanId);
        System.out.println("Loan Type: " + loanType);
        System.out.println("Status: " + status);
        System.out.println("Borrower: " + customer.getName());
        System.out.println("Purpose: " + (purpose.isEmpty() ? "Not specified" : purpose));
        System.out.println("Original Amount: $" + String.format("%.2f", principalAmount));
        System.out.println("Current Balance: $" + String.format("%.2f", currentBalance));
        System.out.println("Interest Rate: " + String.format("%.2f", interestRate * 100) + "% annual");
        System.out.println("Term: " + termInMonths + " months");
        System.out.println("Monthly Payment: $" + String.format("%.2f", monthlyPayment));
        
        if (startDate != null) {
            System.out.println("Start Date: " + startDate);
            System.out.println("Maturity Date: " + maturityDate);
        }
        
        if (nextPaymentDate != null) {
            System.out.println("Next Payment Due: " + nextPaymentDate);
            if (isOverdue()) {
                System.out.println("*** OVERDUE by " + getDaysOverdue() + " days ***");
            }
        }
        
        System.out.println("Payments Remaining: " + paymentsRemaining);
        System.out.println("Total Interest Paid: $" + String.format("%.2f", totalInterestPaid));
        System.out.println("Estimated Total Interest: $" + String.format("%.2f", calculateTotalInterest()));
        System.out.println("Number of Payments Made: " + paymentHistory.size());
        System.out.println("====================\n");
    }
    
    public void printPaymentHistory() {
        System.out.println("\n=== PAYMENT HISTORY ===");
        System.out.println("Loan ID: " + loanId);
        if (paymentHistory.isEmpty()) {
            System.out.println("No payments made yet.");
        } else {
            System.out.println("Payment #\tDate\t\tAmount\t\tPrincipal\tInterest\tBalance");
            System.out.println("------------------------------------------------------------------------");
            for (int i = 0; i < paymentHistory.size(); i++) {
                LoanPayment payment = paymentHistory.get(i);
                System.out.printf("%d\t\t%s\t$%.2f\t\t$%.2f\t\t$%.2f\t\t$%.2f%n",
                    i + 1,
                    payment.getPaymentDate(),
                    payment.getPaymentAmount(),
                    payment.getPrincipalPortion(),
                    payment.getInterestPortion(),
                    payment.getBalanceAfter());
            }
        }
        System.out.println("=======================\n");
    }
    
    // Getters
    public String getLoanId() { return loanId; }
    public LoanType getLoanType() { return loanType; }
    public LoanStatus getStatus() { return status; }
    public Customer getCustomer() { return customer; }
    public double getPrincipalAmount() { return principalAmount; }
    public double getCurrentBalance() { return currentBalance; }
    public double getInterestRate() { return interestRate; }
    public int getTermInMonths() { return termInMonths; }
    public double getMonthlyPayment() { return monthlyPayment; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getNextPaymentDate() { return nextPaymentDate; }
    public LocalDate getMaturityDate() { return maturityDate; }
    public List<LoanPayment> getPaymentHistory() { return new ArrayList<>(paymentHistory); }
    public String getPurpose() { return purpose; }
    public double getTotalInterestPaid() { return totalInterestPaid; }
    public int getPaymentsRemaining() { return paymentsRemaining; }
    
    // Setters (limited for loan integrity)
    public void setStatus(LoanStatus status) { this.status = status; }
    public void setPurpose(String purpose) { this.purpose = purpose != null ? purpose : ""; }
    
    @Override
    public String toString() {
        return String.format("Loan[%s, %s, $%.2f, %s, %s]", 
            loanId, loanType, currentBalance, status, customer.getName());
    }
    
    // Inner class for loan payments
    public static class LoanPayment {
        private LocalDate paymentDate;
        private double paymentAmount;
        private double principalPortion;
        private double interestPortion;
        private double balanceAfter;
        
        public LoanPayment(double paymentAmount, double principalPortion, 
                          double interestPortion, double balanceAfter) {
            this.paymentDate = LocalDate.now();
            this.paymentAmount = paymentAmount;
            this.principalPortion = principalPortion;
            this.interestPortion = interestPortion;
            this.balanceAfter = balanceAfter;
        }
        
        // Getters
        public LocalDate getPaymentDate() { return paymentDate; }
        public double getPaymentAmount() { return paymentAmount; }
        public double getPrincipalPortion() { return principalPortion; }
        public double getInterestPortion() { return interestPortion; }
        public double getBalanceAfter() { return balanceAfter; }
    }
}