package bg.softuni.FootballWorld.model.entity;

import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import bg.softuni.FootballWorld.model.entity.enums.PreferredFootEnum;
import com.sun.xml.bind.v2.TODO;

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

    private String description;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PreferredFootEnum preferredFoot;

    @Column(nullable = false)
    private Integer height;

    @OneToMany(targetEntity = CommentEntity.class, mappedBy = "player", cascade = CascadeType.ALL)
    private Set<CommentEntity> comments;

    //TODO
    private LocalDateTime created;
    private LocalDateTime modified;


    @ManyToOne
    private TeamEntity team;

    @ManyToOne
    private UserEntity manager;






}
