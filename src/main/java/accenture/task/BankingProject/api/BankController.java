package accenture.task.BankingProject.api;

import accenture.task.BankingProject.api.dto.BankDto;
import accenture.task.BankingProject.model.Bank;
import accenture.task.BankingProject.model.BankAccount;
import accenture.task.BankingProject.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static accenture.task.BankingProject.api.dto.Mapper.BankMapper.toBank;
import static accenture.task.BankingProject.api.dto.Mapper.BankMapper.toBankDto;

@RestController
@RequestMapping("/api/v1/banks")
public class BankController {

    private final Logger logger = LoggerFactory.getLogger(BankController.class);
    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Bank> getAllBanks() {
        return bankService.getAllBanks();
    }

    @GetMapping(value = "/{bankId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Bank> getBankById(@PathVariable Long bankId) {

        Bank bank = bankService.getBankById(bankId);
        logger.info("Retrieved bank with ID {} successfully.", bankId);
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BankDto> createNewBank(@RequestBody BankDto bankDto) {
        Bank createdBank = bankService.create(toBank(bankDto));
        return ResponseEntity.ok(toBankDto(createdBank));
    }

    @GetMapping(value = "/{bankId}/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BankAccount> getAllAccountsByBankId(@PathVariable Long bankId) {
        return bankService.getAllAccounts(bankId);
    }
}
