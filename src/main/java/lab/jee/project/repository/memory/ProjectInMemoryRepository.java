package lab.jee.project.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lab.jee.datastore.component.DataStore;
import lab.jee.project.entity.Project;
import lab.jee.project.repository.api.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class ProjectInMemoryRepository implements ProjectRepository {

    private final DataStore store;

    @Inject
    public ProjectInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Project> find(UUID id) {
        return store.findAllProjects().stream()
                .filter(project -> project.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Project> findAll() {
        return store.findAllProjects();
    }

    @Override
    public void create(Project entity) {
        store.createProject(entity);
    }

    @Override
    public void delete(UUID id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void update(Project entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
