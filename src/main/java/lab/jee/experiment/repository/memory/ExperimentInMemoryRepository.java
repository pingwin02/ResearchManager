package lab.jee.experiment.repository.memory;

import lab.jee.datastore.component.DataStore;
import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.repository.api.ExperimentRepository;
import lab.jee.project.entity.Project;
import lab.jee.researcher.entity.Researcher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ExperimentInMemoryRepository implements ExperimentRepository {

    private final DataStore store;

    public ExperimentInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Experiment> findByIdAndResearcher(UUID id, Researcher researcher) {
        return store.findAllExperiments().stream()
                .filter(experiment -> experiment.getId().equals(id) && experiment.getResearcher().equals(researcher))
                .findFirst();
    }

    @Override
    public Optional<Experiment> findByIdAndProject(UUID id, Project project) {
        return store.findAllExperiments().stream()
                .filter(experiment -> experiment.getId().equals(id) && experiment.getProject().equals(project))
                .findFirst();
    }

    @Override
    public List<Experiment> findAllByResearcher(Researcher researcher) {
        return store.findAllExperiments().stream()
                .filter(experiment -> experiment.getResearcher().equals(researcher))
                .toList();
    }

    @Override
    public List<Experiment> findAllByProject(Project project) {
        return store.findAllExperiments().stream()
                .filter(experiment -> experiment.getProject().equals(project))
                .toList();
    }

    @Override
    public Optional<Experiment> find(UUID id) {
        return store.findAllExperiments().stream()
                .filter(experiment -> experiment.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Experiment> findAll() {
        return store.findAllExperiments();
    }

    @Override
    public void create(Experiment entity) {
        store.createExperiment(entity);
    }

    @Override
    public void delete(UUID id) {
        store.deleteExperiment(id);
    }

    @Override
    public void update(Experiment entity) {
        store.updateExperiment(entity);
    }
}
