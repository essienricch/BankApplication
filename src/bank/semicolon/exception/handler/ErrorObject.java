package bank.semicolon.exception.handler;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;


@Builder
public class ErrorObject {

    private  HttpStatus httpStatus;
    private  String message;
    private  String details;
    private  Date dateTime;


}
