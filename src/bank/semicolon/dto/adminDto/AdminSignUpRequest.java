package bank.semicolon.dto.adminDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AdminSignUpRequest {

    private String name;
    private String password;
    private String confirmPassword;
}
