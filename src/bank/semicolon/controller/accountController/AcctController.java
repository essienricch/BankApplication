package bank.semicolon.controller.accountController;

import bank.semicolon.dto.accountDto.requests.*;
import bank.semicolon.dto.accountDto.responses.*;
import bank.semicolon.exception.accountException.AccountServiceException;
import bank.semicolon.exception.accountException.IllegalAccountReadArgument;
import bank.semicolon.exception.userException.UserNotFoundException;
import bank.semicolon.services.accountService.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AcctController {

    private AccountServiceImpl accountService;

    @Autowired
    public AcctController(AccountServiceImpl accountService) {
        this.accountService = accountService;

    }

    @PostMapping("/create")
    public ResponseEntity <UserAddAccountResponse> createAccount(@RequestBody UserAddAccountRequest addAccountRequest) throws UserNotFoundException, IllegalAccountReadArgument {
        return new ResponseEntity<>(accountService.userCreateAccount(addAccountRequest), new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping("/getbalance")
    public ResponseEntity<AccountBalanceResponse> accountBalanceRequest(@RequestBody AccountBalanceRequest accountBalanceRequest) throws AccountServiceException,  IllegalAccountReadArgument {
        return new ResponseEntity<>(accountService.showBalance(accountBalanceRequest), HttpStatus.FOUND);
    }

    @PostMapping("/pin")
    public ResponseEntity<UserSetAccountPinAccessResponse> setAccountPin(@RequestBody UserSetAccountPinAccessRequest accountPinAccessRequest) throws AccountServiceException, IllegalAccountReadArgument {
        return new ResponseEntity<>(accountService.setAccountPin(accountPinAccessRequest), HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositAmountResponse> makeDeposit(@RequestBody DepositAmountRequest depositAmountRequest) throws AccountServiceException, IllegalAccountReadArgument {
        return new ResponseEntity<>(accountService.deposit(depositAmountRequest), HttpStatus.ACCEPTED);
    }

    @PostMapping("/transfer")
    public ResponseEntity <AccountTransferResponse> makeTransfer(@RequestBody AccountTransferRequest transferRequest) throws AccountServiceException, IllegalAccountReadArgument {
        return new ResponseEntity<>(accountService.transferMoney(transferRequest), HttpStatus.OK);
    }
}
