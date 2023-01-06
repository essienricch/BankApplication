package bank.semicolon.services.userService;

import bank.semicolon.data.model.Account;
import bank.semicolon.data.model.User;
import bank.semicolon.dtos.userDto.requests.*;
import bank.semicolon.dtos.userDto.responses.*;
import bank.semicolon.exception.accountException.IllegalAccountReadArgument;
import bank.semicolon.exception.userException.IllegalUserLoginArgumentException;
import bank.semicolon.exception.userException.IllegalUserReadArgument;
import bank.semicolon.exception.userException.IllegalUserSignUpArgumentException;

import java.util.List;

public interface IUserService {

    UserSignUpResponse userSignUp(UserSignUpRequest userSignUpRequest) throws IllegalUserSignUpArgumentException;
    UserLoginResponse userLogin(UserLoginRequest userLoginRequest) throws IllegalUserLoginArgumentException, IllegalUserReadArgument;
    UserAddAccountResponse userCreateAccount(UserAddAccountRequest userAddAccountRequest) throws IllegalUserReadArgument, IllegalAccountReadArgument;

    UserRemoveAccountResponse removeAccount(UserRemoveAccountRequest userRemoveAccount) throws IllegalUserReadArgument, IllegalAccountReadArgument;

    UserDeleteResponse deleteUser(UserDeleteRequest deleteRequest) throws IllegalUserReadArgument;

    int listOfAccount(String emailAddress) throws IllegalUserReadArgument;
    long count();

    User findOneUser(String emailAddress);

    User findUserByEmail(String emailAddress) throws IllegalUserReadArgument;
    List <User> findAllUsers();
}
