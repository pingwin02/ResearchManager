package lab.jee.researcher.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "researchers")
public class Researcher implements Serializable {

    @Id
    private UUID id;

    private String login;

    private String firstName;

    private String lastName;

    private String role;

    private LocalDate birthDate;

    @ToString.Exclude
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String avatarPath;

}
