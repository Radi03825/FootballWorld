package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.TeamCreateDTO;
import bg.softuni.FootballWorld.model.entity.StadiumEntity;
import bg.softuni.FootballWorld.model.entity.TeamEntity;
import bg.softuni.FootballWorld.repository.StadiumRepository;
import bg.softuni.FootballWorld.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;
    private final StadiumRepository stadiumRepository;

    public TeamService(TeamRepository teamRepository, ModelMapper modelMapper, StadiumRepository stadiumRepository) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
        this.stadiumRepository = stadiumRepository;
    }

    public void create(TeamCreateDTO teamCreateDTO) {

        TeamEntity team = this.modelMapper.map(teamCreateDTO, TeamEntity.class);

        Optional<StadiumEntity> byName = this.stadiumRepository.findByName(teamCreateDTO.getStadiumName());

        if (byName.isPresent()) {

            team.setStadium(byName.get());
        } else {

            StadiumEntity stadiumEntity = new StadiumEntity();
            stadiumEntity.setName(teamCreateDTO.getStadiumName());
            stadiumEntity.setEstablished(teamCreateDTO.getStadiumEstablished());
            stadiumEntity.setCapacity(teamCreateDTO.getCapacity());
            stadiumEntity.setAddress(teamCreateDTO.getAddress());
            stadiumEntity.setImageUrl(teamCreateDTO.getStadiumImageUrl());

            this.stadiumRepository.save(stadiumEntity);

            team.setStadium(stadiumEntity);
        }

        this.teamRepository.save(team);

        //TODO - check if team exist... and stadium...
        //Can choose one stadium or build new
    }

    public List<TeamEntity> getAllTeamsOrderAlphabetical() {
        return this.teamRepository.findAll();
    }
}
