package bank.semicolon.dtos.userDto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class UserLoginRequest {

    private String emailAddress;
    private String password;
}
