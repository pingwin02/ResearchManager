package lab.jee.experiment.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PatchExperimentRequest {

    private String description;

    private boolean success;

    private LocalDate dateConducted;
}
