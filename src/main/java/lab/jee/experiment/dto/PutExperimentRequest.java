package lab.jee.experiment.dto;

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
public class PutExperimentRequest {

    private String description;

    private boolean success;

    private LocalDate dateConducted;

    private UUID researcherId;

}
