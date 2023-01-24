package bank.semicolon.dto.userDto.responses;

import bank.semicolon.data.model.Account;
import bank.semicolon.dto.accountDto.responses.GetAccountResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class FindUserResponse {

    private List<GetAccountResponse> accounts = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String message;

}
