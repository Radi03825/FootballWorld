package bg.softuni.FootballWorld.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "teams")
public class TeamEntity extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate established;

    @ManyToOne(optional = false)
    private StadiumEntity stadium;

    @Column(nullable = false)
    private String badgeImageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEstablished() {
        return established;
    }

    public void setEstablished(LocalDate established) {
        this.established = established;
    }

    public StadiumEntity getStadium() {
        return stadium;
    }

    public void setStadium(StadiumEntity stadium) {
        this.stadium = stadium;
    }

    public String getBadgeImageUrl() {
        return badgeImageUrl;
    }

    public void setBadgeImageUrl(String badgeImageUrl) {
        this.badgeImageUrl = badgeImageUrl;
    }
}
