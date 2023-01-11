package bank.semicolon.dto.accountDto.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountTransferResponse {

    private String message;
    private BigDecimal transferAmount;

}
