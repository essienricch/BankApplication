package bank.semicolon.services.accountService;

import bank.semicolon.data.model.Account;
import bank.semicolon.data.model.User;
import bank.semicolon.data.repositories.AccountRepository;
import bank.semicolon.data.repositories.UserRepository;
import bank.semicolon.dtos.accountDto.requests.*;
import bank.semicolon.dtos.accountDto.responses.*;
import bank.semicolon.exception.accountException.*;
import bank.semicolon.exception.userException.IllegalUserReadArgument;
import bank.semicolon.services.userService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService{


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

//    @Override
//    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) throws IllegalAccountCreationArgument {
//        CreateAccountResponse accountResponse = new CreateAccountResponse();
//        if (userRepository.existsById(createAccountRequest.getEmailAddress())){
//            Account account = new Account(createAccountRequest.getAccountType(), createAccountRequest.getEmailAddress());
//            account.generateAccountNumber();
//            accountRepository.save(account);
//            accountResponse.setAccountNumber(account.getAccountNumber());
//            accountResponse.setEmailAddress(account.getEmailAddress());
//            accountResponse.setMessage("Account Created Successfully");
//            System.out.println(accountResponse.getAccountNumber());
//            return accountResponse;
//        }else throw new IllegalAccountCreationArgument("User does not exist");
//    }

    @Override
    public Account findAccount(String accountNumber) throws IllegalAccountReadArgument {
        if(accountRepository.existsById(accountNumber)){
               for (Account account: accountRepository.findAccountByAccountNumber(accountNumber)){
                   return account;
               }
        }else throw new IllegalAccountReadArgument("Account does not exist");
        return null;
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public UpdateAccountResponse updateAccountType(UpdateAccountRequest updateAccountRequest) throws IllegalAccountReadArgument, IllegalAccountUpdateArgument {
        Account updatedAza = findAccount(updateAccountRequest.getAccountNumber());
        if (updatedAza != null){
            if (!updatedAza.getAccountType().equals(updateAccountRequest.getAccountType())){
                updatedAza.setAccountType(updateAccountRequest.getAccountType());
                Account update = updateAccount(updatedAza);
               UpdateAccountResponse updateAccountResponse = new UpdateAccountResponse();
               updateAccountResponse.setAccountNumber(update.getAccountNumber());
               updateAccountResponse.setAccountType(update.getAccountType());
               updateAccountResponse.setMessage("Update Successful");
               return updateAccountResponse;
            }else throw new IllegalAccountUpdateArgument("Account Type already exist");
        }else throw new IllegalAccountReadArgument("Account does not exist");
    }

    @Override
    public DeleteAccountResponse blockAccount(DeleteAccountRequest deleteAccountRequest) throws IllegalAccountReadArgument {

         Account savedAccount = findAccount(deleteAccountRequest.getAccountNumber());

         if (savedAccount != null){
               accountRepository.delete(savedAccount);
               DeleteAccountResponse deleteAccountResponse = new DeleteAccountResponse();
               deleteAccountResponse.setMessage("Account blocked successfully");
               return deleteAccountResponse;

        }else throw new IllegalAccountReadArgument("Account does not exist");
    }


    @Override
    public long accountCounts() {
        return accountRepository.count();
    }

    @Override
    public AccountBalanceResponse showBalance(AccountBalanceRequest accountBalanceRequest) throws IllegalAccountReadArgument, IllegalUserReadArgument {
        Account saveAccount = findAccount(accountBalanceRequest.getAccountNumber());
        if (saveAccount != null){
            User savedUser = userService.findUserByEmail(saveAccount.getEmailAddress());
            AccountBalanceResponse balanceResponse = new AccountBalanceResponse();
               balanceResponse.setMessage( saveAccount.toString(savedUser));
               balanceResponse.setAccountBalance(saveAccount.getBalance());
               balanceResponse.setAccountNumber(saveAccount.getAccountNumber());
                return balanceResponse;

        }else throw new IllegalAccountReadArgument("Account does not exist");

    }

    @Override
    public AccountTransferResponse transferMoney(AccountTransferRequest accountTransferRequest) throws IllegalAccountReadArgument, IllegalTransferAmountArgument {
        AccountTransferResponse transferResponse = new AccountTransferResponse();
        Account senderAccount = findAccount(accountTransferRequest.getSenderAccountNumber());
        Account receiverAccount = findAccount(accountTransferRequest.getReceiverAccountNumber());
        if (receiverAccount != null && senderAccount != null){
           BigDecimal amountTransferred = senderAccount.transfer(accountTransferRequest.getTransferAmount());
            if (amountTransferred != null){

                receiverAccount.topAmount(amountTransferred);
                updateAccount(senderAccount);
                updateAccount(receiverAccount);

               transferResponse.setTransferAmount(accountTransferRequest.getTransferAmount());
               transferResponse.setMessage("Transfer Successful");
               return transferResponse;
            }else throw new IllegalTransferAmountArgument("Transfer not complete, Balance is less than transfer amount");
        }else throw new IllegalAccountReadArgument("Invalid Account Request");
    }

     public DepositAmountResponse deposit(DepositAmountRequest depositAmountRequest) throws IllegalAccountReadArgument {
        Account newAza = findAccount(depositAmountRequest.getAccountNumber());
        if (newAza != null){
//            newAza.setBalance(depositAmountRequest.getDepositAmount());
            newAza.topAmount(depositAmountRequest.getDepositAmount());
            updateAccount(newAza);
            DepositAmountResponse depositAmountResponse = new DepositAmountResponse();
            depositAmountResponse.setAmount(depositAmountRequest.getDepositAmount());
            depositAmountResponse.setMessage("Deposit successful");
            return depositAmountResponse;
        }else throw new IllegalAccountReadArgument(" Transaction not complete,   Account does not exist");
    }

    @Override
    public WithdrawAmountResponse withdrawal(WithdrawAmountRequest withdrawAmountRequest) throws IllegalAccountReadArgument, IllegalWithdrawAmountArgument {
        Account recipientAccount = findAccount(withdrawAmountRequest.getAccountNumber());
        if (recipientAccount != null){
           BigDecimal confirmWithdrawAmount = recipientAccount.withdrawAmount(withdrawAmountRequest.getWithdrawAmount());
            if (confirmWithdrawAmount != null){
                updateAccount(recipientAccount);
                WithdrawAmountResponse withdrawAmountResponse = new WithdrawAmountResponse();
                withdrawAmountResponse.setAmount(confirmWithdrawAmount);
                withdrawAmountResponse.setAccountNumber(recipientAccount.getAccountNumber());
                withdrawAmountResponse.setMessage("Withdrawal successful");
                return withdrawAmountResponse;
            }else throw new IllegalWithdrawAmountArgument("Cant complete transaction. Withdrawal amount is greater than balance");
        }else throw new IllegalAccountReadArgument("Invalid Account");
    }
}
