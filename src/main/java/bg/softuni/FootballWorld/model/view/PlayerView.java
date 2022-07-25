package bg.softuni.FootballWorld.model.view;

import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import bg.softuni.FootballWorld.model.entity.enums.PreferredFootEnum;

import java.math.BigDecimal;

public class PlayerView {

    private Long id;

    private String firstName;

    private String lastName;

    private PreferredFootEnum preferredFoot;

    private PositionEnum position;

    private BigDecimal price;

    private String imageUrl;

    private Integer age;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
