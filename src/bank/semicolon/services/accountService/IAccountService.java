package bank.semicolon.services.accountService;

import bank.semicolon.data.model.Account;
import bank.semicolon.dto.accountDto.requests.*;
import bank.semicolon.dto.accountDto.responses.*;
import bank.semicolon.exception.accountException.*;
import bank.semicolon.exception.userException.IllegalUserReadArgument;


import java.util.List;

public interface IAccountService {

   // CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) throws IllegalAccountCreationArgument;
    Account findAccount(String accountNumber) throws IllegalAccountReadArgument;
    List <Account> findAllAccounts();
    Account updateAccount(Account account);
    ChangeAccountPinResponse changePin(ChangeAccountPinRequest accountPinRequest) throws IllegalAccountReadArgument, IllegalChangeOfPinArgument;

    UpdateAccountResponse updateAccountType(UpdateAccountRequest updateAccountRequest) throws IllegalAccountReadArgument, IllegalAccountUpdateArgument;
    DeleteAccountResponse blockAccount(DeleteAccountRequest deleteAccountRequest) throws IllegalAccountReadArgument;

    long accountCounts();
    AccountBalanceResponse showBalance(AccountBalanceRequest accountBalanceRequest) throws IllegalAccountReadArgument, IllegalUserReadArgument;
    AccountTransferResponse transferMoney(AccountTransferRequest accountTransferRequest) throws IllegalAccountReadArgument, IllegalTransferAmountArgument;
    DepositAmountResponse deposit(DepositAmountRequest depositAmountRequest) throws IllegalAccountReadArgument, IllegalDepositArgument;
    WithdrawAmountResponse withdrawal(WithdrawAmountRequest withdrawAmountRequest) throws IllegalAccountReadArgument, IllegalWithdrawAmountArgument;

}
