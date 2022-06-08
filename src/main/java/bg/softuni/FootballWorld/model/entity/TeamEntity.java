package bg.softuni.FootballWorld.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "teams")
public class TeamEntity extends BaseEntity{

    private String name;

    private String townName;

    private String countryName;

    private Integer stadiumCapacity;


}
