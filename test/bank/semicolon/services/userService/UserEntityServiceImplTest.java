package bank.semicolon.services.userService;

import bank.semicolon.data.model.User_Entity;
import bank.semicolon.dto.userDto.requests.*;
import bank.semicolon.dto.userDto.responses.*;
import bank.semicolon.exception.userException.UserNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserEntityServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void signUpUserTest()  {
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest("wisdom2021","2023","2023");
        UserSignUpResponse userSignUpResponse = new UserSignUpResponse();

        try {
            userSignUpResponse = userService.registerUser(userSignUpRequest);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        System.out.println(userSignUpResponse.getMessage());
        assertNotNull(userSignUpResponse.getEmailAddress());
        assertEquals(userSignUpResponse.getEmailAddress(),"wisdom2021");
    }

    @Test
    void findSignUpUserTest() throws UserNotFoundException {
        try {
            User_Entity userEntity = userService.findOneUser("babyKendrick@2022");
            assertEquals("babyKendrick@2022", userEntity.getEmailAddress());
            System.out.println(userEntity.getFirstName());
            assertNull(userEntity.getFirstName());
        }catch (UserNotFoundException e){
            System.out.println(e.getMessage());
        }
    }


    @Test
    void findOneUserTest() throws UserNotFoundException {
        User_Entity saved = userService.findOneUser("dan122");
        assertNull(saved);
       // assertNotEquals("ess123",saved.getEmailAddress());
    }

    @Test
    void loginUserTest() throws IllegalUserLoginArgumentException {
        UserLoginRequest loginRequest = new UserLoginRequest("ess123","3456");
        UserLoginResponse loginResponse = new UserLoginResponse();

        try {
            loginResponse = userService.userLogin(loginRequest);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }

        assertEquals("ess123",loginResponse.getEmailAddress());
        assertEquals("Login Successful",loginResponse.getMessage());
    }







    @Test
    void deleteUserTest(){
        UserDeleteRequest deleteRequest = new UserDeleteRequest("dan122");
        try{
            UserDeleteResponse deleteResponse = userService.deleteUser(deleteRequest);
            assertNotNull(deleteResponse.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



}