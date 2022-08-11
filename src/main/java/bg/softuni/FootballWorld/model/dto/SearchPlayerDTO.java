package bg.softuni.FootballWorld.model.dto;

public class SearchPlayerDTO {

    private String firstName;

    private String lastName;

    private Integer minPrice;

    private Integer maxPrice;

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

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public boolean isEmpty() {
        return (firstName == null || firstName.isEmpty()) &&
                (lastName == null || lastName.isEmpty()) &&
                minPrice == null &&
                maxPrice == null;
    }
}
