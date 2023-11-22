package accenture.task.BankingProject.api;

import accenture.task.BankingProject.api.dto.BankAccountDto;
import accenture.task.BankingProject.api.dto.BankDto;
import accenture.task.BankingProject.model.Bank;
import accenture.task.BankingProject.model.BankAccount;
import accenture.task.BankingProject.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static accenture.task.BankingProject.api.dto.Mapper.BankAccountMapper.toBankAccount;
import static accenture.task.BankingProject.api.dto.Mapper.BankAccountMapper.toBankAccountDto;
import static accenture.task.BankingProject.api.dto.Mapper.BankMapper.toBank;
import static accenture.task.BankingProject.api.dto.Mapper.BankMapper.toBankDto;
import static org.springframework.http.ResponseEntity.ok;

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
        var createdBankAccount = bankAccountService.create(toBankAccount(bankAccountDto), bankId);

        return ok(toBankAccountDto(createdBankAccount));
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable Long accountId, @RequestParam Double depositAmount) {
        bankAccountService.deposit(accountId, depositAmount);
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable Long accountId, @RequestParam Double withdrawalAmount) {
        bankAccountService.withdrawal(accountId, withdrawalAmount);
        return ResponseEntity.ok("Withdrawal successful");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferAmount(
            @RequestParam Long initialAccountId,
            @RequestParam Long targetAccountId,
            @RequestParam Double transferredAmount) {

        try {
            bankAccountService.transferOperation(initialAccountId, targetAccountId, transferredAmount);
            return new ResponseEntity<>("Transfer successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
