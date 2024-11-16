package lab.jee.project.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lab.jee.project.entity.Project;
import lab.jee.project.repository.api.ProjectRepository;
import lab.jee.researcher.entity.ResearcherRole;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@RolesAllowed(ResearcherRole.ASSISTANT)
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

    @RolesAllowed(ResearcherRole.LEAD_RESEARCHER)
    public void create(Project project) {
        if (projectRepository.find(project.getId()).isPresent()) {
            throw new IllegalArgumentException("Project already exists");
        }
        projectRepository.create(project);
    }

    @RolesAllowed(ResearcherRole.LEAD_RESEARCHER)
    public void update(Project project) {
        projectRepository.update(project);
    }

    @RolesAllowed(ResearcherRole.LEAD_RESEARCHER)
    public void delete(UUID id) {
        projectRepository.delete(id);
    }

}
