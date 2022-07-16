package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.PlayerCreateDTO;
import bg.softuni.FootballWorld.model.entity.PlayerEntity;
import bg.softuni.FootballWorld.model.entity.SkillsEntity;
import bg.softuni.FootballWorld.model.entity.TeamEntity;
import bg.softuni.FootballWorld.model.entity.UserEntity;
import bg.softuni.FootballWorld.repository.PlayerRepository;
import bg.softuni.FootballWorld.repository.SkillsRepository;
import bg.softuni.FootballWorld.repository.TeamRepository;
import bg.softuni.FootballWorld.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final SkillsRepository skillsRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PlayerService(PlayerRepository playerRepository, SkillsRepository skillsRepository, TeamRepository teamRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.skillsRepository = skillsRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    public void createPlayer(PlayerCreateDTO playerCreateDTO, UserDetails userDetails) {

        PlayerEntity player = this.modelMapper.map(playerCreateDTO, PlayerEntity.class);

        TeamEntity teamEntity = teamRepository.findByName(playerCreateDTO.getTeam()).orElseThrow();
        UserEntity userEntity = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

        player.setTeam(teamEntity);
        player.setManager(userEntity);

        SkillsEntity skills = new SkillsEntity();

        skills.setPace(playerCreateDTO.getPace());
        skills.setShooting(playerCreateDTO.getShoot());
        skills.setPassing(playerCreateDTO.getPass());
        skills.setDefending(playerCreateDTO.getDefence());

        player.setSkills(skills);

        this.skillsRepository.save(skills);

        this.playerRepository.save(player);
    }

    public Page<PlayerEntity> getAll(Pageable pageable) {
        return this.playerRepository.findAll(pageable);
    }
}
