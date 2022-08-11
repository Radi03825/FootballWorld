package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.dto.PlayerCreateDTO;
import bg.softuni.FootballWorld.model.dto.SearchPlayerDTO;
import bg.softuni.FootballWorld.model.entity.*;
import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import bg.softuni.FootballWorld.model.entity.enums.PreferredFootEnum;
import bg.softuni.FootballWorld.model.entity.enums.UserRoleEnum;
import bg.softuni.FootballWorld.model.view.PlayerDetailsView;
import bg.softuni.FootballWorld.model.view.PlayerView;
import bg.softuni.FootballWorld.model.view.TeamView;
import bg.softuni.FootballWorld.repository.*;
import bg.softuni.FootballWorld.service.cloudinary.CloudinaryImage;
import bg.softuni.FootballWorld.service.cloudinary.CloudinaryService;
import bg.softuni.FootballWorld.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
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
    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public PlayerService(PlayerRepository playerRepository, SkillsRepository skillsRepository, TeamRepository teamRepository, UserRepository userRepository, ImageRepository imageRepository, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.skillsRepository = skillsRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }


    public void createPlayer(PlayerCreateDTO playerCreateDTO, UserDetails userDetails) throws IOException {

        PlayerEntity player = this.modelMapper.map(playerCreateDTO, PlayerEntity.class);
        CloudinaryImage uploaded = cloudinaryService.upload(playerCreateDTO.getImage());

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setUrl(uploaded.getUrl());
        imageEntity.setPublicId(uploaded.getPublicId());

        this.imageRepository.save(imageEntity);

        player.setImage(imageEntity);

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
        return this.playerRepository.findAll(pageable)
                .map(p -> {
                    PlayerView map = this.modelMapper.map(p, PlayerView.class);
                    map.setAge(countYears(p.getBirthdate()));

                    return map;
                });
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

        detailsView.setManager(player.get().getManager().getEmail());

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

    public List<PlayerView> searchOffer(SearchPlayerDTO searchPlayerDTO) {

        return this.playerRepository.findAll(new PlayerSpecification(searchPlayerDTO))
                .stream()
                .map(p -> {
                    PlayerView map = this.modelMapper.map(p, PlayerView.class);
                    map.setAge(countYears(p.getBirthdate()));

                    return map;
                })
                .collect(Collectors.toList());
    }

    public void deletePlayer(Long id) throws IOException {
        PlayerEntity player = this.playerRepository.findById(id).get();

        ImageEntity image = player.getImage();

        cloudinaryService.delete(image.getPublicId());

        this.playerRepository.delete(player);

        imageRepository.delete(image);
    }


    public boolean isOwner(String userName, Long playerId) {

        boolean isOwner = playerRepository.
                findById(playerId).
                filter(p -> p.getManager().getEmail().equals(userName)).
                isPresent();

        if (isOwner) {
            return true;
        }

        return userRepository.
                findByEmail(userName).
                filter(this::isAdmin).
                isPresent();
    }

    private boolean isAdmin(UserEntity user) {
        return user.getUserRoles().
                stream().
                anyMatch(r -> r.getUserRole() == UserRoleEnum.ADMIN);
    }



    public void init() {
        if (playerRepository.count() == 0 && skillsRepository.count() == 0 && imageRepository.count() == 0) {
            ImageEntity first = initImage("https://res.cloudinary.com/dgntwkoji/image/upload/v1659962665/etaasvpnphvtjya0ndpc.webp", "etaasvpnphvtjya0ndpc");
            ImageEntity second = initImage("https://res.cloudinary.com/dgntwkoji/image/upload/v1659963210/ob4rfh0iwsqooqpdepb5.webp", "ob4rfh0iwsqooqpdepb5");
            ImageEntity third = initImage("https://res.cloudinary.com/dgntwkoji/image/upload/v1660028529/phjpdqyipr4i7pqmrk62.webp", "phjpdqyipr4i7pqmrk62");
            ImageEntity fourth = initImage("https://res.cloudinary.com/dgntwkoji/image/upload/v1660027258/uszbmvoniiaxsl3nku4e.webp", "uszbmvoniiaxsl3nku4e");
            ImageEntity fifth = initImage("https://res.cloudinary.com/dgntwkoji/image/upload/v1660027361/p9ixx384oxhnyof8thq1.webp", "p9ixx384oxhnyof8thq1");
            ImageEntity sixth = initImage("https://res.cloudinary.com/dgntwkoji/image/upload/v1660027287/ezerjwforvcmik4wpmee.webp", "ezerjwforvcmik4wpmee");
            ImageEntity seventh = initImage("https://res.cloudinary.com/dgntwkoji/image/upload/v1660027339/o9h2yunq00iuf05ozfi3.webp", "o9h2yunq00iuf05ozfi3");
            ImageEntity eighth = initImage("https://res.cloudinary.com/dgntwkoji/image/upload/v1660027314/hwkay8xe8dq5bob2uczz.webp", "hwkay8xe8dq5bob2uczz");
            ImageEntity ninth = initImage("https://res.cloudinary.com/dgntwkoji/image/upload/v1659963395/kpqmbw6ftgzavf1afcaf.webp", "kpqmbw6ftgzavf1afcaf");
            ImageEntity tenth = initImage("https://res.cloudinary.com/dgntwkoji/image/upload/v1660028614/tcxasivi18ntbeq2ynuf.webp", "tcxasivi18ntbeq2ynuf");
            ImageEntity eleventh = initImage("https://res.cloudinary.com/dgntwkoji/image/upload/v1660028636/n5o253um1clyggrfg0x8.webp", "n5o253um1clyggrfg0x8");
            ImageEntity twelfth = initImage("https://res.cloudinary.com/dgntwkoji/image/upload/v1660028558/yleva3aurpxna9b0vnbv.webp", "yleva3aurpxna9b0vnbv");

            UserEntity admin = userRepository.findById(1L).get();
            UserEntity moderator = userRepository.findById(2L).get();

            initPlayer("2002-06-30", "Really good young midfielder.", "Stefan", 186, first, "Petrov", "MIDFIELDER", "RIGHT", 1500.00, admin, initSkills(70, 73, 87, 71), 1L);
            initPlayer("2001-04-10", "Fast young forward.", "Teodor", 174, second, "Georgiev", "FORWARD", "RIGHT", 3000.00, admin, initSkills(55, 90, 80, 77), 3L);
            initPlayer("2004-07-27", "The best young goalkeeper.", "Ilian", 193, third, "Manolov", "GOALKEEPER", "RIGHT", 5000.00, admin, initSkills(91, 53, 84, 31), 4L);
            initPlayer("2002-07-04", "Good young defender.", "Kalin", 191, fourth, "Stefanov", "DEFENDER", "RIGHT", 2500.00, moderator, initSkills(70, 63, 88, 50), 3L);
            initPlayer("2003-02-24", "Really good young defender.", "Martin", 185, fifth, "Angelov", "DEFENDER", "RIGHT", 2000.00, moderator, initSkills(76, 73, 84, 61), 5L);
            initPlayer("2001-04-14", "Really good young box to box midfielder.", "Konstantin", 182, sixth, "Vladigerov", "MIDFIELDER", "LEFT", 1000.00, admin, initSkills(70, 75, 87, 55), 2L);
            initPlayer("1999-10-27", "Fast winger.", "Darin", 177, seventh, "Petrov", "FORWARD", "RIGHT", 2500.00, moderator, initSkills(92, 73, 94, 67), 1L);
            initPlayer("2000-01-20", "Really good defensive midfielder.", "Kaloqn", 193, eighth, "Davidov", "MIDFIELDER", "RIGHT", 5000.00, admin, initSkills(89, 77, 84, 36), 4L);
            initPlayer("2000-01-01", "Really good defender.", "Steven", 196, ninth, "Ezze", "DEFENDER", "LEFT", 10000.00, admin, initSkills(91, 73, 77, 64), 5L);
            initPlayer("2002-09-10", "Young goalkeeper with good future.", "Ivan", 193, tenth, "Pavlov", "GOALKEEPER", "RIGHT", 2000.00, moderator, initSkills(62, 46, 70, 23), 7L);
            initPlayer("2001-04-28", "Really good young striker.", "David", 178, eleventh, "Stefanov", "FORWARD", "RIGHT", 7000.00, admin, initSkills(45, 90, 78, 80), 4L);
            initPlayer("2003-11-20", "Good young goalkeeper.", "Antoan", 197, twelfth, "Paskalev", "GOALKEEPER", "RIGHT", 5000.00, moderator, initSkills(86, 45, 70, 27), 8L);
        }
    }

    private ImageEntity initImage(String url, String publicId) {
        ImageEntity image = new ImageEntity();
        image.setUrl(url);
        image.setPublicId(publicId);

        return imageRepository.save(image);
    }

    private SkillsEntity initSkills(Integer defence, Integer pace, Integer pass, Integer shoot) {
        SkillsEntity skills = new SkillsEntity();
        skills.setDefending(defence);
        skills.setPace(pace);
        skills.setPassing(pass);
        skills.setShooting(shoot);

        return skillsRepository.save(skills);
    }

    private PlayerEntity initPlayer(String birthdate, String description, String firstName, Integer height, ImageEntity image, String lastName, String position, String preferredFoot, Double price, UserEntity manager, SkillsEntity skills, Long team) {
        PlayerEntity player = new PlayerEntity();
        player.setBirthdate(LocalDate.parse(birthdate));
        player.setDescription(description);
        player.setFirstName(firstName);
        player.setHeight(height);
        player.setImage(image);
        player.setLastName(lastName);
        player.setPosition(PositionEnum.valueOf(position));
        player.setPreferredFoot(PreferredFootEnum.valueOf(preferredFoot));
        player.setManager(manager);
        player.setSkills(skills);
        player.setTeam(teamRepository.findById(team).get());

        return playerRepository.save(player);
    }
}
