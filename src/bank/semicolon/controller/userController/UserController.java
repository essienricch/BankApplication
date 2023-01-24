package bank.semicolon.controller.userController;

import bank.semicolon.data.model.User_Entity;
import bank.semicolon.dto.roleDto.CreateRoleRequest;
import bank.semicolon.dto.roleDto.RoleResponseDto;
import bank.semicolon.dto.userDto.requests.UpdateUserRequest;
import bank.semicolon.dto.userDto.responses.FindUserResponse;
import bank.semicolon.dto.userDto.responses.UpdateUserResponse;
import bank.semicolon.exception.userException.RoleNotFoundException;
import bank.semicolon.exception.userException.UserNotFoundException;
import bank.semicolon.services.roleService.RoleServiceImpl;
import bank.semicolon.services.userService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {


    private UserServiceImpl userService;
    private RoleServiceImpl roleService;
    @Autowired
    public UserController(UserServiceImpl userService,
                          RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/role/create")
    public ResponseEntity <RoleResponseDto> createRole(@RequestBody CreateRoleRequest roleRequest) throws RoleNotFoundException {
           RoleResponseDto responseDto = roleService.createRole(roleRequest);
           return new ResponseEntity<>(responseDto, new HttpHeaders(), HttpStatus.CREATED);
    }


    @DeleteMapping("/role/delete/{roleName}")
    public ResponseEntity <String> deleteRole(@PathVariable String roleName) throws RoleNotFoundException {
        roleService.deleteRole(roleName);
        return new ResponseEntity<>("Role Successfully deleted ", new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/user/{emailAddress}")
    public ResponseEntity <FindUserResponse> getUser(@PathVariable String emailAddress) throws UserNotFoundException {
      return new ResponseEntity<>(userService.findUserByEmail(emailAddress),new HttpHeaders(), HttpStatus.FOUND);
    }

   @GetMapping("/user")
    public ResponseEntity <List<User_Entity>> getAllUser(){
        return new ResponseEntity<>(userService.findAllUsers(),new HttpHeaders(), HttpStatus.FOUND);
   }

   @PostMapping("/user/update")
    public ResponseEntity <UpdateUserResponse> updateUser(@RequestBody UpdateUserRequest updateUserRequest) throws UserNotFoundException {
        return new ResponseEntity<>(userService.updateUser(updateUserRequest), HttpStatus.OK);
   }



}
