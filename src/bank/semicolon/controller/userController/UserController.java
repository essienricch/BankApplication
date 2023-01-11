package bank.semicolon.controller.userController;

import bank.semicolon.dto.userDto.responses.UserLoginResponse;
import bank.semicolon.dto.userDto.responses.UserSignUpResponse;
import bank.semicolon.data.model.User;
import bank.semicolon.dto.userDto.requests.UserLoginRequest;
import bank.semicolon.dto.userDto.requests.UserSignUpRequest;
import bank.semicolon.exception.userException.IllegalUserLoginArgumentException;
import bank.semicolon.exception.userException.IllegalUserReadArgument;
import bank.semicolon.exception.userException.IllegalUserSignUpArgumentException;
import bank.semicolon.services.userService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

   @Autowired
    private UserServiceImpl userService;

   @PostMapping("/user/sign-up")
    public String signUpUser(@RequestBody UserSignUpRequest userSignUpRequest) throws IllegalUserSignUpArgumentException {
       try {
           UserSignUpResponse signUpResponse = userService.userSignUp(userSignUpRequest);
           return signUpResponse.getMessage();
      }catch (Exception e){
          return e.getMessage();
      }
    }

    @GetMapping("/user/{emailAddress}")
    public User getUser(@PathVariable String emailAddress) throws IllegalUserReadArgument {

       return userService.findUserByEmail(emailAddress);
    }

    @PostMapping("/user/login")
    public String loginUser(@RequestBody UserLoginRequest userLoginRequest) throws IllegalUserLoginArgumentException, IllegalUserReadArgument {
       try {

           UserLoginResponse loginResponse = userService.userLogin(userLoginRequest);
            return loginResponse.getMessage();
       }catch (Exception e){
           return e.getMessage();
       }
    }

}
