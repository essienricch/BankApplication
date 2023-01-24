package bank.semicolon.security;

import bank.semicolon.data.model.Role;
import bank.semicolon.data.model.User_Entity;
import bank.semicolon.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCustomServiceSec implements UserDetailsService {


    private UserRepository userRepository;



    @Autowired
    public UserCustomServiceSec(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User_Entity userEntity;
        userEntity = userRepository.findUser_EntitiesByEmailAddress(emailAddress) ;
        return new User(userEntity.getEmailAddress(), userEntity.getPassword(), mapRolesToAuthority(userEntity.getRoles()));
    }

    private Collection <GrantedAuthority> mapRolesToAuthority(List <Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }
}
