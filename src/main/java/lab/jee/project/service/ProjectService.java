package lab.jee.project.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lab.jee.project.entity.Project;
import lab.jee.project.repository.api.ProjectRepository;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Inject
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Optional<Project> find(UUID id) {
        return projectRepository.find(id);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public void create(Project project) {
        projectRepository.create(project);
    }

    public void delete(UUID id) {
        projectRepository.delete(id);
    }

    public void update(Project project) {
        projectRepository.update(project);
    }
}
