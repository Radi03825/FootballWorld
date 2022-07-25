package bg.softuni.FootballWorld.model.validation;

import bg.softuni.FootballWorld.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserUsernameValidator implements ConstraintValidator<UniqueUserUsername, String> {

    private final UserRepository userRepository;

    public UniqueUserUsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.findByUsername(value).isEmpty();
    }
}
