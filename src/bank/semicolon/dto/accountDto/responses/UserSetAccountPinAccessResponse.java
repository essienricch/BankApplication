package bank.semicolon.dto.accountDto.responses;

import lombok.Data;

@Data
public class UserSetAccountPinAccessResponse {

    private String message;
    private String accountNumber;

}
