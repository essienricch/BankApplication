package bank.semicolon.data.repositories;

import bank.semicolon.data.model.User_Entity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User_Entity, String> {

    List <User_Entity> findUserByEmailAddress(String emailAddress);

    User_Entity findUser_EntitiesByEmailAddress(String emailAddress);
    boolean existsByEmailAddress(String emailAddress);

}
