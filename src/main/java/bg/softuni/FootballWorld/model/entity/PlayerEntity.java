package bg.softuni.FootballWorld.model.entity;

import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import bg.softuni.FootballWorld.model.entity.enums.PreferredFootEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "players")
public class PlayerEntity extends BaseEntity{

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PositionEnum position;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PreferredFootEnum preferredFoot;

    @Column(nullable = false)
    private Integer height;

    @OneToOne
    @JoinColumn(name = "skills_id", referencedColumnName = "id")
    private SkillsEntity skills;

    @OneToMany(targetEntity = CommentEntity.class, mappedBy = "player", cascade = CascadeType.ALL)
    private Set<CommentEntity> comments;

    //TODO
    private LocalDateTime created;
    private LocalDateTime modified;


    @ManyToOne
    private TeamEntity team;

    @ManyToOne
    private UserEntity manager;

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

    public PositionEnum getPosition() {
        return position;
    }

    public void setPosition(PositionEnum position) {
        this.position = position;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PreferredFootEnum getPreferredFoot() {
        return preferredFoot;
    }

    public void setPreferredFoot(PreferredFootEnum preferredFoot) {
        this.preferredFoot = preferredFoot;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public SkillsEntity getSkills() {
        return skills;
    }

    public void setSkills(SkillsEntity skills) {
        this.skills = skills;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public UserEntity getManager() {
        return manager;
    }

    public void setManager(UserEntity manager) {
        this.manager = manager;
    }
}
