package lab.jee.experiment.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class GetExperimentsResponse {

    @Singular
    private List<Experiment> experiments;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class Experiment {

        private UUID id;

        private String description;

    }
}
