package bank.semicolon.dto.userDto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserSetAccountPinAccessRequest {

    private String accountNumber;
    private String pin;
    private String confirmPin;

}
