package bank.semicolon.dtos.accountDto.responses;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawAmountResponse {

    private String message;
    private BigDecimal amount;
    private String accountNumber;
}
