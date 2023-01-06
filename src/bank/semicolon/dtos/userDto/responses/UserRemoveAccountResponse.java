package bank.semicolon.dtos.userDto.responses;


import lombok.Data;

@Data
public class UserRemoveAccountResponse {

    private int count;
    private String emailAddress;
    private String message;
}
