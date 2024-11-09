package lab.jee.experiment.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.repository.api.ExperimentRepository;
import lab.jee.project.repository.api.ProjectRepository;
import lab.jee.researcher.repository.api.ResearcherRepository;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
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

    public void create(Experiment experiment) {
        if (experimentRepository.find(experiment.getId()).isPresent()) {
            throw new IllegalArgumentException("Experiment already exists");
        }
        if (projectRepository.find(experiment.getProject().getId()).isEmpty()) {
            throw new IllegalArgumentException("Project does not exist");
        }
        if (researcherRepository.find(experiment.getResearcher().getId()).isEmpty()) {
            throw new IllegalArgumentException("Researcher does not exist");
        }

        experimentRepository.create(experiment);
    }

    public void update(Experiment experiment) {
        experimentRepository.update(experiment);
    }

    public void delete(UUID id) {
        experimentRepository.delete(id);
    }

    public Optional<Experiment> find(UUID id) {
        return experimentRepository.find(id);
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
