package lab.jee.project.service;

import lab.jee.project.entity.Project;
import lab.jee.project.repository.api.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectService {

    private final ProjectRepository projectRepository;

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
}
