package bank.semicolon.dto.accountDto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@AllArgsConstructor
@Data
public class AccountTransferRequest {

    private String senderAccountNumber;
    private String pinCode;
    private String receiverAccountNumber;
    private BigDecimal transferAmount;
}
