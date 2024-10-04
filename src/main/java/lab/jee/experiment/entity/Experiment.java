package lab.jee.experiment.entity;

import lab.jee.project.entity.Project;
import lab.jee.researcher.entity.Researcher;
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
public class Experiment implements Serializable {

    private UUID id;

    private String description;

    private boolean success;

    private LocalDate dateConducted;

    private Project project;

    private Researcher researcher;

}
