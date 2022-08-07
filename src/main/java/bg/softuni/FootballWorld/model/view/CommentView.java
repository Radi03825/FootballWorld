package bg.softuni.FootballWorld.model.view;

import bg.softuni.FootballWorld.model.entity.PlayerEntity;
import bg.softuni.FootballWorld.model.entity.UserEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class CommentView {

    private Long id;

    private String message;

    private String username;

    private String created;

    public CommentView(Long id, String message, String username, String created) {
        this.id = id;
        this.message = message;
        this.username = username;
        this.created = created;
    }

    public CommentView(Long id, String message, String username) {
        this.id = id;
        this.message = message;
        this.username = username;
    }

    public CommentView(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
