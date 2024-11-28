package lab.jee.experiment.model;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lab.jee.experiment.domain.DateHolder;
import lab.jee.experiment.validation.binding.ValidExperimentDate;
import lab.jee.experiment.validation.group.ExperimentModelGroup;
import lab.jee.project.model.ProjectModel;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ValidExperimentDate(groups = ExperimentModelGroup.class)
@Named
@ConversationScoped
public class ExperimentCreateModel implements DateHolder, Serializable {

    private UUID id;

    @NotBlank
    private String description;

    @NotNull
    private boolean success;

    @NotNull
    private LocalDate dateConducted;

    @NotNull
    private ProjectModel project;

}
