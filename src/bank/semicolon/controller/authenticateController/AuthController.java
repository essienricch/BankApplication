package bank.semicolon.controller.authenticateController;

import bank.semicolon.dto.userDto.requests.UserSignUpRequest;
import bank.semicolon.dto.userDto.responses.UserSignUpResponse;
import bank.semicolon.exception.userException.RoleNotFoundException;
import bank.semicolon.exception.userException.UserServiceException;
import bank.semicolon.services.userService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserServiceImpl userService;

    @Autowired
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register_user")
    public ResponseEntity<UserSignUpResponse> registerUser(@RequestBody UserSignUpRequest signUpRequest)
   throws UserServiceException {
        return new ResponseEntity<>(userService.registerUser(signUpRequest), HttpStatus.OK);
    }

    @PostMapping("/register_admin")
    public ResponseEntity<UserSignUpResponse> registerAdmin(@RequestBody UserSignUpRequest signUpRequest)
            throws UserServiceException, RoleNotFoundException {
        return new ResponseEntity<>(userService.registerAdmin(signUpRequest), HttpStatus.OK);
    }


}
