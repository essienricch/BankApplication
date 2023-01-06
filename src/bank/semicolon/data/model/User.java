package bank.semicolon.data.model;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class User {


    @DBRef
    private List<Account> accounts;
    private String firstName;
    private String lastName;

    @Id
    private String emailAddress;

    @Getter
    private String password;



    public User(){
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account){
       accounts.add(account);
    }

    public void removeAccount(Account account){
        accounts.removeIf(savedAccount -> savedAccount.equals(account));
    }




}
