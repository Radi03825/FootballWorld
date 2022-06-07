package bg.softuni.FootballWorld.model.entity;

import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import com.sun.xml.bind.v2.TODO;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
public class PlayerEntity extends BaseEntity{

    private String firstName;

    private String lastName;

    private PositionEnum position;

    private LocalDate birthdate;


}
