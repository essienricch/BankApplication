package bank.semicolon.dtos.accountDto.requests;

import bank.semicolon.data.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class CreateAccountRequest {

    private AccountType accountType;
    private String emailAddress;
}
