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
public class PatchResearcherRequest {

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String email;

}
