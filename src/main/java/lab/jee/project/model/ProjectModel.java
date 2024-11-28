package lab.jee.project.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ProjectModel implements Serializable {

    private UUID id;

    private String title;

    private double budget;

    private String priority;

}