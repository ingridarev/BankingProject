package accenture.task.BankingProject.api.dto.Mapper;


import accenture.task.BankingProject.api.dto.BankAccountDto;
import accenture.task.BankingProject.api.dto.BankAccountEntityDto;
import accenture.task.BankingProject.model.BankAccount;

public class BankAccountMapper {

    public static BankAccountDto toBankAccountDto(BankAccount bankAccount) {
        var bankAccountDto = new BankAccountDto();

        bankAccountDto.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountDto.setBalance(bankAccount.getBalance());
        bankAccountDto.setBank(bankAccount.getBank());

        return bankAccountDto;
    }

    public static BankAccountEntityDto toBankAccountEntityDto(BankAccount bankAccount) {
        var bankAccountDto = new BankAccountEntityDto();
        bankAccountDto.setId(bankAccount.getId());
        bankAccountDto.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountDto.setBalance(bankAccount.getBalance());
        bankAccountDto.setBank(bankAccount.getBank());

        return bankAccountDto;
    }

    public static BankAccount toBankAccount(BankAccountDto bankAccountDto) {
        var bankAccount = new BankAccount();

        bankAccount.setAccountNumber(bankAccountDto.getAccountNumber());
        bankAccount.setBalance(bankAccountDto.getBalance());
        bankAccount.setBank(bankAccountDto.getBank());

        return bankAccount;
    }

    public static BankAccount toBankAccountEntityDto(BankAccountEntityDto bankAccountDto) {
        var bankAccount = new BankAccount();

        bankAccount.setId(bankAccountDto.getId());
        bankAccount.setAccountNumber(bankAccountDto.getAccountNumber());
        bankAccount.setBalance(bankAccountDto.getBalance());
        bankAccount.setBank(bankAccountDto.getBank());

        return bankAccount;
    }
}
