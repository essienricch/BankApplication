package bank.semicolon.services.roleService;


import bank.semicolon.data.model.Role;

import bank.semicolon.data.repositories.RoleRepository;
import bank.semicolon.dto.roleDto.CreateRoleRequest;
import bank.semicolon.dto.roleDto.RoleResponseDto;
import bank.semicolon.exception.userException.RoleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;


@Service
public class RoleServiceImpl implements IRoleService {


    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public RoleResponseDto createRole(CreateRoleRequest createRoleRequest) throws RoleNotFoundException {
        Role savedRle = findRole(createRoleRequest.getRole());
        if (savedRle == null){
            Role role = new Role(createRoleRequest.getRole().toUpperCase(Locale.ROOT));
            Role savedRole =  roleRepository.save(role);
             RoleResponseDto responseDto = new RoleResponseDto();
            responseDto.setRoleId(savedRole.getRoleId());
            responseDto.setMessage(savedRole.getRoleName() + " Successfully Created");
            return responseDto;
        }else throw new RoleNotFoundException("Role already Exists");

    }

    @Override
    public void deleteRole(String roleName) throws RoleNotFoundException {
        Role savedRole = findRole(roleName);
        if (savedRole != null){
            roleRepository.delete(savedRole);
        }else throw new RoleNotFoundException("Role does not exist");

    }

    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRole(String roleName) throws RoleNotFoundException {
        Role savedRole =  roleRepository.findByRoleName(roleName);
        if (savedRole != null){
            return savedRole;
        }else throw new RoleNotFoundException("Role not found");
    }

    @Override
    public boolean existByName(int roleId) {
        return roleRepository.existsById(roleId);
    }

    @Override
    public long count() {
        return roleRepository.count();
    }
}
