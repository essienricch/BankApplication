package bank.semicolon.data.repositories;

import bank.semicolon.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List <User> findUserByEmailAddress(String emailAddress);

}
