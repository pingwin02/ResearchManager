package lab.jee.experiment.model;

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
public class ExperimentModel {

    private UUID id;

    private String description;

    private boolean success;

    private LocalDate dateConducted;

    private String projectTitle;

}
