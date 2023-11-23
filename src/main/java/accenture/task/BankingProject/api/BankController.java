package accenture.task.BankingProject.api;

import accenture.task.BankingProject.api.dto.BankDto;
import accenture.task.BankingProject.exception.BankNotFoundException;
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

import java.util.ArrayList;
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
    public ResponseEntity<List<Bank>> getAllBanks() {
        try {
            List<Bank> bankList = new ArrayList<>(bankService.getAllBanks());

            if (bankList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(bankList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{bankId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Bank> getBankById(@PathVariable Long bankId) {
        Bank bank = bankService.getBankById(bankId);

        if (bank != null) {
            return new ResponseEntity<>(bank, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BankDto> createNewBank(@RequestBody BankDto bankDto) {
        try {
            Bank createdBank = bankService.create(toBank(bankDto));
            return ResponseEntity.ok(toBankDto(createdBank));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{bankId}/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<BankAccount>> getAllAccountsByBankId(@PathVariable Long bankId) {
        try {
            List<BankAccount> accounts = bankService.getAllAccounts(bankId);
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (BankNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value = "/{bankId}/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<List<BankAccount>> getBankAccountByBankId(@PathVariable Long bankId) {
//        List<BankAccount> bankAccounts = bankService.getAllAccounts(bankId);
//        return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
//    }
}
