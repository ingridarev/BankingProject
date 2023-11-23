package accenture.task.BankingProject.exception;

public class BankNotFoundException extends RuntimeException {

    private Long bankId;

    public BankNotFoundException(Long bankId) {
        super("Bank not found with id: " + bankId);
        this.bankId = bankId;
    }

    public Long getBankId() {
        return bankId;
    }
}
