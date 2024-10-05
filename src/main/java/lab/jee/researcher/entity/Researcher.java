package lab.jee.researcher.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Researcher implements Serializable {

    private UUID id;

    private String login;

    private String firstName;

    private String lastName;

    private String role;

    private LocalDate birthDate;

    @ToString.Exclude
    private String password;

    private String email;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] avatar;

}
