package accenture.task.BankingProject.exception;

public class BankingProjectApplicationValidationException extends RuntimeException{

    private String field;
    private String error;

    private String rejectedValue;

    public BankingProjectApplicationValidationException() {
    }

    public BankingProjectApplicationValidationException(String message, String field, String error, String rejectedValue) {
        super(message);
        this.field = field;
        this.error = error;
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }
}