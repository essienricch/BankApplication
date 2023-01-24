package bank.semicolon.services.userService;

import bank.semicolon.data.model.Account;
import bank.semicolon.data.model.Role;
import bank.semicolon.data.model.User_Entity;
import bank.semicolon.data.repositories.RoleRepository;
import bank.semicolon.data.repositories.UserRepository;
import bank.semicolon.dto.accountDto.responses.GetAccountResponse;
import bank.semicolon.dto.userDto.requests.UpdateUserRequest;
import bank.semicolon.dto.userDto.responses.UpdateUserResponse;
import bank.semicolon.dto.userDto.requests.*;
import bank.semicolon.dto.userDto.responses.*;
import bank.semicolon.exception.userException.RoleNotFoundException;
import bank.semicolon.exception.userException.UserNotFoundException;
import bank.semicolon.exception.userException.UserServiceException;
import bank.semicolon.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements IUserService{


    private RoleRepository roleRepository;
    private SecurityConfig config;
    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           SecurityConfig config) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.config = config;
    }



    @Override
    public UserSignUpResponse registerUser(UserSignUpRequest userSignUpRequest)throws UserServiceException {
        UserSignUpResponse userSignUpResponse = new UserSignUpResponse();
        User_Entity userEntity = new User_Entity();
        if (passwordMatch(userSignUpRequest)){
            if (!userRepository.existsById(userSignUpRequest.getEmailAddress())){
                Role role = roleRepository.findByRoleName("USER");
                return register(userSignUpRequest, userSignUpResponse, userEntity, role);
            }else throw new UserServiceException(" E-mail already Exist");
        }else {
            throw new UserServiceException("Password does not match...");
        }
    }

    public UserSignUpResponse registerAdmin(UserSignUpRequest userSignUpRequest) throws  UserServiceException, RoleNotFoundException {
        UserSignUpResponse userSignUpResponse = new UserSignUpResponse();
        User_Entity userEntity = new User_Entity();
        if (passwordMatch(userSignUpRequest)){
            if (!userRepository.existsById(userSignUpRequest.getEmailAddress())){
                Role role = roleRepository.findByRoleName("ADMIN");
                return register(userSignUpRequest, userSignUpResponse, userEntity, role);

            }else throw new UserServiceException(" E-mail already Exist");
        }else {
            throw new UserServiceException("Password does not match...");
        }
    }

    private UserSignUpResponse register(UserSignUpRequest userSignUpRequest,
                                        UserSignUpResponse userSignUpResponse,
                                        User_Entity userEntity, Role role) {
        userEntity.addRole(role);
        userEntity.setFirstName(userSignUpRequest.getFirstName());
        userEntity.setLastName(userSignUpRequest.getLastName());
        userEntity.setEmailAddress(userSignUpRequest.getEmailAddress());
        userEntity.setPassword(config.passwordEncoder().encode(userSignUpRequest.getPassword()));
        User_Entity user = userRepository.save(userEntity);

        userSignUpResponse.setEmailAddress(user.getEmailAddress());
        userSignUpResponse.setMessage("Congratulations, your sign-up was successful");
        return userSignUpResponse;
    }

    private boolean passwordMatch(UserSignUpRequest userSignUpRequest){
        return userSignUpRequest.getPassword().equals(userSignUpRequest.getConfirmPassword());
    }

    @Override
    public UserLoginResponse userLogin(UserLoginRequest userLoginRequest) throws UserServiceException, UserNotFoundException {
        UserLoginResponse loginResponse = new UserLoginResponse();

        User_Entity savedUserEntity = findOneUser(userLoginRequest.getEmailAddress());
        if (savedUserEntity != null){
            if (validatePassword(userLoginRequest.getPassword(), savedUserEntity)){
               loginResponse.setEmailAddress(savedUserEntity.getEmailAddress());
               loginResponse.setMessage("Login Successful");
               return loginResponse;
            }else throw new UserServiceException("Password Incorrect");
        }
        throw new UserServiceException("User_Entity E-mail does not exists");
    }

    private boolean validatePassword(String password, User_Entity userEntity){
        return config.passwordEncoder().matches(password,userEntity.getPassword());
    }


    @Override
    public UserDeleteResponse deleteUser(UserDeleteRequest deleteRequest) throws UserNotFoundException {
        User_Entity userEntity = findOneUser(deleteRequest.getEmailAddress());
        UserDeleteResponse deleteResponse = new UserDeleteResponse();
        if (userEntity != null){
            userRepository.delete(userEntity);
            deleteResponse.setMessage("Delete successful");
        }else throw new UserNotFoundException("User_Entity not found");
        return null;
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) throws UserNotFoundException {
       UpdateUserResponse updateUserResponse = new UpdateUserResponse();
        User_Entity savedUser = findOneUser(updateUserRequest.getEmailAddress());
        if (savedUser != null){
            if (validatePassword(updateUserRequest.getOldPassword(), savedUser)){
                savedUser.setFirstName(updateUserRequest.getFirstName());
                savedUser.setLastName(updateUserRequest.getLastName());
                savedUser.setPassword(config.passwordEncoder().encode(updateUserRequest.getNewPassword()));
                savedUser.setEmailAddress(updateUserRequest.getEmailAddress());

                User_Entity updatedUser =  userRepository.save(savedUser);
                updateUserResponse.setFirstName(updatedUser.getFirstName());
                updateUserResponse.setLastName(updatedUser.getLastName());
                updateUserResponse.setEmailAddress(updatedUser.getEmailAddress());
               updateUserResponse.setAccountList(mapToAccountDto(updatedUser.getAccounts()));
                updateUserResponse.setMessage("Update Successful");
                return updateUserResponse;
            }else throw new UserServiceException("Incorrect Password");
        }else throw new UserNotFoundException("User not found");
    }

    private List <Account> mapToAccountDto(List <Account> accounts){
        return accounts.stream().map(this::mapAcct).collect(Collectors.toList());
    }

    private Account mapAcct(Account account){
        Account acctDto = new Account();
        acctDto.setAccountNumber(account.getAccountNumber());
        acctDto.setAccountType(account.getAccountType());
        acctDto.setBalance(account.getBalance());
        return acctDto;
    }


    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public FindUserResponse findUserByEmail(String emailAddress) throws UserNotFoundException {
        FindUserResponse userResponse = new FindUserResponse();
        if (userRepository.existsById(emailAddress)){
            for (User_Entity userEntity : userRepository.findUserByEmailAddress(emailAddress)){
                if (userEntity.getEmailAddress().equals(emailAddress)){
                    userResponse.setAccounts(
                            userEntity.getAccounts().
                                    stream().
                                    map(acct ->
                                            GetAccountResponse.builder()
                                                    .accountNumber(acct.getAccountNumber())
                                                    .accountType(acct.getAccountType())
                                                    .build())
                                    .collect(Collectors.toList())
                    );
                    userResponse.setEmailAddress(userEntity.getEmailAddress());
                    userResponse.setFirstName(userEntity.getFirstName());
                    userResponse.setLastName(userEntity.getLastName());
                    userResponse.setMessage("You got served...");
                    return userResponse;
                }
            }
        }else throw new UserNotFoundException("User_Entity not found");
        return null;
    }

    public User_Entity findOneUser(String emailAddress) throws UserNotFoundException {
        return userRepository.findUser_EntitiesByEmailAddress(emailAddress);
    }

    @Override
    public List<User_Entity> findAllUsers() {
        List <User_Entity> userEntities = userRepository.findAll();
        return userEntities.stream().map(this::mapAcct).collect(Collectors.toList());
    }

    private User_Entity mapAcct(User_Entity user){
        User_Entity user_entity = new User_Entity();
        user_entity.setFirstName(user.getFirstName());
        user_entity.setLastName(user.getLastName());
        user_entity.setEmailAddress(user.getEmailAddress());
        user_entity.setAccounts(mapToAccountDto(user.getAccounts()));
        return user_entity;
    }
}
