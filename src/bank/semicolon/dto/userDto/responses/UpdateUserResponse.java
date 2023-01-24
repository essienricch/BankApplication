package bank.semicolon.dto.userDto.responses;

import bank.semicolon.data.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateUserResponse {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private List <Account> accountList = new ArrayList<>();
    private String message;
}
