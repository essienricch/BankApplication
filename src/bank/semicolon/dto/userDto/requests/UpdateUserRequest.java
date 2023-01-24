package bank.semicolon.dto.userDto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserRequest {

    private String firstName;
    private String lastName;
    private String oldPassword;
    private String newPassword;
    private String emailAddress;

}
