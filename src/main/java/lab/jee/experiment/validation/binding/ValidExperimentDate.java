package lab.jee.experiment.validation.binding;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import lab.jee.experiment.validation.validator.ExperimentDateValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExperimentDateValidator.class)
@Documented
public @interface ValidExperimentDate {

    String message() default "Experiment date (${validatedValue.dateConducted}) must be in the past";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
