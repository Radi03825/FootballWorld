package bg.softuni.FootballWorld.service.impl;

import bg.softuni.FootballWorld.model.dto.UserLoginDTO;
import bg.softuni.FootballWorld.model.dto.UserRegisterDTO;
import bg.softuni.FootballWorld.model.entity.UserEntity;
import bg.softuni.FootballWorld.repository.UserRepository;
import bg.softuni.FootballWorld.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bg.softuni.FootballWorld.user.CurrentUser;


import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private CurrentUser currentUser;
    private ModelMapper modelMapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {

        Optional<UserEntity> byEmail = this.userRepository.findByEmail(userRegisterDTO.getEmail());

        UserEntity user = this.modelMapper.map(userRegisterDTO, UserEntity.class);

        this.userRepository.save(user);
    }

    @Override
    public void login(UserLoginDTO userLoginDTO) {

        Optional<UserEntity> user = userRepository.findByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword());

        if (user.isPresent()) {
            this.currentUser.login(user.get());
        }

    }
}
