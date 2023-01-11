package bank.semicolon.services.adminService;

import bank.semicolon.data.model.Account;
import bank.semicolon.data.model.User;
import bank.semicolon.dto.adminDto.AdminLoginRequest;
import bank.semicolon.dto.adminDto.AdminSignUpRequest;

import java.util.List;

public interface IAdminService {

    void signUp(AdminSignUpRequest adminSignUpRequest);
    void login(AdminLoginRequest adminLoginRequest);
    List <User> viewAllUser();
    List <Account> viewAllAccount();

}
