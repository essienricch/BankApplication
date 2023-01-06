package bank.semicolon.dtos.accountDto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@AllArgsConstructor
@Data
public class AccountTransferRequest {

    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal transferAmount;
}
