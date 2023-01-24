package bank.semicolon.services.roleService;

import bank.semicolon.dto.roleDto.CreateRoleRequest;
import bank.semicolon.dto.roleDto.RoleResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleServiceImplTest {

    private RoleServiceImpl roleService;

    @Autowired
    public RoleServiceImplTest(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @Test
    void createRoleTest(){
        CreateRoleRequest createRoleRequest = new CreateRoleRequest("ADMIN");
        try {
            RoleResponseDto responseDto = roleService.createRole(createRoleRequest);
            assertNotNull(responseDto);
            System.out.println(responseDto.getMessage());
            assertEquals(createRoleRequest.getRole() + " Successfully Created", responseDto.getMessage());
        }catch (Exception e){
            //System.out.println(e.getMessage());
        }
        assertEquals(2L, roleService.count());



    }
}