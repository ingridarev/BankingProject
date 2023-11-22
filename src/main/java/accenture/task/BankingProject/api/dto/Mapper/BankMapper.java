package accenture.task.BankingProject.api.dto.Mapper;

import accenture.task.BankingProject.api.dto.BankDto;
import accenture.task.BankingProject.api.dto.BankEntityDto;
import accenture.task.BankingProject.model.Bank;

public class BankMapper {

    public static BankDto toBankDto(Bank bank) {
        var bankDto = new BankDto();

        bankDto.setAddress(bank.getAddress());
        bankDto.setAccounts(bank.getAccounts());

        return bankDto;
    }

    public static BankEntityDto toBankEntityDto(Bank bank) {
        var bankDto = new BankEntityDto();

        bankDto.setId(bank.getId());
        bankDto.setAddress(bank.getAddress());
        bankDto.setAccounts(bank.getAccounts());
        return bankDto;
    }

    public static Bank toBank(BankDto bankDto) {
        var bank = new Bank();

        bank.setAddress(bankDto.getAddress());
        bank.setAccounts(bankDto.getAccounts());
        return bank;
    }

    public static Bank toBankEntityDto(BankEntityDto bankDto) {
        var bank = new Bank();

        bank.setId(bankDto.getId());
        bank.setAddress(bankDto.getAddress());
        bank.setAccounts(bankDto.getAccounts());
        return bank;
    }

}
