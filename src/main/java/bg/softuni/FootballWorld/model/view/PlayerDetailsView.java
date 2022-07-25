package bg.softuni.FootballWorld.model.view;

import bg.softuni.FootballWorld.model.entity.SkillsEntity;
import bg.softuni.FootballWorld.model.entity.TeamEntity;
import bg.softuni.FootballWorld.model.entity.UserEntity;
import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import bg.softuni.FootballWorld.model.entity.enums.PreferredFootEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PlayerDetailsView {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthdate;

    private TeamView team;

    private Integer height;

    private PreferredFootEnum preferredFoot;

    private PositionEnum position;

    private BigDecimal price;

    private String imageUrl;

    private String description;

    private String manager;

    private SkillsEntity skills;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public TeamView getTeam() {
        return team;
    }

    public void setTeam(TeamView team) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public SkillsEntity getSkills() {
        return skills;
    }

    public void setSkills(SkillsEntity skills) {
        this.skills = skills;
    }
}
