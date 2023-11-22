package accenture.task.BankingProject.service;

import accenture.task.BankingProject.dao.BankAccountRepository;
import accenture.task.BankingProject.dao.BankRepository;
import accenture.task.BankingProject.exception.BankingProjectApplicationValidationException;
import accenture.task.BankingProject.model.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankRepository bankRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository,
                              BankRepository bankRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankRepository = bankRepository;
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

//    public BankAccount create(BankAccount bankAccount) {
//        return bankAccountRepository.save(bankAccount);
//    }

    public BankAccount create(BankAccount bankAccount, Long bankId) {
        var existingBank = bankRepository.findById(bankId)
                .orElseThrow(() -> new BankingProjectApplicationValidationException("Bank does not exist",
                        "id", "Bank not found", bankId.toString()));
        bankAccount.setBank(existingBank);

        return bankAccountRepository.save(bankAccount);
    }
    public BankAccount getAccountById(Long accountId) {
        return bankAccountRepository.findById(accountId).orElse(null);
    }

//    public void deposit(BankAccount account, double amount) {
//        if (amount > 0) {
//            account.setBalance(account.getBalance() + amount);
//            bankAccountRepository.save(account);
//            System.out.println("Deposit successful. Current balance of account " + account.getAccountNumber() + " is: " + account.getBalance());
//        } else {
//            System.out.println("Deposit failed. The value is invalid!");
//        }
//    }

    public void deposit(Long accountId, Double depositAmount) {
        BankAccount selectedAccount = getAccountById(accountId);
        if (selectedAccount != null && depositAmount>0) {
            selectedAccount.setBalance(selectedAccount.getBalance()+depositAmount);
            bankAccountRepository.save(selectedAccount);
        }
    }

    public void withdrawal(Long accountId, Double withdrawalAmount) {
        BankAccount selectedAccount = getAccountById(accountId);

        if (withdrawalAmount > 0 && selectedAccount.getBalance() - withdrawalAmount >= 0) {
            selectedAccount.setBalance(selectedAccount.getBalance() - withdrawalAmount);
            bankAccountRepository.save(selectedAccount);
            System.out.println("Withdrawal successful. Current balance of account " + selectedAccount.getAccountNumber() + " is: " + selectedAccount.getBalance());
        } else {
            System.out.println("Withdrawal failed. Insufficient balance or withdrawal amount is less than 0.");
        }
    }

    public void transferOperation(Long accountId1, Long accountId2, Double transferredAmount) {
        BankAccount initialAccount = getAccountById(accountId1);
        BankAccount targetAccount = getAccountById(accountId2);

        if (transferredAmount > 0 && transferredAmount <= initialAccount.getBalance()) {
            initialAccount.setBalance(initialAccount.getBalance() - transferredAmount);
            targetAccount.setBalance(targetAccount.getBalance() + transferredAmount);
            bankAccountRepository.save(initialAccount);
            bankAccountRepository.save(targetAccount);
            System.out.println("Transfer successful. Balance after the transaction: " + initialAccount.getBalance());
        } else {
            System.out.println("Transfer failed. Insufficient balance or withdrawal amount is less than 0.");
        }
    }

    public void printBalanceOperation(BankAccount account) {
        if (account != null) {
            System.out.println("Balance for Account " + account.getAccountNumber() + " : " + account.getBalance());
        }
    }
}
