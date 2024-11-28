package lab.jee.experiment.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ExperimentsModel {

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

        private Long version;

        private LocalDateTime creationDateTime;

        private LocalDateTime modificationDateTime;

    }
}
