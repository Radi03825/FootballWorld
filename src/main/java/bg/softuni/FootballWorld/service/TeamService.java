package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.TeamCreateDTO;
import bg.softuni.FootballWorld.model.entity.StadiumEntity;
import bg.softuni.FootballWorld.model.entity.TeamEntity;
import bg.softuni.FootballWorld.model.view.TeamDetailsView;
import bg.softuni.FootballWorld.model.view.TeamView;
import bg.softuni.FootballWorld.repository.StadiumRepository;
import bg.softuni.FootballWorld.repository.TeamRepository;
import bg.softuni.FootballWorld.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<TeamView> getAllTeamsOrderAlphabetical() {
        return this.teamRepository.findAllByOrderByName().stream()
                .map(this::mapTeam).collect(Collectors.toList());
    }

    public Page<TeamView> getAll(Pageable pageable) {

        List<TeamView> all = this.teamRepository.findAll(pageable).stream()
                .map(this::mapTeam).collect(Collectors.toList());

        return new PageImpl<>(all);
    }

    private TeamView mapTeam(TeamEntity t) {
        TeamView teamView = this.modelMapper.map(t, TeamView.class);
        teamView.setStadium(t.getStadium().getName());
        return teamView;
    }

    public TeamDetailsView getTeamDetails(Long id) {
        Optional<TeamEntity> team = this.teamRepository.findById(id);

        if (team.isEmpty()) {
            throw new ObjectNotFoundException("Team not found!");
        }

        return this.modelMapper.map(team.get(), TeamDetailsView.class);
    }
}
