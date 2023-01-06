package bank.semicolon.controller.user;

import bank.semicolon.data.model.User;
import bank.semicolon.dtos.userDto.requests.UserLoginRequest;
import bank.semicolon.dtos.userDto.requests.UserSignUpRequest;
import bank.semicolon.dtos.userDto.responses.UserLoginResponse;
import bank.semicolon.dtos.userDto.responses.UserSignUpResponse;
import bank.semicolon.exception.userException.IllegalUserLoginArgumentException;
import bank.semicolon.exception.userException.IllegalUserReadArgument;
import bank.semicolon.exception.userException.IllegalUserSignUpArgumentException;
import bank.semicolon.services.userService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {

   @Autowired
    private UserServiceImpl userService;

   @RequestMapping(value = "/user",method = RequestMethod.POST)
    public String signUpUser(@RequestBody UserSignUpRequest userSignUpRequest) throws IllegalUserSignUpArgumentException {
      UserSignUpResponse signUpResponse = new UserSignUpResponse();
       try {
         signUpResponse = userService.userSignUp(userSignUpRequest);
      }catch (Exception e){
          System.out.println(e.getMessage());
      }
       return signUpResponse.getMessage();
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List <User> getAllUser(){
       return userService.findAllUsers();
    }

    @RequestMapping(value = "/user/{userLoginRequest}",method = RequestMethod.GET)
    public String loginUser(@PathVariable UserLoginRequest userLoginRequest) throws IllegalUserLoginArgumentException, IllegalUserReadArgument {
      UserLoginResponse loginResponse = new UserLoginResponse();
       try {

           loginResponse = userService.userLogin(userLoginRequest);

       }catch (Exception e){
          System.out.println(e.getMessage());
       }

       return loginResponse.getMessage();
    }
}
