package lab.jee.experiment.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.repository.api.ExperimentRepository;
import lab.jee.project.entity.Project;
import lab.jee.researcher.entity.Researcher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class ExperimentPersistenceRepository implements ExperimentRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Experiment> find(UUID id) {
        return Optional.ofNullable(em.find(Experiment.class, id));
    }

    @Override
    public List<Experiment> findAll() {
        return em.createQuery("SELECT e FROM Experiment e", Experiment.class).getResultList();
    }

    @Override
    public void create(Experiment entity) {
        em.persist(entity);
        em.refresh(em.find(Project.class, entity.getProject().getId()));
        em.refresh(em.find(Researcher.class, entity.getResearcher().getId()));
    }

    @Override
    public void update(Experiment entity) {
        em.merge(entity);
    }

    @Override
    public void delete(UUID id) {
        Experiment e = em.find(Experiment.class, id);
        Project p = em.find(Project.class, e.getProject().getId());
        em.remove(e);
        em.refresh(p);
    }

    @Override
    public List<Experiment> findAllByResearcher(Researcher researcher) {
        Researcher r = em.find(Researcher.class, researcher.getId());
        return r.getExperiments();
    }

    @Override
    public List<Experiment> findAllByProject(Project project) {
        Project p = em.find(Project.class, project.getId());
        return p.getExperiments();
    }
}
