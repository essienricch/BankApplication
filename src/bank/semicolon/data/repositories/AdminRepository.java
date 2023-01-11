package bank.semicolon.data.repositories;

import bank.semicolon.data.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends MongoRepository <Admin, String> {


}
