package bg.softuni.FootballWorld.model.dto;

import javax.validation.constraints.NotNull;

public class GameDTO {

    @NotNull
    private Long firstPlayer;

    @NotNull
    private Long secondPlayer;

    public Long getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Long firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Long getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Long secondPlayer) {
        this.secondPlayer = secondPlayer;
    }
}
