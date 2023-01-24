package bank.semicolon.services.accountService;

import bank.semicolon.data.model.Account;
import bank.semicolon.data.model.User_Entity;
import bank.semicolon.data.repositories.AccountRepository;
import bank.semicolon.data.repositories.UserRepository;
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
import bank.semicolon.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService{

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private SecurityConfig config;
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              UserRepository userRepository,
                              SecurityConfig config) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.config = config;
    }


    @Override
    public UserAddAccountResponse userCreateAccount(UserAddAccountRequest userAddAccountRequest) throws UserNotFoundException,
            IllegalAccountReadArgument {
        User_Entity savedUserEntity = userRepository.findUser_EntitiesByEmailAddress(userAddAccountRequest.getEmailAddress());
        UserAddAccountResponse accountResponse = new UserAddAccountResponse();
        if (savedUserEntity != null){
            Account newAccount = new Account(userAddAccountRequest.getAccountType(), userAddAccountRequest.getEmailAddress());
            newAccount.generateAccountNumber();
            Account savedAcct = accountRepository.save(newAccount);
            savedUserEntity.addAccount(savedAcct);
            userRepository.save(savedUserEntity);
            accountResponse.setAccountSize(listOfAccount(savedUserEntity.getEmailAddress()));
            accountResponse.setAccountNumber(savedAcct.getAccountNumber());
            accountResponse.setMessage("Account Created Successfully");
            return accountResponse;
        }else throw new IllegalAccountReadArgument("User not found");
    }


    private int listOfAccount(String emailAddress) {
        int count = 0;
        for (Account account: accountRepository.findAll()){
            if (account.getEmailAddress().equals(emailAddress)){
                count++;
            }
        }return count;
    }

    @Override
    public Account findAccount(String accountNumber) throws IllegalAccountReadArgument {
        return accountRepository.findAccountByAccountNumber(accountNumber);
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
    public ChangeAccountPinResponse changePin(ChangeAccountPinRequest accountPinRequest)
            throws IllegalAccountReadArgument,AccountServiceException {

        ChangeAccountPinResponse accountPinResponse = new ChangeAccountPinResponse();
        if (passwordMatch(accountPinRequest.getNewPin(), accountPinRequest.getConfirmNewPin())) {
            Account savedAccount = findAccount(accountPinRequest.getAccountNumber());
            if (savedAccount != null) {
                if (accountPinMatch(savedAccount, accountPinRequest.getOldPin())) {
                    savedAccount.setAccountPin(config.passwordEncoder().encode(accountPinRequest.getNewPin()));
                    accountPinResponse.setAccountNumber(savedAccount.getAccountNumber());
                    accountPinResponse.setMessage("Pin change successful");
                    return accountPinResponse;
                }else throw new AccountServiceException("Pin Incorrect");
            }else throw new IllegalAccountReadArgument("Account not found");
        }else throw new AccountServiceException("Pin does not match");
    }

    @Override
    public UserSetAccountPinAccessResponse setAccountPin(UserSetAccountPinAccessRequest setAccountPinAccessRequest)
            throws IllegalAccountReadArgument, AccountServiceException {

        UserSetAccountPinAccessResponse setAccountPinAccessResponse = new UserSetAccountPinAccessResponse();
        if (pinMatch(setAccountPinAccessRequest.getPin(), setAccountPinAccessRequest.getConfirmPin())){
            Account savedAccount = accountRepository.findAccountByAccountNumber(setAccountPinAccessRequest.getAccountNumber());
                if (savedAccount != null){
                    savedAccount.setAccountPin(config.passwordEncoder().encode(setAccountPinAccessRequest.getPin()));
                    Account updatedAcct = updateAccount(savedAccount);
                    setAccountPinAccessResponse.setAccountNumber(updatedAcct.getAccountNumber());
                    setAccountPinAccessResponse.setMessage("Pin set successful");
                    return setAccountPinAccessResponse;
                }else throw new IllegalAccountReadArgument("Account does not exist");
        }else throw new AccountServiceException("Pin does not match");
    }

    private boolean pinMatch(String pin, String confirmPin){
        return pin.equals(confirmPin);
    }


    private boolean passwordMatch(String newPin, String confirmNewPin){
        return newPin.equals(confirmNewPin);
    }
    private boolean accountPinMatch(Account account, String oldPin){
        return config.passwordEncoder().matches(oldPin, account.getAccountPin());
    }

    @Override
    public UpdateAccountResponse updateAccountType(UpdateAccountRequest updateAccountRequest) throws IllegalAccountReadArgument,
            AccountServiceException {
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
            }else throw new AccountServiceException("Account Type already exist");
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
    public AccountBalanceResponse showBalance(AccountBalanceRequest accountBalanceRequest) throws IllegalAccountReadArgument,
            AccountServiceException {
        Account saveAccount = findAccount(accountBalanceRequest.getAccountNumber());
        if (saveAccount != null){
            if (accountPinMatch(saveAccount,accountBalanceRequest.getPinCode())) {
                User_Entity savedUserEntity = userRepository.findUser_EntitiesByEmailAddress(saveAccount.getEmailAddress());
                AccountBalanceResponse balanceResponse = new AccountBalanceResponse();
                balanceResponse.setAccount_Holder(savedUserEntity.getFirstName() + "  " +savedUserEntity.getLastName());
                balanceResponse.setAccountBalance(saveAccount.getBalance());
                balanceResponse.setAccountNumber(saveAccount.getAccountNumber());
                return balanceResponse;
            }else throw new AccountServiceException("Incorrect pin");
        }else throw new IllegalAccountReadArgument("Account does not exist");

    }

    @Override
    public AccountTransferResponse transferMoney(AccountTransferRequest accountTransferRequest) throws IllegalAccountReadArgument, AccountServiceException {
        AccountTransferResponse transferResponse = new AccountTransferResponse();
        Account senderAccount = findAccount(accountTransferRequest.getSenderAccountNumber());
        Account receiverAccount = findAccount(accountTransferRequest.getReceiverAccountNumber());
        if (receiverAccount != null && senderAccount != null){
            if (accountPinMatch(senderAccount,accountTransferRequest.getPinCode())){
               BigDecimal amountTransferred = senderAccount.transfer(accountTransferRequest.getTransferAmount());
                if (amountTransferred != null){

                    receiverAccount.topAmount(amountTransferred);
                    updateAccount(senderAccount);
                    updateAccount(receiverAccount);

                   transferResponse.setTransferAmount(accountTransferRequest.getTransferAmount());
                   transferResponse.setMessage("Transfer Successful");
                   return transferResponse;
                }else throw new AccountServiceException("Pin incorrect");
            }else throw new AccountServiceException("Transfer not complete, Balance is less than transfer amount");
        }else throw new IllegalAccountReadArgument("Invalid Account Request");
    }

     public DepositAmountResponse deposit(DepositAmountRequest depositAmountRequest) throws IllegalAccountReadArgument, AccountServiceException {
        Account newAza = findAccount(depositAmountRequest.getAccountNumber());
        if (newAza != null){
            if (accountPinMatch(newAza,depositAmountRequest.getPinCode())) {
                newAza.topAmount(depositAmountRequest.getDepositAmount());
                updateAccount(newAza);
                DepositAmountResponse depositAmountResponse = new DepositAmountResponse();
                depositAmountResponse.setAmount(depositAmountRequest.getDepositAmount());
                depositAmountResponse.setMessage("Deposit successful");
                return depositAmountResponse;
            }else throw new AccountServiceException("Incorrect pin");
        }else throw new IllegalAccountReadArgument(" Transaction not complete,   Account does not exist");
    }

    @Override
    public WithdrawAmountResponse withdrawal(WithdrawAmountRequest withdrawAmountRequest) throws IllegalAccountReadArgument, AccountServiceException {
        Account recipientAccount = findAccount(withdrawAmountRequest.getAccountNumber());
        if (recipientAccount != null){
            if (accountPinMatch(recipientAccount, withdrawAmountRequest.getPinCode())){
           BigDecimal confirmWithdrawAmount = recipientAccount.withdrawAmount(withdrawAmountRequest.getWithdrawAmount());
            if (confirmWithdrawAmount != null){
                updateAccount(recipientAccount);
                WithdrawAmountResponse withdrawAmountResponse = new WithdrawAmountResponse();
                withdrawAmountResponse.setAmount(confirmWithdrawAmount);
                withdrawAmountResponse.setAccountNumber(recipientAccount.getAccountNumber());
                withdrawAmountResponse.setMessage("Withdrawal successful");
                return withdrawAmountResponse;
            }else throw new AccountServiceException("Incorrect pin");
            }else throw new AccountServiceException("Cant complete transaction. Withdrawal amount is greater than balance");
        }else throw new IllegalAccountReadArgument("Invalid Account");
    }

    @Override
    public UserRemoveAccountResponse removeAccount(UserRemoveAccountRequest userRemoveAccount) throws UserNotFoundException, IllegalAccountReadArgument {
        UserRemoveAccountResponse accountResponse = new UserRemoveAccountResponse();
        User_Entity savedUserEntity = userRepository.findUser_EntitiesByEmailAddress(userRemoveAccount.getEmailAddress());
        if (savedUserEntity != null){
            Account savedAccount = accountRepository.findAccountByAccountNumber(userRemoveAccount.getAccountNumber());
                if (savedAccount != null){
                    savedUserEntity.removeAccount(savedAccount);
                    accountRepository.delete(savedAccount);

                    accountResponse.setCount(listOfAccount(savedUserEntity.getEmailAddress()));
                    accountResponse.setEmailAddress(savedUserEntity.getEmailAddress());
                    accountResponse.setMessage("Account removed");
                    return accountResponse;
                }else throw new IllegalAccountReadArgument("Account does not exist");
        }else throw new UserNotFoundException("User_Entity does not exist");
    }
}
