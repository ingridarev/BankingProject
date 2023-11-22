package accenture.task.BankingProject.api.dto;


import accenture.task.BankingProject.model.BankAccount;

import java.util.List;
import java.util.Objects;

public class BankDto {

    private String address;

    private List<BankAccount> accounts;

    public BankDto(){}

    public BankDto(String title) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankDto bankDto = (BankDto) o;
        return Objects.equals(address, bankDto.address) && Objects.equals(accounts, bankDto.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, accounts);
    }

    @Override
    public String toString() {
        return "BankDto{" +
                "address='" + address + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
