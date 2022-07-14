package bg.softuni.FootballWorld.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(name = "stadiums")
public class StadiumEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigInteger capacity;

    @Column(nullable = false)
    private LocalDate established;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getCapacity() {
        return capacity;
    }

    public void setCapacity(BigInteger capacity) {
        this.capacity = capacity;
    }

    public LocalDate getEstablished() {
        return established;
    }

    public void setEstablished(LocalDate established) {
        this.established = established;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
