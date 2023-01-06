package bank.semicolon.dtos.userDto.requests;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Data
public class UserSignUpRequest {

    private String firstName;
    private String lastName;
    @NonNull
    private String emailAddress;
    @NonNull
    private String password;
    @NonNull
    private String confirmPassword;


}
