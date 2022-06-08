package bg.softuni.FootballWorld.model.entity;

import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import com.sun.xml.bind.v2.TODO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    private BigDecimal price;

    @ManyToOne
    private TeamEntity team;

    @ManyToOne
    private UserEntity manager;
}
