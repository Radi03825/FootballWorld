package bg.softuni.FootballWorld.model.dto;

import bg.softuni.FootballWorld.model.validation.FieldMatch;
import bg.softuni.FootballWorld.model.validation.UniqueUserEmail;
import bg.softuni.FootballWorld.model.validation.UniqueUserUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "confirmPassword")
public class UserRegisterDTO {

    @NotBlank
    @Size(min = 5, max = 25)
    private String firstName;

    @NotBlank
    @Size(min = 5, max = 25)
    private String lastName;

    @NotBlank(message = "Username is required.")
    @Size(min = 5, max = 25, message = "Username should be between 5 and 25 symbols.")
    @UniqueUserUsername(message = "User username should be unique.")
    private String username;

    @NotBlank(message = "Email should be provided.")
    @Email(message = "Email should be valid.")
    @UniqueUserEmail(message = "Email should be unique.")
    private String email;

    @NotBlank
    @Size(min = 5)
    private String password;

    @NotBlank
    @Size(min = 5)
    private String confirmPassword;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
