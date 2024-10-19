package lab.jee.project.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ProjectEditModel {

    private String title;

    private double budget;

    private String priority;

}