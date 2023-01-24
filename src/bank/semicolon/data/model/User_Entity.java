package bank.semicolon.data.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Document(collection = "user")
public class User_Entity {


    @DBRef
    private List<Account> accounts = new ArrayList<>();

    private String firstName;
    private String lastName;

    @NonNull
    @Id
    private String emailAddress;

    @NonNull
    @Getter
    private String password;



    @DBRef
    private List <Role> roles = new ArrayList<>();

    public User_Entity(String firstName, String lastName, @NonNull String emailAddress, @NonNull String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public User_Entity(@NonNull String emailAddress, @NonNull String password, @NonNull List<Role> roles) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.roles = roles;
    }

    public void addAccount(Account account){
       accounts.add(account);
    }

    public  void removeAccount(Account account){
        accounts.removeIf(savedAccount -> savedAccount.equals(account));
    }

    public void addRole(Role role){
        roles.add(role);
    }

    public  void removeRole(Role role){
        roles.removeIf(savedRole -> savedRole.equals(role));
    }




}
