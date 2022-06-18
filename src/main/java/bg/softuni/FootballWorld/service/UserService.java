package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.UserLoginDTO;
import bg.softuni.FootballWorld.model.dto.UserRegisterDTO;

public interface UserService {
    void register(UserRegisterDTO userRegisterDTO);

    void login(UserLoginDTO userLoginDTO);
}

