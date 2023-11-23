package accenture.task.BankingProject.exception;
public class BankAccountNotFoundException extends RuntimeException {

    private Long accountId;

    public BankAccountNotFoundException(Long accountId) {
        super("Bank Account not found with id: " + accountId);
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
