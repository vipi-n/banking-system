package io.space.vipin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDate registrationDate;
    private List<String> accountNumbers;
    private boolean isActive;
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    
    // Phone number validation pattern (supports various formats)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[+]?[1-9]?[0-9]{7,15}$"
    );
    
    public Customer(String customerId, String name, String email, String phoneNumber, 
                   String address, LocalDate dateOfBirth) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        if (dateOfBirth != null && dateOfBirth.isAfter(LocalDate.now().minusYears(18))) {
            throw new IllegalArgumentException("Customer must be at least 18 years old");
        }
        
        this.customerId = customerId;
        this.name = name.trim();
        this.email = email.toLowerCase().trim();
        this.phoneNumber = phoneNumber.trim();
        this.address = address != null ? address.trim() : "";
        this.dateOfBirth = dateOfBirth;
        this.registrationDate = LocalDate.now();
        this.accountNumbers = new ArrayList<>();
        this.isActive = true;
    }
    
    // Simplified constructor for basic customer creation
    public Customer(String customerId, String name, String email, String phoneNumber) {
        this(customerId, name, email, phoneNumber, "", null);
    }
    
    public void addAccount(String accountNumber) {
        if (accountNumber != null && !accountNumbers.contains(accountNumber)) {
            accountNumbers.add(accountNumber);
        }
    }
    
    public void removeAccount(String accountNumber) {
        accountNumbers.remove(accountNumber);
    }
    
    public boolean hasAccounts() {
        return !accountNumbers.isEmpty();
    }
    
    public void updateContactInfo(String email, String phoneNumber, String address) {
        if (email != null && isValidEmail(email)) {
            this.email = email.toLowerCase().trim();
        }
        if (phoneNumber != null && isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber.trim();
        }
        if (address != null) {
            this.address = address.trim();
        }
    }
    
    public int getAge() {
        if (dateOfBirth == null) {
            return 0;
        }
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
    
    public void printCustomerInfo() {
        System.out.println("\n=== CUSTOMER INFORMATION ===");
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Address: " + (address.isEmpty() ? "Not provided" : address));
        System.out.println("Date of Birth: " + (dateOfBirth != null ? dateOfBirth : "Not provided"));
        System.out.println("Age: " + (dateOfBirth != null ? getAge() : "Unknown"));
        System.out.println("Registration Date: " + registrationDate);
        System.out.println("Status: " + (isActive ? "Active" : "Inactive"));
        System.out.println("Number of Accounts: " + accountNumbers.size());
        if (!accountNumbers.isEmpty()) {
            System.out.println("Account Numbers: " + String.join(", ", accountNumbers));
        }
        System.out.println("============================\n");
    }
    
    private static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber.replaceAll("[-\\s()]", "")).matches();
    }
    
    // Getters
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public List<String> getAccountNumbers() { return new ArrayList<>(accountNumbers); }
    public boolean isActive() { return isActive; }
    
    // Setters
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        }
    }
    
    public void setAddress(String address) {
        this.address = address != null ? address.trim() : "";
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null || !dateOfBirth.isAfter(LocalDate.now().minusYears(18))) {
            this.dateOfBirth = dateOfBirth;
        }
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    @Override
    public String toString() {
        return String.format("Customer[ID: %s, Name: %s, Email: %s, Accounts: %d]", 
            customerId, name, email, accountNumbers.size());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return customerId.equals(customer.customerId);
    }
    
    @Override
    public int hashCode() {
        return customerId.hashCode();
    }
}