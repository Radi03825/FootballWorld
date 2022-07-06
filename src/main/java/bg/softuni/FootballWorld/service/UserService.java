package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.UserRegisterDTO;
import bg.softuni.FootballWorld.model.entity.UserEntity;
import bg.softuni.FootballWorld.model.entity.UserRoleEntity;
import bg.softuni.FootballWorld.model.entity.enums.UserRoleEnum;
import bg.softuni.FootballWorld.repository.UserRepository;
import bg.softuni.FootballWorld.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegisterDTO userRegisterDTO) {

        Optional<UserEntity> byEmail = this.userRepository.findByEmail(userRegisterDTO.getEmail());

        UserEntity user = this.modelMapper.map(userRegisterDTO, UserEntity.class);

        this.userRepository.save(user);
    }

    public void init() {
        if (userRepository.count() == 0 && userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity();
            adminRole.setUserRole(UserRoleEnum.ADMIN);
            UserRoleEntity moderatorRole = new UserRoleEntity();
            moderatorRole.setUserRole(UserRoleEnum.MODERATOR);
            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setUserRole(UserRoleEnum.USER);


            adminRole = this.userRoleRepository.save(adminRole);
            moderatorRole = this.userRoleRepository.save(moderatorRole);
            userRole = this.userRoleRepository.save(userRole);

            initAdmin(List.of(adminRole, moderatorRole, userRole));
        }
    }

    private void initAdmin(List<UserRoleEntity> roles) {
        UserEntity admin = new UserEntity();
        admin.setUserRoles(roles);
        admin.setFirstName("Admin");
        admin.setLastName("Adminov");
        admin.setUsername("Admin");
        admin.setEmail("admin@admin.com");
        admin.setPassword(this.passwordEncoder.encode("123456"));

        userRepository.save(admin);
    }
}
