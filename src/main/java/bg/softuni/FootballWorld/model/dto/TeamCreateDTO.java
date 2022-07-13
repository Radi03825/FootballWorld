package bg.softuni.FootballWorld.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigInteger;
import java.time.LocalDate;

public class TeamCreateDTO {

    @Size(min = 3, max = 25)
    @NotBlank
    private String name;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate established;

    @NotBlank
    private String badgeImageUrl;

    @Size(min = 3, max = 30)
    @NotBlank
    private String stadiumName;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate stadiumEstablished;

    @Positive
    @NotNull
    private BigInteger capacity;

    @Size(min = 5, max = 30)
    @NotBlank
    private String address;

    @NotBlank
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
