package bank.semicolon.dto.userDto.responses;

import lombok.Data;

@Data
public class UserSetAccountPinAccessResponse {

    private String message;
    private String accountNumber;

}
