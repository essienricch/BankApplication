package bank.semicolon.dto.accountDto.responses;

import lombok.Data;

@Data
public class ChangeAccountPinResponse {

    private String accountNumber;
    private String message;
}
