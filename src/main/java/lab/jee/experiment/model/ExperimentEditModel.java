package lab.jee.experiment.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ExperimentEditModel {

    private String description;

    private boolean success;

    private LocalDate dateConducted;

}
