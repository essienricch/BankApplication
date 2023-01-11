package bank.semicolon.dto.adminDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminLoginRequest {

    private String adminId;
    private String password;
}
