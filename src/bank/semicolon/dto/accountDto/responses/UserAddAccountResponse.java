package bank.semicolon.dto.accountDto.responses;

import lombok.Data;

@Data
public class UserAddAccountResponse {

    private int accountSize;
    private String accountNumber;
    private String message;
}
