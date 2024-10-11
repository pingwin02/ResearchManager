package lab.jee.researcher.service;

import lab.jee.crypto.component.Pbkdf2PasswordHash;
import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.repository.api.ResearcherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ResearcherService {

    private final ResearcherRepository researcherRepository;
    private final Pbkdf2PasswordHash passwordHash;

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

    public void create(Researcher researcher) {
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
