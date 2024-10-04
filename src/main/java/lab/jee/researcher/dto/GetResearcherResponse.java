package lab.jee.researcher.dto;

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
public class GetResearcherResponse {

    private UUID id;

    private String login;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate birthDate;

}
