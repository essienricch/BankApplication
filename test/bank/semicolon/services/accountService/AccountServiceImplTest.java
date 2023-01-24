package bank.semicolon.services.accountService;

import bank.semicolon.data.model.Account;
import bank.semicolon.data.model.AccountType;
import bank.semicolon.dto.accountDto.requests.*;
import bank.semicolon.dto.accountDto.responses.*;
import bank.semicolon.dto.accountDto.requests.UserAddAccountRequest;
import bank.semicolon.dto.accountDto.requests.UserRemoveAccountRequest;
import bank.semicolon.dto.accountDto.requests.UserSetAccountPinAccessRequest;
import bank.semicolon.dto.accountDto.responses.UserAddAccountResponse;
import bank.semicolon.dto.accountDto.responses.UserRemoveAccountResponse;
import bank.semicolon.dto.accountDto.responses.UserSetAccountPinAccessResponse;
import bank.semicolon.exception.accountException.IllegalAccountReadArgument;
import bank.semicolon.exception.userException.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceImplTest {

    @Autowired
    private AccountServiceImpl accountService;



//    @Test
//    void createAccount_confirmIfUserExist_countIsOneTest() throws IllegalAccountCreationArgument {
//        CreateAccountRequest accountRequest = new CreateAccountRequest(AccountType.DOMICILIARY,"dodo123");
//        CreateAccountResponse accountResponse = new CreateAccountResponse();
//
//        try {
//            accountResponse =  accountService.createAccount(accountRequest);
//        }catch (IllegalAccountCreationArgument e){
//            System.out.println(e.getMessage());
////        }
//
//        //assertEquals(7L, accountService.accountCounts());
//        assertNotNull(accountResponse.getAccountNumber());
//        assertEquals("dodo123",accountResponse.getEmailAddress());
//    }

    @Test
    void findAccountTest() throws IllegalAccountReadArgument {
        try {
            Account savedAza = accountService.findAccount("1799260915");
            //assertNotEquals(savedAza.getAccountNumber(),"5875517028");
            assertNotNull(savedAza);

        }catch (IllegalAccountReadArgument e){
            System.out.println(e.getMessage());
        }

        //assertNotEquals(savedAza.getAccountNumber(),"2339018165");
    }

    @Test
    void makeDeposit_updateAccountBalance() {
        BigDecimal depositAmount = new BigDecimal("500000.0");
        DepositAmountRequest amountRequest = new DepositAmountRequest(depositAmount,"4857392706","0000");
        try{
           DepositAmountResponse amountResponse = accountService.deposit(amountRequest);

            assertEquals(depositAmount,amountResponse.getAmount());
            assertEquals("Deposit successful",amountResponse.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void updateAccountType()  {
        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest(AccountType.CURRENT,"4857392706");
        UpdateAccountResponse accountResponse = new UpdateAccountResponse();

        try {
            accountResponse = accountService.updateAccountType(updateAccountRequest);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
             assertEquals(accountResponse.getAccountType(), AccountType.CURRENT);
            //assertNotNull(accountResponse.getAccountType());
    }

    @Test
    void transferMoneyTest() {
        AccountTransferRequest transferRequest = new AccountTransferRequest("4857392706","0000","1599510064",new BigDecimal("200000.0"));
        AccountTransferResponse accountTransferResponse = new AccountTransferResponse();

        try {
            accountTransferResponse = accountService.transferMoney(transferRequest);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println(accountTransferResponse.getTransferAmount());
        assertNotNull(accountTransferResponse.getMessage());
        assertEquals(new BigDecimal("200000.0"),accountTransferResponse.getTransferAmount());
    }

    @Test
    void withdrawFundsTest() {
        WithdrawAmountRequest withdrawAmountRequest = new WithdrawAmountRequest("4857392706","0000",new BigDecimal("200000"));
        try {
            WithdrawAmountResponse amountResponse = accountService.withdrawal(withdrawAmountRequest);
            assertEquals(amountResponse.getAccountNumber(),"4857392706");
            assertEquals(new BigDecimal("200000"), amountResponse.getAmount());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void showBalanceTest() throws UserNotFoundException, IllegalAccountReadArgument {
       AccountBalanceRequest balanceRequest = new AccountBalanceRequest("1599510064","0000");

       try {
           AccountBalanceResponse balanceResponse = accountService.showBalance(balanceRequest);
           assertEquals(balanceResponse.getAccountNumber(), "1599510064");
           System.out.println(balanceResponse.getAccountBalance().toString());
           System.out.println(balanceResponse.getMessage());
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
    }

    @Test
    void deleteAccountTest() throws IllegalAccountReadArgument {
        DeleteAccountRequest deleteAccountRequest = new DeleteAccountRequest("3632572306");
       try {
           DeleteAccountResponse deleteAccountResponse = accountService.blockAccount(deleteAccountRequest);
           assertEquals("Account blocked successfully",deleteAccountResponse.getMessage());

       }catch (Exception e){
           System.out.println(e.getMessage());
       }
    }

    @Test
    void updateAccountPinTest() {
        ChangeAccountPinRequest accountPinRequest = new ChangeAccountPinRequest("0000", "2020", "2020", "5876764360");
        ChangeAccountPinResponse accountPinResponse = new ChangeAccountPinResponse();
        try {
            accountPinResponse = accountService.changePin(accountPinRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(accountPinResponse.getAccountNumber(),"5876764360");

    }

    @Test
    void setAccountPinTest(){
        UserSetAccountPinAccessResponse setAccountPinAccessResponse = new UserSetAccountPinAccessResponse();
        UserSetAccountPinAccessRequest setAccountPinAccessRequest = new UserSetAccountPinAccessRequest("5876764360","0020","0020");
        try {
            setAccountPinAccessResponse = accountService.setAccountPin(setAccountPinAccessRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertNotNull(setAccountPinAccessResponse.getAccountNumber());
    }

    @Test
    void userAddAccount_countIsOneTest() throws UserNotFoundException, IllegalAccountReadArgument {
        UserAddAccountRequest addAccountRequest = new UserAddAccountRequest(AccountType.SAVINGS,"wisdom2022");
        UserAddAccountResponse userAddAccountResponse = new UserAddAccountResponse();
        try {
            userAddAccountResponse = accountService.userCreateAccount(addAccountRequest);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(userAddAccountResponse.getAccountNumber());
        assertNotNull(userAddAccountResponse.getAccountNumber());
        assertEquals(4, userAddAccountResponse.getAccountSize());
    }


    @Test
    void removeAccount_countIsZeroTest() throws UserNotFoundException, IllegalAccountReadArgument {
        UserRemoveAccountRequest removeAccountRequest = new UserRemoveAccountRequest("dan122","2354461404");
        try {
            UserRemoveAccountResponse response = accountService.removeAccount(removeAccountRequest);
            assertEquals(3, response.getCount());
            assertEquals(response.getEmailAddress(),"dan122");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    }