package bank.semicolon.services.accountService;

import bank.semicolon.data.model.Account;
import bank.semicolon.dto.accountDto.requests.*;
import bank.semicolon.dto.accountDto.responses.*;
import bank.semicolon.dto.accountDto.requests.UserAddAccountRequest;
import bank.semicolon.dto.accountDto.requests.UserRemoveAccountRequest;
import bank.semicolon.dto.accountDto.requests.UserSetAccountPinAccessRequest;
import bank.semicolon.dto.accountDto.responses.UserAddAccountResponse;
import bank.semicolon.dto.accountDto.responses.UserRemoveAccountResponse;
import bank.semicolon.dto.accountDto.responses.UserSetAccountPinAccessResponse;
import bank.semicolon.exception.accountException.*;
import bank.semicolon.exception.userException.UserNotFoundException;


import java.util.List;

public interface IAccountService {

    UserAddAccountResponse userCreateAccount(UserAddAccountRequest userAddAccountRequest)
            throws UserNotFoundException, IllegalAccountReadArgument;
    Account findAccount(String accountNumber) throws IllegalAccountReadArgument;
    List <Account> findAllAccounts();
    Account updateAccount(Account account);
    ChangeAccountPinResponse changePin(ChangeAccountPinRequest accountPinRequest) throws IllegalAccountReadArgument,  AccountServiceException;

    UserRemoveAccountResponse removeAccount(UserRemoveAccountRequest userRemoveAccount)
            throws UserNotFoundException, IllegalAccountReadArgument;
    UpdateAccountResponse updateAccountType(UpdateAccountRequest updateAccountRequest) throws IllegalAccountReadArgument,  AccountServiceException;
    DeleteAccountResponse blockAccount(DeleteAccountRequest deleteAccountRequest) throws IllegalAccountReadArgument;
    UserSetAccountPinAccessResponse setAccountPin(UserSetAccountPinAccessRequest setAccountPinAccessRequest)
         throws IllegalAccountReadArgument, AccountServiceException;
    long accountCounts();
    AccountBalanceResponse showBalance(AccountBalanceRequest accountBalanceRequest) throws IllegalAccountReadArgument, UserNotFoundException, AccountServiceException;
    AccountTransferResponse transferMoney(AccountTransferRequest accountTransferRequest) throws IllegalAccountReadArgument,  AccountServiceException;
    DepositAmountResponse deposit(DepositAmountRequest depositAmountRequest) throws IllegalAccountReadArgument, AccountServiceException;
    WithdrawAmountResponse withdrawal(WithdrawAmountRequest withdrawAmountRequest) throws IllegalAccountReadArgument, AccountServiceException;

}
