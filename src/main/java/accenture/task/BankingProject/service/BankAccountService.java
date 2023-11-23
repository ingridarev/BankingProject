package accenture.task.BankingProject.service;

import accenture.task.BankingProject.dao.BankAccountRepository;
import accenture.task.BankingProject.dao.BankRepository;
import accenture.task.BankingProject.exception.BankAccountNotFoundException;
import accenture.task.BankingProject.exception.BankingProjectApplicationValidationException;
import accenture.task.BankingProject.model.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {
    private final Logger logger = LoggerFactory.getLogger(BankAccountService.class);

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


    public BankAccount create(BankAccount bankAccount, Long bankId) {
        try {
            var existingBank = bankRepository.findById(bankId)
                    .orElseThrow(() -> new BankingProjectApplicationValidationException("Bank does not exist",
                            "id", "Bank not found", bankId.toString()));
            bankAccount.setBank(existingBank);

            return bankAccountRepository.save(bankAccount);
        } catch (Exception e) {
            logger.error("Error while creating a bank account for bank with ID: {}. Error: {}", bankId, e.getMessage(), e);
            throw new RuntimeException("Error while creating a bank account for bank with ID: " + bankId, e);
        }
    }

    public BankAccount getAccountById(Long accountId) {
        try {
            return bankAccountRepository.findById(accountId)
                    .orElseThrow(() -> new BankAccountNotFoundException(accountId));
        } catch (Exception e) {
            logger.error("Error while getting bank account with ID: {}. Error: {}", accountId, e.getMessage(), e);
            throw new RuntimeException("Error while getting bank account with ID: " + accountId, e);
        }
    }

    public void deposit(Long accountId, Double depositAmount) {
        try {
            BankAccount selectedAccount = getAccountById(accountId);
            if (selectedAccount != null && depositAmount > 0) {
                selectedAccount.setBalance(selectedAccount.getBalance() + depositAmount);
                bankAccountRepository.save(selectedAccount);
            }
        } catch (Exception e) {
            logger.error("Error while processing deposit for account with ID: {}. Error: {}", accountId, e.getMessage(), e);
            throw new RuntimeException("Error while processing deposit for account with ID: " + accountId, e);
        }
    }


    public void withdrawal(Long accountId, Double withdrawalAmount) {
        try {
            BankAccount selectedAccount = getAccountById(accountId);

            if (withdrawalAmount > 0 && selectedAccount.getBalance() - withdrawalAmount >= 0) {
                selectedAccount.setBalance(selectedAccount.getBalance() - withdrawalAmount);
                bankAccountRepository.save(selectedAccount);
                logger.info("Withdrawal successful. Current balance of account {} is: {}", selectedAccount.getAccountNumber(), selectedAccount.getBalance());
            } else {
                logger.warn("Withdrawal failed. Insufficient balance or withdrawal amount is less than 0.");
            }
        } catch (Exception e) {
            logger.error("Error while processing withdrawal for account with ID: {}. Error: {}", accountId, e.getMessage(), e);
            throw new RuntimeException("Error while processing withdrawal for account with ID: " + accountId, e);
        }
    }


    public void transferOperation(Long accountId1, Long accountId2, Double transferredAmount) {
        try {
            BankAccount initialAccount = getAccountById(accountId1);
            BankAccount targetAccount = getAccountById(accountId2);

            if (transferredAmount > 0 && transferredAmount <= initialAccount.getBalance()) {
                initialAccount.setBalance(initialAccount.getBalance() - transferredAmount);
                targetAccount.setBalance(targetAccount.getBalance() + transferredAmount);
                bankAccountRepository.save(initialAccount);
                bankAccountRepository.save(targetAccount);
                logger.info("Transfer successful. Balance after the transaction: {}", initialAccount.getBalance());
            } else {
                logger.warn("Transfer failed. Insufficient balance or withdrawal amount is less than 0.");
            }
        } catch (Exception e) {
            logger.error("Error occurred while processing transfer for accounts with ID: {} and {}. Error: {}", accountId1, accountId2, e.getMessage(), e);
            throw new RuntimeException("Error occurred while processing transfer for accounts with ID: " + accountId1 + " and " + accountId2, e);
        }
    }


    public void printBalanceOperation(BankAccount account) {
        if (account != null) {
            System.out.println("Balance for Account " + account.getAccountNumber() + " : " + account.getBalance());
        }
    }
}
