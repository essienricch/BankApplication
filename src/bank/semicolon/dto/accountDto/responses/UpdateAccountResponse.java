package bank.semicolon.dto.accountDto.responses;

import bank.semicolon.data.model.AccountType;
import lombok.Data;


@Data
public class UpdateAccountResponse {

    private String accountNumber;
    private AccountType accountType;
    private String message;

}
