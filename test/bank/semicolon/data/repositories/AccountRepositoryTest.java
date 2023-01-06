package bank.semicolon.data.repositories;

import bank.semicolon.data.model.Account;
import bank.semicolon.data.model.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    void setUp(){
        account = new Account(AccountType.SAVINGS,"wes234");
    }


    @Test
    void saveAccountTest(){
        accountRepository.save(account);
        assertEquals(1L,accountRepository.count());
        assertNotNull(account);
    }


}