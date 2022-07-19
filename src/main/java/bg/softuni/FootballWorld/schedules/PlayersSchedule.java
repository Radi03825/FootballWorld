package bg.softuni.FootballWorld.schedules;

import bg.softuni.FootballWorld.model.entity.PlayerEntity;
import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import bg.softuni.FootballWorld.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class PlayersSchedule {

    private Logger LOGGER = LoggerFactory.getLogger(PlayersSchedule.class);

    private List<PlayerEntity> top3players;
    private PositionEnum position;

    private final PlayerService playerService;

    public PlayersSchedule(PlayerService playerService) {
        this.playerService = playerService;
        this.top3players = this.playerService.getTop3ByPosition(PositionEnum.GOALKEEPER);
        this.position = PositionEnum.GOALKEEPER;
    }

    @Scheduled(cron = "20 * * * * *")
    public void setTop3players() {
        PositionEnum position = randomPosition();
        setPosition(position);

        this.top3players = this.playerService.getTop3ByPosition(position);

        LOGGER.info("Shuffling players...");
    }

    private PositionEnum randomPosition() {
        int pick = new Random().nextInt(PositionEnum.values().length);
        return PositionEnum.values()[pick];
    }

    public List<PlayerEntity> getTop3players() {
        return top3players;
    }

    public PositionEnum getPosition() {
        return position;
    }

    public void setPosition(PositionEnum position) {
        this.position = position;
    }
}
