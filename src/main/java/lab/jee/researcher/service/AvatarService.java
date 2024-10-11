package lab.jee.researcher.service;

import lab.jee.researcher.repository.api.ResearcherRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

public class AvatarService {

    private final ResearcherRepository researcherRepository;
    private final Path avatarDirectory;

    public AvatarService(ResearcherRepository researcherRepository, String avatarDirectory) {
        this.researcherRepository = researcherRepository;
        this.avatarDirectory = Paths.get(System.getProperty("user.dir").split("target")[0], avatarDirectory);
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

    public void createAvatar(UUID id, InputStream is) {
        researcherRepository.find(id).ifPresent(researcher -> {
            try {
                if (researcher.getAvatarPath() != null) {
                    throw new IllegalStateException("Avatar already exists");
                }

                String fileName = id.toString() + ".png";
                Path filePath = avatarDirectory.resolve(fileName);

                Files.copy(is, filePath);

                researcher.setAvatarPath(fileName);
                researcherRepository.update(researcher);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to save avatar", e);
            }
        });
    }

    public void updateAvatar(UUID id, InputStream is) {
        researcherRepository.find(id).ifPresent(researcher -> {
            try {
                if (researcher.getAvatarPath() == null) {
                    throw new IllegalStateException("Avatar doesn't exist");
                }

                String fileName = id.toString() + ".png";
                Path filePath = avatarDirectory.resolve(fileName);

                Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);

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
