package bank.semicolon.dto.userDto.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class UserRemoveAccountRequest {

    private String emailAddress;
    private String accountNumber;
}
