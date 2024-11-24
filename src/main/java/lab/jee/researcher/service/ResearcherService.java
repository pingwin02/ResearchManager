package lab.jee.researcher.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.entity.ResearcherRole;
import lab.jee.researcher.repository.api.ResearcherRepository;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@RolesAllowed(ResearcherRole.LEAD_RESEARCHER)
public class ResearcherService {

    private final ResearcherRepository researcherRepository;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ResearcherService(ResearcherRepository researcherRepository,
                             @SuppressWarnings("CdiInjectionPointsInspection")
                             Pbkdf2PasswordHash passwordHash) {
        this.researcherRepository = researcherRepository;
        this.passwordHash = passwordHash;
    }

    @RolesAllowed(ResearcherRole.ASSISTANT)
    public Optional<Researcher> find(UUID id) {
        return researcherRepository.find(id);
    }

    @RolesAllowed(ResearcherRole.ASSISTANT)
    public Optional<Researcher> find(String login) {
        return researcherRepository.findByLogin(login);
    }

    @RolesAllowed(ResearcherRole.ASSISTANT)
    public List<Researcher> findAll() {
        return researcherRepository.findAll();
    }

    @PermitAll
    public void create(Researcher researcher) {
        if (researcherRepository.findByLogin(researcher.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Researcher already exists");
        }
        researcher.setPassword(passwordHash.generate(researcher.getPassword().toCharArray()));
        researcherRepository.create(researcher);
    }

    public boolean verify(String login, String password) {
        return find(login).map(researcher -> passwordHash.verify(password.toCharArray(), researcher.getPassword())).orElse(false);
    }

    public void update(Researcher researcher) {
        researcherRepository.update(researcher);
    }

    public void delete(UUID id) {
        researcherRepository.delete(id);
    }
}
