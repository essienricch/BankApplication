package bank.semicolon.data.repository;

import bank.semicolon.data.model.User;
import bank.semicolon.data.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

        @Autowired
        private UserRepository userRepository;
        private User user;

        @BeforeEach
        void setUp(){
            user = new User();
        }

        @Test
        void saveUserTest(){
            User newUser = userRepository.save(user);
            assertNotNull(newUser.getEmailAddress());
            assertEquals(1L, userRepository.count());
        }

        @Test
        void findIfEmailExistsTest(){
            assertTrue(userRepository.existsById("wes234"));
        }

    }
