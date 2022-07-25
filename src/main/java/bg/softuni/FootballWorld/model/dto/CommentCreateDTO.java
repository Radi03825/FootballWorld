package bg.softuni.FootballWorld.model.dto;

public class CommentCreateDTO {

    private String username;

    private Long player;

    private String message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPlayer() {
        return player;
    }

    public void setPlayer(Long playerId) {
        this.player = playerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
