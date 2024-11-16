package lab.jee.researcher.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lab.jee.researcher.entity.ResearcherRole;
import lab.jee.researcher.repository.api.ResearcherRepository;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@RolesAllowed(ResearcherRole.LEAD_RESEARCHER)
public class AvatarService {

    private final ResearcherRepository researcherRepository;
    @Resource(name = "avatarsDir")
    private String avatarsDir;
    private Path avatarDirectory;

    @Inject
    public AvatarService(ResearcherRepository researcherRepository) {
        this.researcherRepository = researcherRepository;
    }

    @PostConstruct
    public void init() {
        if (avatarsDir == null) {
            throw new IllegalStateException("avatarsDir is not properly configured");
        }
        avatarDirectory = Paths.get(System.getProperty("server.config.dir").split("target")[0],
                "src/main/webapp/", avatarsDir);
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
                    throw new IllegalArgumentException("Avatar already exists");
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
                    throw new IllegalArgumentException("Avatar doesn't exist");
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

    public void createAvatarDirectoryIfNotExists() {
        File directory = new File(avatarDirectory.toString());
        if (!directory.exists()) {
            directory.mkdirs();
        } else {
            for (File file : directory.listFiles()) {
                file.delete();
            }
        }
    }
}
