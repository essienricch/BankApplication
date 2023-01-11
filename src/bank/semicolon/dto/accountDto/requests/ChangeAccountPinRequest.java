package bank.semicolon.dto.accountDto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeAccountPinRequest {

    private String oldPin;
    private String newPin;
    private String confirmNewPin;
    private String accountNumber;
}
