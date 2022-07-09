package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.TeamCreateDTO;
import bg.softuni.FootballWorld.model.entity.StadiumEntity;
import bg.softuni.FootballWorld.model.entity.TeamEntity;
import bg.softuni.FootballWorld.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    public TeamService(TeamRepository teamRepository, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    public void create(TeamCreateDTO teamCreateDTO) {

        TeamEntity team = this.modelMapper.map(teamCreateDTO, TeamEntity.class);

        StadiumEntity stadiumEntity = new StadiumEntity();
        stadiumEntity.setName(teamCreateDTO.getStadiumName());
        stadiumEntity.setEstablished(teamCreateDTO.getStadiumEstablished());
        stadiumEntity.setCapacity(teamCreateDTO.getCapacity());
        stadiumEntity.setAddress(teamCreateDTO.getAddress());
        stadiumEntity.setImageUrl(teamCreateDTO.getStadiumImageUrl());

        team.setStadium(stadiumEntity);


        //TODO
    }
}
