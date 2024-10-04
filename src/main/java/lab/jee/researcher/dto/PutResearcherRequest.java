package lab.jee.researcher.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PutResearcherRequest {

    private String login;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    @ToString.Exclude
    private String password;

    private String email;

}
