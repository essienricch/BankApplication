package bank.semicolon.data.repositories;

import bank.semicolon.data.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends MongoRepository <Role, Integer> {
    Role findByRoleName(String roleName);

    boolean findByRoleId(int roleId);
}
