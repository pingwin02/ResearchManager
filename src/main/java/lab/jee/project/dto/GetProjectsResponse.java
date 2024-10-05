package lab.jee.project.dto;

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
public class GetProjectsResponse {

    @Singular
    private List<Project> projects;

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
}
