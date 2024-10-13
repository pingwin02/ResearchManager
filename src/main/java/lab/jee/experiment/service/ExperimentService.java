package lab.jee.experiment.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.repository.api.ExperimentRepository;
import lab.jee.project.entity.Project;
import lab.jee.researcher.entity.Researcher;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class ExperimentService {

    private final ExperimentRepository experimentRepository;

    @Inject
    public ExperimentService(ExperimentRepository experimentRepository) {
        this.experimentRepository = experimentRepository;
    }

    public void create(Experiment experiment) {
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

    public Optional<Experiment> find(Researcher researcher, UUID id) {
        return experimentRepository.findByIdAndResearcher(id, researcher);
    }

    public Optional<Experiment> find(Project project, UUID id) {
        return experimentRepository.findByIdAndProject(id, project);
    }

    public List<Experiment> findAll() {
        return experimentRepository.findAll();
    }

    public List<Experiment> findAll(Researcher researcher) {
        return experimentRepository.findAllByResearcher(researcher);
    }

    public List<Experiment> findAll(Project project) {
        return experimentRepository.findAllByProject(project);
    }
}
