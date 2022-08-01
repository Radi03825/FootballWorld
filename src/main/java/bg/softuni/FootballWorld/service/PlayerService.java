package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.PlayerCreateDTO;
import bg.softuni.FootballWorld.model.dto.SearchPlayerDTO;
import bg.softuni.FootballWorld.model.entity.PlayerEntity;
import bg.softuni.FootballWorld.model.entity.SkillsEntity;
import bg.softuni.FootballWorld.model.entity.TeamEntity;
import bg.softuni.FootballWorld.model.entity.UserEntity;
import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import bg.softuni.FootballWorld.model.view.PlayerDetailsView;
import bg.softuni.FootballWorld.model.view.PlayerView;
import bg.softuni.FootballWorld.model.view.TeamView;
import bg.softuni.FootballWorld.repository.*;
import bg.softuni.FootballWorld.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Page<PlayerView> getAll(Pageable pageable) {
        List<PlayerView> list = this.playerRepository.findAll(pageable).stream()
                .map(p -> {
                    PlayerView map = this.modelMapper.map(p, PlayerView.class);
                    map.setAge(countYears(p.getBirthdate()));

                    return map;
                }).collect(Collectors.toList());

        return new PageImpl<>(list, pageable, this.playerRepository.findAll().size());
    }

    private Integer countYears(LocalDate birthdate) {
        LocalDate now = LocalDate.now();

        Period period = Period.between(birthdate, now);

        int years = period.getYears();

        return years;
    }

    public Page<PlayerView> getTop3ByPosition(PositionEnum position) {
        List<PlayerView> list = this.playerRepository.findByPosition(position, PageRequest.of(0, 3)).stream()
                .map(p -> {
                    PlayerView map = this.modelMapper.map(p, PlayerView.class);
                    map.setAge(countYears(p.getBirthdate()));

                    return map;
                }).collect(Collectors.toList());

        return new PageImpl<>(list);
    }

    public PlayerDetailsView getPlayerDetails(Long id) {
        Optional<PlayerEntity> player = this.playerRepository.findById(id);

        if (player.isEmpty()) {
            throw new ObjectNotFoundException("Player not found!");
        }

        PlayerDetailsView detailsView = this.modelMapper.map(player.get(), PlayerDetailsView.class);

        detailsView.setManager(player.get().getManager().getUsername());

        TeamView teamView = this.modelMapper.map(player.get().getTeam(), TeamView.class);
        teamView.setStadium(player.get().getTeam().getStadium().getName());

        detailsView.setTeam(teamView);

        return detailsView;
    }

    public List<PlayerView> getMyPlayers(UserDetails userDetails) {

        UserEntity user = this.userRepository.findByEmail(userDetails.getUsername()).get();

        return user.getPlayers().stream()
                .map(p -> {
                    PlayerView map = this.modelMapper.map(p, PlayerView.class);
                    map.setAge(countYears(p.getBirthdate()));

                    return map;
                }).collect(Collectors.toList());
    }

    public void buy(Long id, UserDetails userDetails) {

        PlayerEntity player = this.playerRepository.findById(id).get();

        UserEntity user = this.userRepository.findByEmail(userDetails.getUsername()).get();

        if (!user.getPlayers().contains(player)) {
            List<PlayerEntity> players = user.getPlayers();

            players.add(player);

            user.setPlayers(players);

            this.userRepository.save(user);
        }
    }

    public void sell(Long id, UserDetails userDetails) {

        Optional<PlayerEntity> byId = this.playerRepository.findById(id);

        if (byId.isPresent()) {
            UserEntity user = this.userRepository.findByEmail(userDetails.getUsername()).get();

            List<PlayerEntity> players = user.getPlayers();

            players.remove(byId.get());

            user.setPlayers(players);

            this.userRepository.save(user);
        }
    }

    public Page<PlayerView> searchOffer(SearchPlayerDTO searchPlayerDTO) {

        List<PlayerView> list = this.playerRepository.findAll(new PlayerSpecification(searchPlayerDTO))
                .stream()
                .map(p -> {
                    PlayerView map = this.modelMapper.map(p, PlayerView.class);
                    map.setAge(countYears(p.getBirthdate()));

                    return map;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(list);
    }
}
