package bank.semicolon.exception.handler;

import bank.semicolon.exception.userException.RoleNotFoundException;
import bank.semicolon.exception.accountException.AccountServiceException;
import bank.semicolon.exception.accountException.IllegalAccountReadArgument;
import bank.semicolon.exception.userException.UserNotFoundException;
import bank.semicolon.exception.userException.UserServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(
            value = {RoleNotFoundException.class,
            UserServiceException.class,
            UsernameNotFoundException.class})

    public ResponseEntity<ErrorObject> userCustomHandler (Exception exceptionResponse){
        ErrorObject errorObject = new ErrorObject(
                HttpStatus.BAD_REQUEST,
                exceptionResponse.getLocalizedMessage(),
                "shit happens",
//                request.getDescription(true),
                new Date()

        );
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AccountServiceException.class, IllegalAccountReadArgument.class})
    public ResponseEntity<ErrorObject> accountCustomHandler (Exception exceptionResponse, WebRequest request){
        ErrorObject errorObject = new ErrorObject(
                HttpStatus.BAD_REQUEST,
                exceptionResponse.getLocalizedMessage(),
                request.getDescription(true),
                new Date()

        );
        return new ResponseEntity<>(errorObject,new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleAllException (Exception exceptionResponse){
        ErrorObject errorObject = new ErrorObject(
                HttpStatus.BAD_REQUEST,
                exceptionResponse.getMessage(),
                " ",
                new Date()

        );
        return new ResponseEntity<>(errorObject,HttpStatus.BAD_REQUEST);
    }

}
