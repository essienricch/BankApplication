package bank.semicolon.dto.accountDto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class AccountBalanceRequest {

    private String accountNumber;
    private String pinCode;
//    private String emailAddress;

}
