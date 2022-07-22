package bg.softuni.FootballWorld.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CommentCreateDTO {

    @Positive
    private Long playerId;

    @Size(min = 5)
    @NotBlank
    private String text;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
