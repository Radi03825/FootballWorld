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

        if (byEmail.isEmpty()) {

            UserEntity user = this.modelMapper.map(userRegisterDTO, UserEntity.class);

            user.setPassword(this.passwordEncoder.encode(userRegisterDTO.getPassword()));

            Optional<UserRoleEntity> userRole = this.userRoleRepository.findByUserRole(UserRoleEnum.USER);

            user.setUserRoles(List.of(userRole.get()));

            this.userRepository.save(user);
        }
    }

    public List<UserEntity> getAll() {
        return this.userRepository.findAll();
    }
}
