package accenture.task.BankingProject.model;

import com.itextpdf.text.DocumentException;

import javax.persistence.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @OneToMany(mappedBy = "bank", orphanRemoval = true)
    private List<BankAccount> accounts = new ArrayList<>();

    public Bank(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(id, bank.id) && Objects.equals(address, bank.address) && Objects.equals(accounts, bank.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, accounts);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", accounts=" + accounts +
                '}';
    }

    //    private static void createAccount(Scanner scanner) {
//        System.out.println("Enter initial balance for the new account:");
//        BigDecimal initialBalance = scanner.nextBigDecimal();
//        BankAccount newAccount = new BankAccount(initialBalance);
//        accounts.add(newAccount);
//        System.out.println("New account created with account number: " + newAccount.getAccountNumber() + " and initial balance of : " + newAccount.getBalance());
//    }
//
//    static List<BankAccount> getAllAccounts() {
//        return accounts;
//    }
//
//    private static void printAccountList() {
//        for (BankAccount bankAccount : accounts) {
//            System.out.print(bankAccount.getAccountNumber() + " ");
//        }
//    }
//
//    private static BankAccount selectAccount(Scanner scanner) {
//        printAccountList();
//
//        int accountNumberToSelect = scanner.nextInt();
//        for (BankAccount account : accounts) {
//            if (account.getAccountNumber() == accountNumberToSelect) {
//                return account;
//            }
//        }
//        System.out.println("Invalid account selection.");
//        return null;
//    }
//
//
//    private static boolean isValidAccountIndex(int index) {
//        return index > 0 && index <= accounts.size();
//    }
//
//    private static void depositOperation(Scanner scanner) {
//        System.out.println("Choose the account to deposit to:");
//        BankAccount selectedAccount = selectAccount(scanner);
//
//        System.out.println("Enter deposit amount:");
//        BigDecimal depositAmount = scanner.nextBigDecimal();
//        selectedAccount.deposit(depositAmount);
//    }
//
//    private static void withdrawalOperation(Scanner scanner) {
//        System.out.println("Choose the account to withdraw from:");
//        BankAccount selectedAccount = selectAccount(scanner);
//
//        System.out.println("Enter withdrawal amount:");
//        BigDecimal withdrawalAmount = scanner.nextBigDecimal();
//        selectedAccount.withdraw(withdrawalAmount);
//
//    }
//
//    private static void printBalanceOperation(Scanner scanner) {
//        BankAccount selectedAccount = selectAccount(scanner);
//
//        if (selectedAccount != null) {
//            System.out.println("Balance for Account " + selectedAccount.getAccountNumber() + " : " + selectedAccount.getBalance());
//        }
//    }
//
//    private static void transferOperation(Scanner scanner) {
//        System.out.println("Choose the source account for the transfer:");
//        BankAccount sourceAccount = selectAccount(scanner);
//
//        System.out.println("Choose the target account for the transfer:");
//        BankAccount targetAccount = selectAccount(scanner);
//
//        System.out.println("Enter transfer amount:");
//        BigDecimal transferAmount = scanner.nextBigDecimal();
//        sourceAccount.transfer(targetAccount, transferAmount);
//
//    }
//
//    private static void printAllAccounts() {
//        System.out.println("All Accounts:");
//        for (BankAccount account : accounts) {
////            System.out.println("Account " + account.getAccountNumber() + ": " + account.getBalance());
//            System.out.println(account.getAccountNumber() + " : " + account.getBalance());
//        }
//        System.out.println();
//    }


//    private static void exportAccountBalances() {
//        try (PrintWriter writer = new PrintWriter(new FileWriter("file.txt"))) {
//            for (BankAccount account : accounts) {
//                writer.println("Account " + account.getAccountNumber() + " : " + account.getBalance());
//            }
//            System.out.println("Account balances exported to file.txt successfully");
//            System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
//        } catch (IOException e) {
//            System.out.println("Error exporting account balances: " + e.getMessage());
//        }
//    }
//
//    private static void importAccountsFromFile() {
//        System.out.println("Enter the path to the file containing account information:");
//        String filePath = scanner.next();
//
//        try {
//            List<String> lines = Files.readAllLines(Path.of(filePath));
//            for (String line : lines) {
//                String[] entity = line.split("\\s+");
//                if (entity.length == 4) {
//                    String accountNumber = entity[1];
//                    BigDecimal balance = new BigDecimal(entity[3]);
//
//                    Integer accountNumberInt = Integer.parseInt(accountNumber);
//
//                    BankAccount account = new BankAccount(balance);
//                    account.setAccountNumber(accountNumberInt);
//                    accounts.add(account);
//                }
//            }
//            System.out.println("Accounts imported successfully.");
//        } catch (IOException e) {
//            System.out.println("Error importing accounts from file: " + e.getMessage());
//        }
//    }
//
//    public static void main(String[] args) throws DocumentException, IOException {
//
//        Bank bank = new Bank();
//        BankAccount acc1 = new BankAccount(new BigDecimal("500"));
//        BankAccount acc2 = new BankAccount();
//        accounts.add(acc1);
//        accounts.add(acc2);
//
//        PdfService pdfService = new PdfService();
//
//        while (true) {
//            System.out.println("1. Create an Account");
//            System.out.println("2. Deposit");
//            System.out.println("3. Withdraw");
//            System.out.println("4. Print Balance");
//            System.out.println("5. Transfer balance to another account");
//            System.out.println("6. Print All Accounts");
//            System.out.println("7. Export All Accounts with balances to TXT");
//            System.out.println("8. Export All Accounts with balances to PDF");
//            System.out.println("9. Import accounts from file");
//            System.out.println("10. Exit");
//            System.out.println("Enter your selection:");
//
//            int selection = scanner.nextInt();
//
//            switch (selection) {
//                case 1 -> createAccount(scanner);
//                case 2 -> depositOperation(scanner);
//                case 3 -> withdrawalOperation(scanner);
//                case 4 -> printBalanceOperation(scanner);
//                case 5 -> transferOperation(scanner);
//                case 6 -> printAllAccounts();
//                case 7 -> exportAccountBalances();
//                case 8 -> PdfService.writeToAFile(bank);
//                case 9 -> importAccountsFromFile();
//                case 10 -> {
//                    System.out.println("Exiting.");
//                    System.exit(0);
//                }
//                default -> System.out.println("Invalid selection. Please choose a valid option.");
//            }
//
//
//        }
//
//    }
}

