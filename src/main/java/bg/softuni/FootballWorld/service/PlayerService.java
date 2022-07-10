package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.PlayerCreateDTO;
import bg.softuni.FootballWorld.model.entity.PlayerEntity;
import bg.softuni.FootballWorld.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    public PlayerService(PlayerRepository playerRepository, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
    }


    public void createPlayer(PlayerCreateDTO playerCreateDTO) {

        PlayerEntity player = this.modelMapper.map(playerCreateDTO, PlayerEntity.class);

        System.out.println();
    }
}
