package bank.semicolon.services.userService;

import bank.semicolon.data.model.AccountType;
import bank.semicolon.data.model.User;
import bank.semicolon.dto.userDto.requests.*;
import bank.semicolon.dto.userDto.responses.*;
import bank.semicolon.exception.accountException.IllegalAccountReadArgument;
import bank.semicolon.exception.userException.IllegalUserLoginArgumentException;
import bank.semicolon.exception.userException.IllegalUserReadArgument;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void signUpUserTest()  {
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest("wisdom2021","2023","2023");
        UserSignUpResponse userSignUpResponse = new UserSignUpResponse();

        try {
            userSignUpResponse = userService.userSignUp(userSignUpRequest);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        System.out.println(userSignUpResponse.getMessage());
        assertNotNull(userSignUpResponse.getEmailAddress());
        assertEquals(userSignUpResponse.getEmailAddress(),"wisdom2021");
    }

    @Test
    void findSignUpUserTest() throws IllegalUserReadArgument {
        try {
            User user = userService.findUserByEmail("babyKendrick@2022");
            assertEquals("babyKendrick@2022", user.getEmailAddress());
            System.out.println(user.getFirstName());
            assertNull(user.getFirstName());
        }catch (IllegalUserReadArgument e){
            System.out.println(e.getMessage());
        }
    }


    @Test
    void findOneUserTest(){
        User saved = userService.findOneUser("dan122");
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
    void userAddAccount_countIsOneTest() throws IllegalUserReadArgument, IllegalAccountReadArgument {
        UserAddAccountRequest addAccountRequest = new UserAddAccountRequest(AccountType.SAVINGS,"wisdom2021");
        UserAddAccountResponse userAddAccountResponse = new UserAddAccountResponse();
        try {
            userAddAccountResponse = userService.userCreateAccount(addAccountRequest);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(userAddAccountResponse.getAccountNumber());
        assertNotNull(userAddAccountResponse.getAccountNumber());
        assertEquals(1, userAddAccountResponse.getAccountSize());
    }

    @Test
    void setAccountPinTest(){
        UserSetAccountPinAccessResponse setAccountPinAccessResponse = new UserSetAccountPinAccessResponse();
        UserSetAccountPinAccessRequest setAccountPinAccessRequest = new UserSetAccountPinAccessRequest("5876764360","0020","0020");
        try {
            setAccountPinAccessResponse = userService.setAccountPin(setAccountPinAccessRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertNotNull(setAccountPinAccessResponse.getAccountNumber());
    }

    @Test
    void removeAccount_countIsZeroTest() throws IllegalUserReadArgument, IllegalAccountReadArgument {
        UserRemoveAccountRequest removeAccountRequest = new UserRemoveAccountRequest("dan122","2354461404");
        try {
            UserRemoveAccountResponse response = userService.removeAccount(removeAccountRequest);
            assertEquals(3, response.getCount());
            assertEquals(response.getEmailAddress(),"dan122");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
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