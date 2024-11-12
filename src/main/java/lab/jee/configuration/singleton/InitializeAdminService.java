package lab.jee.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.entity.ResearcherRole;
import lab.jee.researcher.repository.api.ResearcherRepository;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {

    private final ResearcherRepository repository;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InitializeAdminService(
            ResearcherRepository repository,
            @SuppressWarnings("CdiInjectionPointsInspection")
            Pbkdf2PasswordHash passwordHash
    ) {
        this.repository = repository;
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (repository.findByLogin("admin-service").isEmpty()) {

            Researcher admin = Researcher.builder()
                    .id(UUID.fromString("14d59f3a-057c-44d5-825a-19295a6600a8"))
                    .login("admin-service")
                    .password(passwordHash.generate("admin".toCharArray()))
                    .firstName("Admin")
                    .lastName("Service")
                    .role(ResearcherRole.LEAD_RESEARCHER)
                    .role(ResearcherRole.ASSISTANT)
                    .birthDate(LocalDate.of(1990, 1, 1))
                    .email("admin@jee.com")
                    .build();

            repository.create(admin);
        }
    }

}
