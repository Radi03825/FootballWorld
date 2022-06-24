package bg.softuni.FootballWorld.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "skills")
public class SkillsEntity extends BaseEntity{

    @Column(nullable = false)
    private Integer pace;

    @Column(nullable = false)
    private Integer shooting;

    @Column(nullable = false)
    private Integer passing;

    @Column(nullable = false)
    private Integer defending;

    public Integer getPace() {
        return pace;
    }

    public void setPace(Integer pace) {
        this.pace = pace;
    }

    public Integer getShooting() {
        return shooting;
    }

    public void setShooting(Integer shooting) {
        this.shooting = shooting;
    }

    public Integer getPassing() {
        return passing;
    }

    public void setPassing(Integer passing) {
        this.passing = passing;
    }

    public Integer getDefending() {
        return defending;
    }

    public void setDefending(Integer defending) {
        this.defending = defending;
    }
}
