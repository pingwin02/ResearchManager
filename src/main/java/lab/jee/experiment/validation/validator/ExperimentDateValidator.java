package lab.jee.experiment.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lab.jee.experiment.domain.DateHolder;
import lab.jee.experiment.validation.binding.ValidExperimentDate;

import java.time.LocalDate;

public class ExperimentDateValidator implements ConstraintValidator<ValidExperimentDate, DateHolder> {

    @Override
    public boolean isValid(DateHolder value, ConstraintValidatorContext context) {
        return value.getDateConducted().isBefore(LocalDate.now())
                || value.getDateConducted().isEqual(LocalDate.now());
    }
}
