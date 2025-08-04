# Comprehensive Banking System

A full-featured banking system implemented in Java that provides all normal banking operations including customer management, multiple account types, transactions, loans, and comprehensive reporting.

## Features

### 🏦 Core Banking Features
- **Customer Management**: Create, update, search, and manage customer information
- **Multiple Account Types**: 
  - Checking Accounts (1% annual interest)
  - Savings Accounts (2.5% annual interest)
  - Credit Accounts (18% annual interest, customizable credit limits)
- **Banking Transactions**: Deposits, withdrawals, transfers between accounts
- **Transaction History**: Complete audit trail of all banking activities
- **Balance Inquiries**: Real-time account balance checking

### 💰 Loan Management
- **Loan Types**: Personal, Mortgage, Auto, Education, Business loans
- **Loan Lifecycle**: Application → Approval → Activation → Payment tracking
- **Payment Calculations**: Automatic monthly payment calculation using standard loan formulas
- **Payment History**: Detailed tracking of principal and interest payments
- **Overdue Tracking**: Automatic detection and reporting of overdue loans

### 📊 Reporting & Analytics
- **Bank Summary Reports**: Overview of customers, accounts, loans, and financial metrics
- **Customer Reports**: Detailed customer profiles with all accounts and loans
- **Account Statements**: Transaction history and account details
- **Loan Statements**: Payment history and loan details
- **Overdue Loan Reports**: Track delinquent accounts
- **Inactive Account Reports**: Monitor dormant accounts

### 🔧 Administrative Functions
- **Interest Application**: Automatic monthly interest calculation for savings accounts
- **Account Management**: Open, close, and manage account status
- **Customer Search**: Find customers by ID or name
- **System Statistics**: Comprehensive banking system metrics

## System Architecture

### Class Structure

```
BankingSystem (Main Controller)
├── Customer (Customer management)
├── Account (Account operations)
│   ├── AccountType (CHECKING, SAVINGS, CREDIT)
│   └── Transaction history integration
├── Loan (Loan management)
│   ├── LoanType (PERSONAL, MORTGAGE, AUTO, EDUCATION, BUSINESS)
│   ├── LoanStatus (PENDING, APPROVED, ACTIVE, PAID_OFF, DEFAULTED, REJECTED)
│   └── LoanPayment (Payment tracking)
└── Transaction (Transaction tracking)
    └── TransactionType (DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT, INTEREST, FEE, LOAN_PAYMENT, LOAN_DISBURSEMENT)
```

### Key Design Principles
- **Encapsulation**: Proper data hiding and controlled access
- **Validation**: Input validation for emails, phone numbers, and business rules
- **Transaction Integrity**: Proper handling of failed operations
- **Audit Trail**: Complete transaction history tracking
- **Extensibility**: Easy to add new account types, loan types, and features

## Getting Started

### Prerequisites
- Java 8 or higher
- Any Java IDE or command line tools

### Compilation and Execution

```bash
# Compile all Java files
javac -d out src/io/space/vipin/*.java

# Run the interactive banking system
java -cp out io.space.vipin.Main

# Run the demonstration (non-interactive)
java -cp out io.space.vipin.BankingSystemTest
```

### Sample Usage

The system includes sample data for demonstration:
- **Customer 1**: John Doe (Checking + Savings accounts, Personal loan)
- **Customer 2**: Jane Smith (Credit account)

## Menu System

### Main Menu Options
1. **Customer Management**: Create, find, update customers
2. **Account Management**: Create accounts, view statements, close accounts
3. **Transactions**: Deposits, withdrawals, transfers, balance inquiries
4. **Loan Management**: Apply for loans, make payments, view loan details
5. **Reports**: Bank summary, customer reports, overdue loans
6. **Admin Functions**: Apply interest, system statistics

## Banking Operations

### Account Types
- **Checking Account**: Basic transaction account with 1% annual interest
- **Savings Account**: Higher interest account (2.5% annual) for savings
- **Credit Account**: Revolving credit with customizable limits and 18% annual interest

### Transaction Types
- **Deposits**: Add money to any account
- **Withdrawals**: Remove money (with balance/credit limit validation)
- **Transfers**: Move money between accounts
- **Interest**: Automatic monthly interest application
- **Loan Payments**: Principal and interest calculations

### Loan Features
- **Standard Loan Formula**: Accurate monthly payment calculations
- **Multiple Loan Types**: Personal, mortgage, auto, education, business
- **Payment Tracking**: Separate principal and interest tracking
- **Overdue Detection**: Automatic identification of late payments
- **Early Payoff**: Support for paying off loans early

## Data Validation

### Customer Validation
- Email format validation using regex patterns
- Phone number format validation
- Age validation (18+ years required)
- Required field validation

### Transaction Validation
- Positive amount validation
- Sufficient funds checking
- Account status validation
- Credit limit enforcement

### Loan Validation
- Payment amount validation
- Loan status checking
- Interest rate calculations
- Term validation

## Error Handling

The system includes comprehensive error handling:
- Invalid input handling with user-friendly messages
- Transaction failure recovery
- Account status validation
- Loan payment validation
- System state consistency checks

## Future Enhancements

Potential areas for expansion:
- **Data Persistence**: File or database storage
- **Security**: User authentication and authorization
- **Web Interface**: REST API or web-based UI
- **Notifications**: Email/SMS alerts for transactions
- **Investment Accounts**: Stock trading and portfolio management
- **International Banking**: Multi-currency support
- **Mobile Banking**: Mobile app integration
- **ATM Simulation**: ATM transaction processing

## Testing

The system includes a comprehensive test class (`BankingSystemTest.java`) that demonstrates:
- Customer creation and management
- Account creation for all types
- Various banking transactions
- Loan application and payment process
- Interest calculation
- Report generation
- System validation

## License

This project is created for educational and demonstration purposes.

## Contributing

This is a demonstration project showcasing comprehensive banking system implementation in Java. The code is well-documented and follows object-oriented design principles.