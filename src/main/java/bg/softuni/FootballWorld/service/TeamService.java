package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.TeamCreateDTO;
import bg.softuni.FootballWorld.model.entity.PlayerEntity;
import bg.softuni.FootballWorld.model.entity.StadiumEntity;
import bg.softuni.FootballWorld.model.entity.TeamEntity;
import bg.softuni.FootballWorld.model.entity.enums.UserRoleEnum;
import bg.softuni.FootballWorld.model.view.TeamDetailsView;
import bg.softuni.FootballWorld.model.view.TeamView;
import bg.softuni.FootballWorld.repository.*;
import bg.softuni.FootballWorld.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;
    private final StadiumRepository stadiumRepository;
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final SkillsRepository skillsRepository;

    public TeamService(TeamRepository teamRepository, ModelMapper modelMapper, StadiumRepository stadiumRepository, UserRepository userRepository, PlayerRepository playerRepository, SkillsRepository skillsRepository) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
        this.stadiumRepository = stadiumRepository;
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.skillsRepository = skillsRepository;
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
    }

    public List<TeamView> getAllTeamsOrderAlphabetical() {
        return this.teamRepository.findAllByOrderByName().stream()
                .map(this::mapTeam).collect(Collectors.toList());
    }

    public Page<TeamView> getAll(Pageable pageable) {

        return this.teamRepository.findAll(pageable)
                .map(this::mapTeam);
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

    public void deleteTeam(Long id) {
        List<PlayerEntity> allPlayersByTeamId = this.playerRepository.findAllByTeamId(id);

        this.playerRepository.deleteAll(allPlayersByTeamId);

        allPlayersByTeamId
                .forEach(p -> skillsRepository.deleteById(p.getSkills().getId()));

        this.teamRepository.deleteById(id);
    }


    public boolean isAdmin(String userName) {
        return userRepository.
                findByEmail(userName).
                filter(u -> u.getUserRoles().stream().anyMatch(r -> r.getUserRole() == UserRoleEnum.ADMIN)).
                isPresent();
    }

}

