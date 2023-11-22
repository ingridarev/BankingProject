package accenture.task.BankingProject.api.dto;

import accenture.task.BankingProject.model.Bank;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

public class BankAccountDto {

    private Double balance;

    private Integer accountNumber;

    @JsonIgnore
    private Bank bank;

    public BankAccountDto(Double balance, Integer accountNumber, Bank bank) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.bank = bank;
    }

    public BankAccountDto() {
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccountDto that = (BankAccountDto) o;
        return Objects.equals(balance, that.balance) && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(bank, that.bank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, accountNumber, bank);
    }

    @Override
    public String toString() {
        return "BankAccountDto{" +
                "balance=" + balance +
                ", accountNumber=" + accountNumber +
                ", bank=" + bank +
                '}';
    }
}
