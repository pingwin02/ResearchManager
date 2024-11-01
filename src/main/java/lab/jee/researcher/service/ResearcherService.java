package lab.jee.researcher.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lab.jee.crypto.component.Pbkdf2PasswordHash;
import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.repository.api.ResearcherRepository;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class ResearcherService {

    private final ResearcherRepository researcherRepository;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ResearcherService(ResearcherRepository researcherRepository, Pbkdf2PasswordHash passwordHash) {
        this.researcherRepository = researcherRepository;
        this.passwordHash = passwordHash;
    }

    public Optional<Researcher> find(UUID id) {
        return researcherRepository.find(id);
    }

    public Optional<Researcher> find(String login) {
        return researcherRepository.findByLogin(login);
    }

    public List<Researcher> findAll() {
        return researcherRepository.findAll();
    }

    @Transactional
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

    @Transactional
    public void update(Researcher researcher) {
        researcherRepository.update(researcher);
    }

    @Transactional
    public void delete(UUID id) {
        researcherRepository.delete(id);
    }
}
