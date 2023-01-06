package bank.semicolon.dtos.accountDto.responses;

import lombok.Data;

@Data
public class CreateAccountResponse {

    private String emailAddress;
    private String accountNumber;
    private String message;

}
