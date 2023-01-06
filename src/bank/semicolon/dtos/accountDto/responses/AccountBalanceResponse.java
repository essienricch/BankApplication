package bank.semicolon.dtos.accountDto.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountBalanceResponse {

    private String accountNumber;
    private BigDecimal accountBalance;
    private String message;

}
