package accenture.task.BankingProject.api;

import accenture.task.BankingProject.api.dto.BankAccountDto;
import accenture.task.BankingProject.exception.BankAccountNotFoundException;
import accenture.task.BankingProject.exception.DepositFailedException;
import accenture.task.BankingProject.exception.WithdrawalFailedException;
import accenture.task.BankingProject.model.BankAccount;
import accenture.task.BankingProject.service.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static accenture.task.BankingProject.api.dto.Mapper.BankAccountMapper.toBankAccount;
import static accenture.task.BankingProject.api.dto.Mapper.BankAccountMapper.toBankAccountDto;

@RestController
@RequestMapping("/api/v1/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<BankAccount> getBankAccountById(@PathVariable Long accountId) {
        BankAccount bankAccount = bankAccountService.getAccountById(accountId);

        if (bankAccount != null) {
            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<BankAccountDto> createBankAccount(@PathVariable Long bankId, @RequestBody BankAccountDto bankAccountDto) {
//        BankAccount createdBankAccount = bankAccountService.create(toBankAccount(bankAccountDto));
//        createdBankAccount.setBank();
//        return ResponseEntity.ok(toBankAccountDto(createdBankAccount));
//    }

    @PostMapping(value = "/{bankId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BankAccountDto> createBankAccount(@RequestBody BankAccountDto bankAccountDto, @PathVariable Long bankId) {
        try {
            var createdBankAccount = bankAccountService.create(toBankAccount(bankAccountDto), bankId);

            return ResponseEntity.ok(toBankAccountDto(createdBankAccount));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable Long accountId, @RequestParam Double depositAmount) {
        try {
            bankAccountService.deposit(accountId, depositAmount);
            return ResponseEntity.ok("Deposit successful");
        } catch (DepositFailedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (BankAccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable Long accountId, @RequestParam Double withdrawalAmount) {
        try {
            bankAccountService.withdrawal(accountId, withdrawalAmount);
            return ResponseEntity.ok("Withdrawal successful");
        } catch (WithdrawalFailedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (BankAccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferAmount(
            @RequestParam Long initialAccountId,
            @RequestParam Long targetAccountId,
            @RequestParam Double transferredAmount) {

        try {
            bankAccountService.transferOperation(initialAccountId, targetAccountId, transferredAmount);
            return new ResponseEntity<>("Transfer successful", HttpStatus.OK);
         } catch (BankAccountNotFoundException e) {
            return new ResponseEntity<>("Transfer failed: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Transfer failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
