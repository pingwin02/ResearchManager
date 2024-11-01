package lab.jee.experiment.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.repository.api.ExperimentRepository;
import lab.jee.project.entity.Project;
import lab.jee.project.repository.api.ProjectRepository;
import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.repository.api.ResearcherRepository;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class ExperimentService {

    private final ExperimentRepository experimentRepository;

    private final ProjectRepository projectRepository;

    private final ResearcherRepository researcherRepository;

    @Inject
    public ExperimentService(
            ExperimentRepository experimentRepository,
            ProjectRepository projectRepository,
            ResearcherRepository researcherRepository) {
        this.experimentRepository = experimentRepository;
        this.projectRepository = projectRepository;
        this.researcherRepository = researcherRepository;
    }

    @Transactional
    public void create(Experiment experiment) {
        if (experimentRepository.find(experiment.getId()).isPresent()) {
            throw new IllegalArgumentException("Experiment already exists");
        }
        if (projectRepository.find(experiment.getProject().getId()).isEmpty()) {
            throw new IllegalArgumentException("Project does not exist");
        }

        experimentRepository.create(experiment);

        projectRepository.find(experiment.getProject().getId())
                .ifPresent(project -> project.getExperiments().add(experiment));
    }

    @Transactional
    public void update(Experiment experiment) {
        experimentRepository.update(experiment);
    }

    @Transactional
    public void delete(UUID id) {
        experimentRepository.delete(id);
    }

    public Optional<Experiment> find(UUID id) {
        return experimentRepository.find(id);
    }

    public Optional<Experiment> find(UUID id, Researcher researcher) {
        return experimentRepository.findByIdAndResearcher(id, researcher);
    }

    public Optional<Experiment> find(UUID id, Project project) {
        return experimentRepository.findByIdAndProject(id, project);
    }

    public List<Experiment> findAll() {
        return experimentRepository.findAll();
    }

    public Optional<List<Experiment>> findAllByResearcher(UUID id) {
        return researcherRepository.find(id)
                .map(experimentRepository::findAllByResearcher);
    }

    public Optional<List<Experiment>> findAllByProject(UUID id) {
        return projectRepository.find(id)
                .map(experimentRepository::findAllByProject);
    }
}
