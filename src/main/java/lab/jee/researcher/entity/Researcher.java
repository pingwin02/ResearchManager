package lab.jee.researcher.entity;

import jakarta.persistence.*;
import lab.jee.experiment.entity.Experiment;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
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

    @Column(nullable = false, unique = true)
    private String login;

    private String firstName;

    private String lastName;

    @CollectionTable(name = "researchers__roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @Singular
    private Set<String> roles;

    private LocalDate birthDate;

    @ToString.Exclude
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String avatarPath;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "researcher", cascade = CascadeType.ALL)
    private List<Experiment> experiments;

}
