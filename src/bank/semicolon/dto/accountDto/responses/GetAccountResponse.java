package bank.semicolon.dto.accountDto.responses;

import bank.semicolon.data.model.AccountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAccountResponse {

    private String accountNumber;
    private AccountType accountType;

}
