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
public class GetExperimentResponse {

    private UUID id;

    private String description;

    private boolean success;

    private LocalDate dateConducted;

    private Project project;

    private Researcher researcher;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class Project {

        private UUID id;

        private String title;
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class Researcher {

        private UUID id;

        private String login;
    }
}
