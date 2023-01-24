package bank.semicolon.dto.accountDto.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountBalanceResponse {

    private String accountNumber;
    private BigDecimal accountBalance;
    private String account_Holder;

}
