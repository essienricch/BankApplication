package bank.semicolon.dto.userDto.responses;

import lombok.Data;

@Data
public class UserAddAccountResponse {

    private int accountSize;
    private String accountNumber;
    private String message;
}
