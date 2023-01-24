package bank.semicolon.dto.accountDto.responses;


import lombok.Data;

@Data
public class UserRemoveAccountResponse {

    private int count;
    private String emailAddress;
    private String message;
}
