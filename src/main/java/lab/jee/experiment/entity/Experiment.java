package lab.jee.experiment.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "experiments")
public class Experiment implements Serializable {

    @Id
    private UUID id;

    private String description;

    private boolean success;

    private LocalDate dateConducted;

    @ManyToOne
    @JoinColumn(name = "project")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "researcher")
    private Researcher researcher;

}
