package lab.jee.project.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ProjectModel {

    private UUID id;

    private String title;

    private double budget;

    private String priority;

}