package accenture.task.BankingProject.api.dto;

import accenture.task.BankingProject.model.Bank;

import java.util.Objects;

public class BankAccountEntityDto extends BankAccountDto {
    private Long id;

    public BankAccountEntityDto(){}

    public BankAccountEntityDto(Long id) {
        this.id = id;
    }

    public BankAccountEntityDto(Double balance, Integer accountNumber, Bank bank, Long id) {
        super(balance, accountNumber, bank);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BankAccountEntityDto that = (BankAccountEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "BankAccountEntityDto{" +
                "id=" + id +
                '}';
    }
}
