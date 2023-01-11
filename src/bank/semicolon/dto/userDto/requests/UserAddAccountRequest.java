package bank.semicolon.dto.userDto.requests;


import bank.semicolon.data.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserAddAccountRequest {

    private AccountType accountType;
    private String emailAddress;

}
