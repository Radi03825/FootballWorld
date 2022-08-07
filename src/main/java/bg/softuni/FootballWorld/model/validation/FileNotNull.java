package bg.softuni.FootballWorld.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Constraint(validatedBy = FileNotNullValidator.class)
public @interface FileNotNull {

    String message() default "File can not be null.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
