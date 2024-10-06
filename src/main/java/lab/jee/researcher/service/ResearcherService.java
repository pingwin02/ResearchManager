package lab.jee.researcher.service;

import lab.jee.crypto.component.Pbkdf2PasswordHash;
import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.repository.api.ResearcherRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ResearcherService {

    private final ResearcherRepository researcherRepository;

    private final Pbkdf2PasswordHash passwordHash;

    private final Path avatarDirectory;

    public ResearcherService(ResearcherRepository researcherRepository, Pbkdf2PasswordHash passwordHash, String avatarDirectory) {
        this.researcherRepository = researcherRepository;
        this.passwordHash = passwordHash;
        this.avatarDirectory = Paths.get(System.getProperty("user.dir").split("target")[0], avatarDirectory);
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

    public Optional<byte[]> getAvatar(UUID id) {
        return researcherRepository.find(id).flatMap(researcher -> {
            if (researcher.getAvatarPath() == null) {
                return Optional.empty();
            }
            Path filePath = avatarDirectory.resolve(researcher.getAvatarPath());
            try {
                return Optional.of(Files.readAllBytes(filePath));
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read avatar", e);
            }
        });
    }

    public void updateAvatar(UUID id, InputStream is) {
        researcherRepository.find(id).ifPresent(researcher -> {
            try {
                String fileName = id.toString() + ".png";
                Path filePath = avatarDirectory.resolve(fileName);

                if (researcher.getAvatarPath() != null) {
                    Path oldFilePath = avatarDirectory.resolve(researcher.getAvatarPath());
                    Files.deleteIfExists(oldFilePath);
                }

                Files.copy(is, filePath);

                researcher.setAvatarPath(fileName);
                researcherRepository.update(researcher);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to save avatar", e);
            }
        });
    }

    public void deleteAvatar(UUID id) {
        researcherRepository.find(id).ifPresent(researcher -> {
            if (researcher.getAvatarPath() != null) {
                Path filePath = avatarDirectory.resolve(researcher.getAvatarPath());
                try {
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    throw new IllegalStateException("Failed to delete avatar", e);
                }
                researcher.setAvatarPath(null);
                researcherRepository.update(researcher);
            }
        });
    }
}
