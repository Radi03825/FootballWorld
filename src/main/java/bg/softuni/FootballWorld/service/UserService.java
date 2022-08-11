package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.UserRegisterDTO;
import bg.softuni.FootballWorld.model.entity.UserEntity;
import bg.softuni.FootballWorld.model.entity.UserRoleEntity;
import bg.softuni.FootballWorld.model.entity.enums.UserRoleEnum;
import bg.softuni.FootballWorld.model.view.UserView;
import bg.softuni.FootballWorld.repository.UserRepository;
import bg.softuni.FootballWorld.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if (byEmail.isEmpty()) {

            UserEntity user = this.modelMapper.map(userRegisterDTO, UserEntity.class);

            user.setPassword(this.passwordEncoder.encode(userRegisterDTO.getPassword()));

            Optional<UserRoleEntity> userRole = this.userRoleRepository.findByUserRole(UserRoleEnum.USER);

            user.setUserRoles(List.of(userRole.get()));

            this.userRepository.save(user);
        }
    }

    public List<UserView> getAll() {
        return this.userRepository.findAll().stream().map(u -> {
            UserView map = this.modelMapper.map(u, UserView.class);
            List<UserRoleEntity> userRoles = u.getUserRoles();
            map.setRole(String.valueOf(userRoles.get(u.getUserRoles().size() - 1).getUserRole()));
            return map;
        }).collect(Collectors.toList());
    }

    public void upgrade(Long id) {
        Optional<UserEntity> userById = this.userRepository.findById(id);

        if (userById.isPresent()) {
            Optional<UserRoleEntity> userRole = this.userRoleRepository.findByUserRole(UserRoleEnum.MODERATOR);

            userById.get().getUserRoles().add(userRole.get());

            this.userRepository.save(userById.get());
        }
    }



    public void init() {
        if (userRepository.count() == 0 && userRoleRepository.count() == 0) {

            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setUserRole(UserRoleEnum.USER);
            UserRoleEntity moderatorRole = new UserRoleEntity();
            moderatorRole.setUserRole(UserRoleEnum.MODERATOR);
            UserRoleEntity adminRole = new UserRoleEntity();
            adminRole.setUserRole(UserRoleEnum.ADMIN);

            userRole = this.userRoleRepository.save(userRole);
            moderatorRole = this.userRoleRepository.save(moderatorRole);
            adminRole = this.userRoleRepository.save(adminRole);

            initAdmin(List.of(userRole, moderatorRole, adminRole));
            initModerator(List.of(userRole, moderatorRole));
            initUser(List.of(userRole));
        }
    }

    private void initAdmin(List<UserRoleEntity> roles) {
        UserEntity admin = new UserEntity();
        admin.setUserRoles(roles);
        admin.setFirstName("Admin");
        admin.setLastName("Adminov");
        admin.setUsername("Admin");
        admin.setEmail("admin@email.com");
        admin.setPassword(this.passwordEncoder.encode("123456"));

        userRepository.save(admin);
    }

    private void initModerator(List<UserRoleEntity> roles) {
        UserEntity moderator = new UserEntity();
        moderator.setUserRoles(roles);
        moderator.setFirstName("Moderator");
        moderator.setLastName("Moderatorov");
        moderator.setUsername("Moderator");
        moderator.setEmail("moderator@email.com");
        moderator.setPassword(this.passwordEncoder.encode("123456"));

        userRepository.save(moderator);
    }

    private void initUser(List<UserRoleEntity> roles) {
        UserEntity user = new UserEntity();
        user.setUserRoles(roles);
        user.setFirstName("User");
        user.setLastName("Userov");
        user.setUsername("User");
        user.setEmail("user@email.com");
        user.setPassword(this.passwordEncoder.encode("123456"));

        userRepository.save(user);
    }
}
