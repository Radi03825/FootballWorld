package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.entity.UserEntity;
import bg.softuni.FootballWorld.model.entity.UserRoleEntity;
import bg.softuni.FootballWorld.model.entity.enums.UserRoleEnum;
import bg.softuni.FootballWorld.model.user.FootballWorldUserDetails;
import bg.softuni.FootballWorld.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FootballWorldUserDetailsServiceTest {

    @Mock
    private UserRepository mockUserRepo;

    private FootballWorldUserDetailsService toTest;

    @BeforeEach
    void setUp() {
        toTest = new FootballWorldUserDetailsService(
                mockUserRepo
        );
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        // arrange
        UserEntity testUserEntity = new UserEntity();

        testUserEntity.setEmail("test@test.com");
        testUserEntity.setPassword("password");
        testUserEntity.setFirstName("Test1");
        testUserEntity.setLastName("Testov");
        //testUserEntity.setActive(true);

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUserRole(UserRoleEnum.USER);
        
        testUserEntity.setUserRoles(List.of(userRole));

        when(mockUserRepo.findByEmail(testUserEntity.getEmail())).
                thenReturn(Optional.of(testUserEntity));

        // act
        FootballWorldUserDetails userDetails = (FootballWorldUserDetails)
                toTest.loadUserByUsername(testUserEntity.getEmail());

        // assert
        Assertions.assertEquals(testUserEntity.getEmail(),
                userDetails.getUsername());
        Assertions.assertEquals(testUserEntity.getFirstName(), userDetails.getFirstName());
        Assertions.assertEquals(testUserEntity.getLastName(), userDetails.getLastName());
        Assertions.assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(testUserEntity.getFirstName() + " " + testUserEntity.getLastName(),
                userDetails.getFullName());

    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername("non-existant@email.com")
        );
    }
}
