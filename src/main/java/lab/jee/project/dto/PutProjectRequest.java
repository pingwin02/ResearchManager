package lab.jee.project.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PutProjectRequest {

    private String title;

    private double budget;

    private String priority;
}
