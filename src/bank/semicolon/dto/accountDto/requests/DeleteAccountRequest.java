package bank.semicolon.dto.accountDto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class DeleteAccountRequest {

    private String accountNumber;
}
