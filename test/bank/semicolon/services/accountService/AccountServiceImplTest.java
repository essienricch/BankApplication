package bank.semicolon.services.accountService;

import bank.semicolon.data.model.Account;
import bank.semicolon.data.model.AccountType;
import bank.semicolon.dtos.accountDto.requests.*;
import bank.semicolon.dtos.accountDto.responses.*;
import bank.semicolon.exception.accountException.*;
import bank.semicolon.exception.userException.IllegalUserReadArgument;
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
            Account savedAza = accountService.findAccount("5875517028");
            //assertNotEquals(savedAza.getAccountNumber(),"5875517028");
            assertNull(savedAza);

        }catch (IllegalAccountReadArgument e){
            System.out.println(e.getMessage());
        }

        //assertNotEquals(savedAza.getAccountNumber(),"2339018165");
    }

    @Test
    void makeDeposit_updateAccountBalance() throws IllegalAccountReadArgument {
        BigDecimal depositAmount = new BigDecimal("500000.0");
        DepositAmountRequest amountRequest = new DepositAmountRequest(depositAmount,"4857392706");
        try{
           DepositAmountResponse amountResponse = accountService.deposit(amountRequest);

            assertEquals(depositAmount,amountResponse.getAmount());
            assertEquals("Deposit successful",amountResponse.getMessage());
        }catch (IllegalAccountReadArgument e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void updateAccountType() throws IllegalAccountReadArgument, IllegalAccountUpdateArgument {
        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest(AccountType.CURRENT,"4857392706");
        UpdateAccountResponse  accountResponse = new UpdateAccountResponse();

        try {
            accountResponse = accountService.updateAccountType(updateAccountRequest);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
             assertEquals(accountResponse.getAccountType(), AccountType.CURRENT);
            //assertNotNull(accountResponse.getAccountType());
    }

    @Test
    void transferMoneyTest() throws IllegalAccountReadArgument, IllegalTransferAmountArgument {
        AccountTransferRequest transferRequest = new AccountTransferRequest("4857392706","1599510064",new BigDecimal("200000.0"));
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
    void withdrawFundsTest() throws IllegalWithdrawAmountArgument, IllegalAccountReadArgument {
        WithdrawAmountRequest withdrawAmountRequest = new WithdrawAmountRequest("4857392706",new BigDecimal("200000"));
        try {
            WithdrawAmountResponse amountResponse = accountService.withdrawal(withdrawAmountRequest);
            assertEquals(amountResponse.getAccountNumber(),"4857392706");
            assertEquals(new BigDecimal("200000"), amountResponse.getAmount());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void showBalanceTest() throws IllegalUserReadArgument, IllegalAccountReadArgument {
       AccountBalanceRequest balanceRequest = new AccountBalanceRequest("1599510064");

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
        DeleteAccountRequest deleteAccountRequest = new DeleteAccountRequest("5875517028");
       try {
           DeleteAccountResponse deleteAccountResponse = accountService.blockAccount(deleteAccountRequest);
           assertEquals("Account blocked successfully",deleteAccountResponse.getMessage());

       }catch (Exception e){
           System.out.println(e.getMessage());
       }

    }

}