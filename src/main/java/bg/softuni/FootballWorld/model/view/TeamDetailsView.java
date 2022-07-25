package bg.softuni.FootballWorld.model.view;

import bg.softuni.FootballWorld.model.entity.StadiumEntity;

import java.time.LocalDate;

public class TeamDetailsView {

    private String name;

    private LocalDate established;

    private String badgeImageUrl;

    private StadiumEntity stadium;

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

    public String getBadgeImageUrl() {
        return badgeImageUrl;
    }

    public void setBadgeImageUrl(String badgeImageUrl) {
        this.badgeImageUrl = badgeImageUrl;
    }

    public StadiumEntity getStadium() {
        return stadium;
    }

    public void setStadium(StadiumEntity stadium) {
        this.stadium = stadium;
    }
}
