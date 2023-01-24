package bank.semicolon.data.repositories;

import bank.semicolon.data.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends MongoRepository <Account, String> {

    Account findAccountByAccountNumber(String accountNumber);

}
