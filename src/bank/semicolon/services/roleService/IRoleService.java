package bank.semicolon.services.roleService;

import bank.semicolon.data.model.Role;
import bank.semicolon.dto.roleDto.CreateRoleRequest;
import bank.semicolon.dto.roleDto.RoleResponseDto;
import bank.semicolon.exception.userException.RoleNotFoundException;

import java.util.List;

public interface IRoleService {

    RoleResponseDto createRole(CreateRoleRequest createRoleRequest) throws RoleNotFoundException;
    void deleteRole(String roleName) throws RoleNotFoundException;
    List <Role> findAllRole();
    Role findRole(String roleName) throws RoleNotFoundException;

    boolean existByName(int roleName);

    long count();

}
