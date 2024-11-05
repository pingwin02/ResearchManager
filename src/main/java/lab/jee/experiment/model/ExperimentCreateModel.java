package lab.jee.experiment.model;

import lab.jee.project.model.ProjectModel;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ExperimentCreateModel {

    private UUID id;

    private String description;

    private boolean success;

    private LocalDate dateConducted;

    private ProjectModel project;

    // TODO
    private UUID researcher;

}
