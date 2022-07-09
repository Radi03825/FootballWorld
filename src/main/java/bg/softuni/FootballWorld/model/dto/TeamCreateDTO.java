package bg.softuni.FootballWorld.model.dto;

import java.math.BigInteger;
import java.time.LocalDate;

public class TeamCreateDTO {

    private String name;

    private LocalDate established;

    private String badgeImageUrl;

    private String stadiumName;

    private LocalDate stadiumEstablished;

    private BigInteger capacity;

    private String address;

    private String stadiumImageUrl;

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

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public LocalDate getStadiumEstablished() {
        return stadiumEstablished;
    }

    public void setStadiumEstablished(LocalDate stadiumEstablished) {
        this.stadiumEstablished = stadiumEstablished;
    }

    public BigInteger getCapacity() {
        return capacity;
    }

    public void setCapacity(BigInteger capacity) {
        this.capacity = capacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStadiumImageUrl() {
        return stadiumImageUrl;
    }

    public void setStadiumImageUrl(String stadiumImageUrl) {
        this.stadiumImageUrl = stadiumImageUrl;
    }
}
