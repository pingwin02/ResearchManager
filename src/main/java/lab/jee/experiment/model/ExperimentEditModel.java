package lab.jee.experiment.model;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lab.jee.experiment.domain.DateHolder;
import lab.jee.experiment.validation.binding.ValidExperimentDate;
import lab.jee.experiment.validation.group.ExperimentModelGroup;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ValidExperimentDate(groups = ExperimentModelGroup.class)
@Named
@RequestScoped
public class ExperimentEditModel implements DateHolder, Serializable {

    @NotBlank
    private String description;

    @NotNull
    private boolean success;

    @NotNull
    private LocalDate dateConducted;

    private Long version;

}
