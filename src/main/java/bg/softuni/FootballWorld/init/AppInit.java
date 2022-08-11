package bg.softuni.FootballWorld.init;

import bg.softuni.FootballWorld.service.PlayerService;
import bg.softuni.FootballWorld.service.TeamService;
import bg.softuni.FootballWorld.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {

    private final UserService userService;
    private final TeamService teamService;
    private final PlayerService playerService;

    public AppInit(UserService userService, TeamService teamService, PlayerService playerService) {
        this.userService = userService;
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.init();
        teamService.init();
        playerService.init();
    }
}
