package bank.semicolon.services.adminService;

import bank.semicolon.data.model.Account;
import bank.semicolon.data.model.User;
import bank.semicolon.dto.adminDto.AdminLoginRequest;
import bank.semicolon.dto.adminDto.AdminSignUpRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService  implements IAdminService{


    @Override
    public void signUp(AdminSignUpRequest adminSignUpRequest) {

    }

    @Override
    public void login(AdminLoginRequest adminLoginRequest) {

    }

    @Override
    public List<User> viewAllUser() {
        return null;
    }

    @Override
    public List<Account> viewAllAccount() {
        return null;
    }
}
