package bank.semicolon.services.userService;

import bank.semicolon.data.model.Account;
import bank.semicolon.data.model.User;
import bank.semicolon.data.repositories.AccountRepository;
import bank.semicolon.data.repositories.UserRepository;
import bank.semicolon.dto.userDto.requests.*;
import bank.semicolon.dto.userDto.responses.*;
import bank.semicolon.exception.accountException.IllegalAccountReadArgument;
import bank.semicolon.exception.accountException.IllegalChangeOfPinArgument;
import bank.semicolon.exception.userException.IllegalUserLoginArgumentException;
import bank.semicolon.exception.userException.IllegalUserReadArgument;
import bank.semicolon.exception.userException.IllegalUserSignUpArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements IUserService{


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserSignUpResponse userSignUp(UserSignUpRequest userSignUpRequest) throws IllegalUserSignUpArgumentException {
        UserSignUpResponse userSignUpResponse = new UserSignUpResponse();
        User user = new User();
        if (passwordMatch(userSignUpRequest)){
            if (!userRepository.existsById(userSignUpRequest.getEmailAddress())){
                user.setFirstName(userSignUpRequest.getFirstName());
                user.setLastName(userSignUpRequest.getLastName());
                user.setEmailAddress(userSignUpRequest.getEmailAddress());
                user.setPassword(userSignUpRequest.getPassword());

                userRepository.save(user);

                userSignUpResponse.setEmailAddress(user.getEmailAddress());
                userSignUpResponse.setMessage("Congratulations, your sign-up was successful");
                return userSignUpResponse;

            }else throw new IllegalUserSignUpArgumentException("User E-mail Exist already");
        }else {
            throw new IllegalUserSignUpArgumentException("Password does not match...");
        }
    }

    private boolean passwordMatch(UserSignUpRequest userSignUpRequest){
        return userSignUpRequest.getPassword().equals(userSignUpRequest.getConfirmPassword());
    }

    @Override
    public UserLoginResponse userLogin(UserLoginRequest userLoginRequest) throws IllegalUserLoginArgumentException, IllegalUserReadArgument {
        UserLoginResponse loginResponse = new UserLoginResponse();

        User savedUser = findUserByEmail(userLoginRequest.getEmailAddress());
        if (savedUser != null){
            if (validatePassword(userLoginRequest.getPassword(), savedUser)){
               loginResponse.setEmailAddress(savedUser.getEmailAddress());
               loginResponse.setMessage("Login Successful");
               return loginResponse;
            }else throw new IllegalUserLoginArgumentException("Password Incorrect");
        }
        throw new IllegalUserLoginArgumentException("User E-mail does not exists");
    }

    private boolean validatePassword(String password, User user){
        return password.equals(user.getPassword());
    }

    @Override
    public UserAddAccountResponse userCreateAccount(UserAddAccountRequest userAddAccountRequest) throws IllegalUserReadArgument, IllegalAccountReadArgument {
        User savedUser = findUserByEmail(userAddAccountRequest.getEmailAddress());
        UserAddAccountResponse accountResponse = new UserAddAccountResponse();
        if (savedUser != null){
            Account newAccount = new Account(userAddAccountRequest.getAccountType(), userAddAccountRequest.getEmailAddress());
                    newAccount.generateAccountNumber();
                    accountRepository.save(newAccount);
                    savedUser.addAccount(newAccount);
                    accountResponse.setAccountSize(listOfAccount(savedUser.getEmailAddress()));
                    accountResponse.setAccountNumber(newAccount.getAccountNumber());
                    accountResponse.setMessage("Account Created");
                    return accountResponse;
        }else throw new IllegalAccountReadArgument("User mot found");
    }

    @Override
    public UserSetAccountPinAccessResponse setAccountPin(UserSetAccountPinAccessRequest setAccountPinAccessRequest) throws IllegalAccountReadArgument, IllegalChangeOfPinArgument {
        UserSetAccountPinAccessResponse setAccountPinAccessResponse = new UserSetAccountPinAccessResponse();
        if (pinMatch(setAccountPinAccessRequest.getPin(), setAccountPinAccessRequest.getConfirmPin())){
          for (Account savedAccount : accountRepository.findAccountByAccountNumber(setAccountPinAccessRequest.getAccountNumber())){
               if (savedAccount.getAccountNumber().equals(setAccountPinAccessRequest.getAccountNumber())){
                   savedAccount.setAccountPin(setAccountPinAccessRequest.getPin());
                   setAccountPinAccessResponse.setAccountNumber(savedAccount.getAccountNumber());
                   setAccountPinAccessResponse.setMessage("Pin set successful");
                   return setAccountPinAccessResponse;
               }else throw new IllegalAccountReadArgument("Account does not exist");
          }
        }else throw new IllegalChangeOfPinArgument("Pin does not match");
        return null;
    }

    private boolean pinMatch(String pin, String confirmPin){
        return pin.equals(confirmPin);
    }

    @Override
    public UserRemoveAccountResponse removeAccount(UserRemoveAccountRequest userRemoveAccount) throws IllegalUserReadArgument, IllegalAccountReadArgument {
        UserRemoveAccountResponse accountResponse = new UserRemoveAccountResponse();
        User savedUser = findUserByEmail(userRemoveAccount.getEmailAddress());
        if (savedUser != null){
            for(Account savedAccount : accountRepository.findAccountByAccountNumber(userRemoveAccount.getAccountNumber())){
                if (savedAccount.getAccountNumber().equals(userRemoveAccount.getAccountNumber()) ){
                    accountRepository.delete(savedAccount);
                    savedUser.removeAccount(savedAccount);
                    accountResponse.setCount(listOfAccount(savedUser.getEmailAddress()));
                    accountResponse.setEmailAddress(savedUser.getEmailAddress());
                    accountResponse.setMessage("Account removed");
                    return accountResponse;
                }else throw new IllegalAccountReadArgument("Account does not exist");
            }
        }else throw new IllegalUserReadArgument("User does not exist");
        return null;
    }

    @Override
    public UserDeleteResponse deleteUser(UserDeleteRequest deleteRequest) throws IllegalUserReadArgument {
        User user = findOneUser(deleteRequest.getEmailAddress());
        UserDeleteResponse deleteResponse = new UserDeleteResponse();
        if (user != null){
            userRepository.delete(user);
            deleteResponse.setMessage("Delete successful");
        }else throw new IllegalUserReadArgument("User not found");
        return null;
    }

    @Override
    public int listOfAccount(String emailAddress) {
        int count = 0;
       for (Account account: accountRepository.findAll()){
           if (account.getEmailAddress().equals(emailAddress)){
               count++;
           }
       }return count;
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public User findUserByEmail(String emailAddress) throws IllegalUserReadArgument {
        if (userRepository.existsById(emailAddress)){
            for (User user: userRepository.findUserByEmailAddress(emailAddress)){
                if (user.getEmailAddress().equals(emailAddress)){
                    return user;
                }
            }
        }else throw new IllegalUserReadArgument("User not found");
        return null;
    }

    public User findOneUser(String emailAddress){
        for (User user : findAllUsers()){
            if (user.getEmailAddress().equals(emailAddress)){
                return user;
            }
        }return null;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
