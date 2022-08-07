package bg.softuni.FootballWorld.model.dto;

import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import bg.softuni.FootballWorld.model.entity.enums.PreferredFootEnum;
import bg.softuni.FootballWorld.model.validation.FileNotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PlayerCreateDTO {

    @Size(min = 3, max = 25)
    @NotBlank
    private String firstName;

    @Size(min = 5, max = 25)
    @NotBlank
    private String lastName;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate birthdate;

    @NotBlank
    private String team;

    @Max(210)
    @Min(150)
    @NotNull
    private Integer height;

    @NotNull
    private PreferredFootEnum preferredFoot;

    @NotNull
    private PositionEnum position;

    @Positive
    @NotNull
    private BigDecimal price;

    @FileNotNull
    private MultipartFile image;

    @Size(min = 5)
    @NotBlank
    private String description;

    @Max(100)
    @Min(0)
    @NotNull
    private Integer pace;

    @Max(100)
    @Min(0)
    @NotNull
    private Integer shoot;

    @Max(100)
    @Min(0)
    @NotNull
    private Integer pass;

    @Max(100)
    @Min(0)
    @NotNull
    private Integer defence;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public PreferredFootEnum getPreferredFoot() {
        return preferredFoot;
    }

    public void setPreferredFoot(PreferredFootEnum preferredFoot) {
        this.preferredFoot = preferredFoot;
    }

    public PositionEnum getPosition() {
        return position;
    }

    public void setPosition(PositionEnum position) {
        this.position = position;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPace() {
        return pace;
    }

    public void setPace(Integer pace) {
        this.pace = pace;
    }

    public Integer getShoot() {
        return shoot;
    }

    public void setShoot(Integer shoot) {
        this.shoot = shoot;
    }

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }

    public Integer getDefence() {
        return defence;
    }

    public void setDefence(Integer defence) {
        this.defence = defence;
    }
}
