package bank.semicolon.dto.accountDto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserSetAccountPinAccessRequest {

    private String accountNumber;
    private String pin;
    private String confirmPin;

}
