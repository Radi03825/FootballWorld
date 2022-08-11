package bg.softuni.FootballWorld.util;

import bg.softuni.FootballWorld.model.entity.*;
import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import bg.softuni.FootballWorld.model.entity.enums.PreferredFootEnum;
import bg.softuni.FootballWorld.model.entity.enums.UserRoleEnum;
import bg.softuni.FootballWorld.repository.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Component
public class TestDataUtils {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final StadiumRepository stadiumRepository;
    private final SkillsRepository skillsRepository;
    private final ImageRepository imageRepository;

    public TestDataUtils(UserRepository userRepository, UserRoleRepository userRoleRepository, PlayerRepository playerRepository, TeamRepository teamRepository, StadiumRepository stadiumRepository, SkillsRepository skillsRepository, CommentRepository commentRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.stadiumRepository = stadiumRepository;
        this.skillsRepository = skillsRepository;
        this.imageRepository = imageRepository;
    }

    public void initRoles() {
        if (userRoleRepository.count() == 0) {
            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setUserRole(UserRoleEnum.USER);

            UserRoleEntity moderatorRole = new UserRoleEntity();
            moderatorRole.setUserRole(UserRoleEnum.MODERATOR);

            UserRoleEntity adminRole = new UserRoleEntity();
            adminRole.setUserRole(UserRoleEnum.ADMIN);

            userRoleRepository.save(userRole);
            userRoleRepository.save(moderatorRole);
            userRoleRepository.save(adminRole);
        }
    }

    public UserEntity createTestAdmin(String email) {

        initRoles();

        UserEntity admin = new UserEntity();
        admin.setEmail(email);
        admin.setUsername("Admin");
        admin.setFirstName("Admin");
        admin.setLastName("Adminov");
        admin.setUserRoles(userRoleRepository.findAll());

        return userRepository.save(admin);
    }

    public UserEntity createTestModerator(String email) {

        initRoles();

        UserEntity moderator = new UserEntity();
        moderator.setEmail(email);
        moderator.setUsername("Moderator");
        moderator.setFirstName("Moderator");
        moderator.setLastName("Moderatorov");
        moderator.setUserRoles(userRoleRepository.
                findAll().stream().
                filter(r -> r.getUserRole() == UserRoleEnum.MODERATOR).
                toList());

        return userRepository.save(moderator);
    }

    public UserEntity createTestUser(String email) {

        initRoles();

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setUsername("User");
        user.setFirstName("User");
        user.setLastName("Userov");
        user.setUserRoles(userRoleRepository.
                        findAll().stream().
                        filter(r -> r.getUserRole() == UserRoleEnum.USER).
                        toList());

        return userRepository.save(user);
    }

    public PlayerEntity createTestPlayer(UserEntity user, TeamEntity team, SkillsEntity skills) {
        PlayerEntity player = new PlayerEntity();
        player.setFirstName("Player");
        player.setLastName("Playerov");
        player.setPosition(PositionEnum.FORWARD);
        player.setBirthdate(LocalDate.parse("2000-02-03"));
        player.setPrice(BigDecimal.valueOf(123.00));
        player.setDescription("Description");
        player.setPreferredFoot(PreferredFootEnum.RIGHT);
        player.setHeight(189);
        player.setManager(user);
        player.setTeam(team);
        player.setSkills(skills);
        player.setImage(createTestImage(user.getUsername()));

        return playerRepository.save(player);
    }

    public TeamEntity createTestTeam(StadiumEntity stadium) {
        TeamEntity team = new TeamEntity();

        team.setName("Team");
        team.setEstablished(LocalDate.parse("2000-02-03"));
        team.setBadgeImageUrl("https://image.com/badge");
        team.setStadium(stadium);

        return teamRepository.save(team);
    }

    public StadiumEntity createTestStadium() {
        StadiumEntity stadium = new StadiumEntity();

        stadium.setName("Stadium");
        stadium.setCapacity(BigInteger.valueOf(10000));
        stadium.setEstablished(LocalDate.parse("2000-02-03"));
        stadium.setAddress("Address");
        stadium.setImageUrl("https://image.com/stadium");

        return stadiumRepository.save(stadium);
    }

    public SkillsEntity createTestSkills(int number) {
        SkillsEntity skills = new SkillsEntity();

        skills.setPace(number);
        skills.setShooting(number);
        skills.setPassing(number);
        skills.setDefending(number);

        return skillsRepository.save(skills);
    }

    public ImageEntity createTestImage(String username) {
        ImageEntity image = new ImageEntity();

        image.setUrl(username);
        image.setPublicId(username);

        return imageRepository.save(image);
    }

    public void cleanUpDatabase() {
        playerRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
        teamRepository.deleteAll();
        stadiumRepository.deleteAll();
        skillsRepository.deleteAll();
    }

}