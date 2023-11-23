package accenture.task.BankingProject.api;

import accenture.task.BankingProject.api.dto.ErrorDto;
import accenture.task.BankingProject.api.dto.ErrorFieldDto;
import accenture.task.BankingProject.api.dto.Mapper.ErrorFieldMapper;
import accenture.task.BankingProject.exception.BankAccountNotFoundException;
import accenture.task.BankingProject.exception.BankNotFoundException;
import accenture.task.BankingProject.exception.BankingProjectApplicationServiceDisabledException;
import accenture.task.BankingProject.exception.BankingProjectApplicationValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(HttpServletRequest request, Exception ex) {
        logger.info("SQLException Occurred:: URL=" + request.getRequestURL());
        return "database_error";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occurred")
    @ExceptionHandler(IOException.class)
    public void handleIOException() {
        logger.error("IOException handler executed");
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorDto> handleDataAccessException(HttpServletRequest request, DataAccessException dataAccessException) {
        logger.error("DataAccessException: {}. Cause?: {}",
                dataAccessException.getMessage(), dataAccessException.getMostSpecificCause().getMessage());

        var errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var errorFields = List.of(
                new ErrorFieldDto("sql", dataAccessException.getMostSpecificCause().getMessage(), null)
        );
        var errorDto = new ErrorDto(request.getRequestURL().toString(),
                errorFields,
                dataAccessException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now());
        return ResponseEntity.internalServerError().body(errorDto);
    }

    @ExceptionHandler(BankingProjectApplicationValidationException.class)
    public ResponseEntity<ErrorDto> handleBankingProjectValidationException(HttpServletRequest request, BankingProjectApplicationValidationException bankingProjectApplicationValidationException) {
        logger.error("BankingProjectApplicationValidationException: {}, for field: {}", bankingProjectApplicationValidationException.getMessage(), bankingProjectApplicationValidationException.getField());

        var errorStatus = HttpStatus.BAD_REQUEST;

        var errorFields = List.of(
                new ErrorFieldDto(bankingProjectApplicationValidationException.getField(), bankingProjectApplicationValidationException.getError(), bankingProjectApplicationValidationException.getRejectedValue())
        );

        var errorDto = new ErrorDto(request.getRequestURL().toString(),
                errorFields,
                bankingProjectApplicationValidationException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorDto);
    }


    @ExceptionHandler(BankingProjectApplicationServiceDisabledException.class)
    public ResponseEntity<Void> handleBankingProjectApplicationServiceDisabledException(HttpServletRequest request, BankingProjectApplicationServiceDisabledException bankingProjectApplicationServiceDisabledException) {
        logger.error("BankingProjectApplicationServiceDisabledException: {}", bankingProjectApplicationServiceDisabledException.getMessage());

        var errorStatus = HttpStatus.SERVICE_UNAVAILABLE;

        return new ResponseEntity<>(errorStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException notValidException) {
        logger.error("MethodArgumentNotValidException: {}", notValidException.getMessage());

        var errorStatus = HttpStatus.BAD_REQUEST;

        var errorFields = notValidException.getBindingResult()
                .getAllErrors().stream()
                .map(ErrorFieldMapper::toErrorFieldDto)
                .collect(toList());

        var errorDto = new ErrorDto(request.getRequestURL().toString(),
                errorFields,
                notValidException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "We do not support this")
    @ExceptionHandler(HttpMediaTypeException.class)
    public void handleHttpMediaTypeException(HttpMediaTypeException mediaTypeException) {
        logger.error("Not supported request resulted in HttpMediaTypeException: {}", mediaTypeException.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something really bad happened")
    @ExceptionHandler(Exception.class)
    public void handleAllExceptions(Exception exception) {
        logger.error("All Exceptions handler: {}", exception.getMessage());
    }

    @ExceptionHandler(BankNotFoundException.class)
    public ResponseEntity<ErrorDto> handleBankNotFoundException(HttpServletRequest request, BankNotFoundException bankNotFoundException) {
        logger.error("BankNotFoundException: {}", bankNotFoundException.getMessage());

        var errorStatus = HttpStatus.NOT_FOUND;

        var errorFields = List.of(
                new ErrorFieldDto("bankId", "Bank not found", bankNotFoundException.getBankId().toString())
        );

        var errorDto = new ErrorDto(request.getRequestURL().toString(),
                errorFields,
                bankNotFoundException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now());
        return ResponseEntity.status(errorStatus).body(errorDto);
    }

    @ExceptionHandler(BankAccountNotFoundException.class)
    public ResponseEntity<ErrorDto> handleBankAccountNotFoundException(HttpServletRequest request, BankAccountNotFoundException ex) {
        logger.error("BankAccountNotFoundException: {}", ex.getMessage());

        var errorStatus = HttpStatus.NOT_FOUND;

        var errorFields = List.of(
                new ErrorFieldDto("accountId", ex.getMessage(), ex.getAccountId().toString())
        );

        var errorDto = new ErrorDto(request.getRequestURL().toString(),
                errorFields,
                ex.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now());
        return ResponseEntity.status(errorStatus).body(errorDto);
    }


}