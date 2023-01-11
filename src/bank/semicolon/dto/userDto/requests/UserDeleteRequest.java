package bank.semicolon.dto.userDto.requests;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDeleteRequest {

    private String emailAddress;

}
