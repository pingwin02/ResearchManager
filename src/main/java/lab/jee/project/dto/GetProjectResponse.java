package lab.jee.project.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class GetProjectResponse {

    private UUID id;

    private String title;

    private double budget;

    private String priority;
}
