package lab.jee.experiment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lab.jee.experiment.validation.binding.ValidExperimentDate;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ValidExperimentDate
public class PutExperimentRequest {

    @NotBlank
    private String description;

    @NotNull
    private boolean success;

    @NotNull
    private LocalDate dateConducted;

}
