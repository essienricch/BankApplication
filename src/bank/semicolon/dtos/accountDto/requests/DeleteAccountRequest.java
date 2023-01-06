package bank.semicolon.dtos.accountDto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class DeleteAccountRequest {

    private String accountNumber;
}
