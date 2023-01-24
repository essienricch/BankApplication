package bank.semicolon.services.userService;

import bank.semicolon.data.model.User_Entity;
import bank.semicolon.dto.userDto.requests.UpdateUserRequest;
import bank.semicolon.dto.userDto.responses.UpdateUserResponse;
import bank.semicolon.dto.userDto.requests.*;
import bank.semicolon.dto.userDto.responses.*;

import bank.semicolon.exception.userException.RoleNotFoundException;
import bank.semicolon.exception.userException.UserNotFoundException;
import bank.semicolon.exception.userException.UserServiceException;

import java.util.List;

public interface IUserService {

    UserSignUpResponse registerUser(UserSignUpRequest userSignUpRequest) throws UserServiceException, RoleNotFoundException;
    UserLoginResponse userLogin(UserLoginRequest userLoginRequest)
            throws UserServiceException, UserNotFoundException;



    UserDeleteResponse deleteUser(UserDeleteRequest deleteRequest) throws UserNotFoundException;

    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) throws UserNotFoundException;

    long count();

    User_Entity findOneUser(String emailAddress) throws UserNotFoundException;

    FindUserResponse findUserByEmail(String emailAddress) throws UserNotFoundException;
    List <User_Entity> findAllUsers();
}
