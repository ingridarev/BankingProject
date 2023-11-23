package accenture.task.BankingProject.service;

import accenture.task.BankingProject.dao.BankAccountRepository;
import accenture.task.BankingProject.dao.BankRepository;
import accenture.task.BankingProject.model.Bank;
import accenture.task.BankingProject.model.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    private final BankRepository bankRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankService(BankRepository bankRepository, BankAccountRepository bankAccountRepository) {
        this.bankRepository = bankRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    public Bank getBankById(Long bankId) {
        return bankRepository.findById(bankId).orElse(null);
    }

    public Bank create(Bank bank) {
        return bankRepository.save(bank);
    }

    public List<BankAccount> getAllAccounts(Long bankId) {
        Bank bank = bankRepository.findById(bankId).orElse(null);
            return bank.getAccounts();
        }
}
