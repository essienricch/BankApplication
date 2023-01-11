package bank.semicolon.dto.accountDto.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositAmountResponse {

    private String message;
    private BigDecimal amount;
}
