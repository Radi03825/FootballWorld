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
import org.springframework.data.domain.PageImpl;
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



    public void init() {
        if (stadiumRepository.count() == 0 && teamRepository.count() == 0) {
            StadiumEntity arenaSozopol = initStadium("ul. 'Republikanska', 8130 Tsentar, Sozopol", 3500, "2012-06-14", "https://www.krupal.bg/storage/app/media/Projects/Sozopol%20Stadium/sozopol-stadium-slide2.jpg", "Arena Sozopol");
            StadiumEntity stadionHristoBotev = initStadium("ul. 'Mitko Palauzov', 5300 Gabrovo, 8130 Tsentar, Sozopol", 5500, "1951-03-17", "http://photos.wikimapia.org/p/00/05/20/65/98_big.jpg", "Stadion Hristo Botev");
            StadiumEntity stadionBistritsa = initStadium("Bistritsa, Sofia, Bulgaria, 8130 Tsentar, Sozopol", 2500, "2017-06-04", "https://www.europlan-online.de/files/d4c24d28bc680378c1f794cbba0cc41d.jpeg", "Stadion Bistritsa");
            StadiumEntity rakovskiStadium = initStadium("ul. 'Balsha' 18, 1408 Ivan Vazov, Sofia", 2500, "1950-06-04", "https://levskisofia.info/files/stadiums/288.jpg", "Rakovski Stadium");
            StadiumEntity georgiBenkovski = initStadium("ul. 'Akademik Stefan Mladenov' 20, 3705 Vidin", 15000, "1920-03-04", "https://secure.cache.images.core.optasports.com/soccer/venues/300x225/9877.jpg", "Georgi Benkovski");
            StadiumEntity stadionTsarSamuil = initStadium("ul. 'Bulgaria' 77, 2850 Tsar Samuil, Petrich", 9500, "1920-03-04", "https://matchez.today/cdn-cgi/image/w=720,h=405,fit=cover,f=auto/images/venues/12285.png", "Stadion Tsar Samuil");
            StadiumEntity stadionBonchuk = initStadium("ul. 'Samoranska' 9, 2602 Gorna Mahala, Dupnica", 16000, "1952-01-07", "https://pirinsport.com/sites/default/files/%D0%B1%D0%BE%D0%BD%D1%87%D1%83%D0%BA%20%D0%B4%D1%83%D0%BF%D0%BD%D0%B8%D1%86%D0%B0.jpg", "Stadion Bonchuk");
            StadiumEntity stadionIskar = initStadium("2000 Tsentar, Samokov", 7000, "1972-03-08", "https://levskisofia.info/files/stadiums/188.jpg", "Stadion Iskar");

            initTeam("https://upload.wikimedia.org/wikipedia/en/thumb/3/3a/FCSozopol_logo.png/225px-FCSozopol_logo.png", "2008-06-03", "FC Sozopol", arenaSozopol);
            initTeam("https://upload.wikimedia.org/wikipedia/en/3/33/FC_Yantra_Gabrovo.png", "1919-02-05", "FC Yantra Gabrovo", stadionHristoBotev);
            initTeam("https://upload.wikimedia.org/wikipedia/en/thumb/2/23/FC_Vitosha_Bistritsa_emblem.png/180px-FC_Vitosha_Bistritsa_emblem.png", "1958-05-15", "FC Vitosha Bistritsa", stadionBistritsa);
            initTeam("https://levskirakovski.bg/wp-content/themes/rakovski/images/levski-rakovski-logo.svg", "1913-07-15", "Levski Rakovski", rakovskiStadium);
            initTeam("https://upload.wikimedia.org/wikipedia/en/2/2b/Bdin_vidin_logo.png", "1923-04-15", "Bdin Vidin", georgiBenkovski);
            initTeam("https://upload.wikimedia.org/wikipedia/en/f/f9/Belasitsa_Petrich.png", "1923-02-10", "OFC Belasitsa Petrich", stadionTsarSamuil);
            initTeam("https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Marekold3.png/180px-Marekold3.png", "1947-05-11", "FC Marek Dupnitsa", stadionBonchuk);
            initTeam("https://upload.wikimedia.org/wikipedia/en/6/6a/Rilski-sportist_small.png", "1947-10-10", "FC Rilski Sportist Samokov", stadionIskar);
        }
    }

    private StadiumEntity initStadium(String address, int capacity, String established, String imageUrl, String name) {
        StadiumEntity stadium = new StadiumEntity();
        stadium.setAddress(address);
        stadium.setCapacity(BigInteger.valueOf(capacity));
        stadium.setEstablished(LocalDate.parse(established));
        stadium.setImageUrl(imageUrl);
        stadium.setName(name);

        return stadiumRepository.save(stadium);
    }

    private TeamEntity initTeam(String badgeImageUrl, String established, String name, StadiumEntity stadium) {
        TeamEntity team = new TeamEntity();
        team.setBadgeImageUrl(badgeImageUrl);
        team.setEstablished(LocalDate.parse(established));
        team.setName(name);
        team.setStadium(stadium);

        return teamRepository.save(team);
    }
}

