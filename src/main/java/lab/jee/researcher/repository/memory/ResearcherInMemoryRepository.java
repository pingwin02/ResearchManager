package lab.jee.researcher.repository.memory;

import lab.jee.datastore.component.DataStore;
import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.repository.api.ResearcherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ResearcherInMemoryRepository implements ResearcherRepository {

    private final DataStore store;

    public ResearcherInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Researcher> find(UUID id) {
        return store.findAllResearchers().stream()
                .filter(researcher -> researcher.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Researcher> findAll() {
        return store.findAllResearchers();
    }

    @Override
    public void create(Researcher entity) {
        store.createResearcher(entity);
    }

    @Override
    public void delete(UUID id) {
        store.deleteResearcher(id);
    }

    @Override
    public void update(Researcher entity) {
        store.updateResearcher(entity);
    }

    @Override
    public Optional<Researcher> findByLogin(String login) {
        return store.findAllResearchers().stream()
                .filter(researcher -> researcher.getLogin().equals(login))
                .findFirst();
    }
}
