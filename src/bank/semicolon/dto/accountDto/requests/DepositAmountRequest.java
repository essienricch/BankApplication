package bank.semicolon.dto.accountDto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class DepositAmountRequest {

    private BigDecimal depositAmount;
    private String accountNumber;
    private String pinCode;

}
