package accenture.task.BankingProject.service;

import accenture.task.BankingProject.api.BankController;
import accenture.task.BankingProject.dao.BankAccountRepository;
import accenture.task.BankingProject.dao.BankRepository;
import accenture.task.BankingProject.exception.BankNotFoundException;
import accenture.task.BankingProject.model.Bank;
import accenture.task.BankingProject.model.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private final Logger logger = LoggerFactory.getLogger(BankService.class);
    private final BankRepository bankRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankService(BankRepository bankRepository, BankAccountRepository bankAccountRepository) {
        this.bankRepository = bankRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<Bank> getAllBanks() {
        try {
            return bankRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving all banks.", e);
        }
    }

    public Bank getBankById(Long bankId) {
        try {
            return bankRepository.findById(bankId).orElseThrow(() -> new BankNotFoundException(bankId));
        } catch (Exception e) {
            logger.warn("Something went wrong with bank: ID {} ", bankId);
            throw new RuntimeException("Error occurred while retrieving bank with ID: " + bankId, e);
        }
    }

    public Bank create(Bank bank) {
        try {
            return bankRepository.save(bank);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating a bank.", e);
        }
    }
    public List<BankAccount> getAllAccounts(Long bankId) {
        try {
            Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new BankNotFoundException(bankId));
            return bank.getAccounts();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving accounts for bank with ID: " + bankId, e);
        }
    }

}
