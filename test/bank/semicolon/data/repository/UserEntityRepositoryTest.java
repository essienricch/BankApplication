package bank.semicolon.data.repository;

import bank.semicolon.data.model.User_Entity;
import bank.semicolon.data.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserEntityRepositoryTest {

        @Autowired
        private UserRepository userRepository;
        private User_Entity userEntity;

        @BeforeEach
        void setUp(){
            userEntity = new User_Entity();
        }

        @Test
        void saveUserTest(){
            User_Entity newUserEntity = userRepository.save(userEntity);
            assertNotNull(newUserEntity.getEmailAddress());
            assertEquals(1L, userRepository.count());
        }

        @Test
        void findIfEmailExistsTest(){
            assertTrue(userRepository.existsById("wes234"));
        }

    }
