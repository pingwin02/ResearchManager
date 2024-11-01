package lab.jee.experiment.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lab.jee.experiment.entity.Experiment;
import lab.jee.experiment.repository.api.ExperimentRepository;
import lab.jee.project.entity.Project;
import lab.jee.researcher.entity.Researcher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
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
    }

    @Override
    public void update(Experiment entity) {
        em.merge(entity);
    }

    @Override
    public void delete(UUID id) {
        em.remove(em.find(Experiment.class, id));
    }

    @Override
    public Optional<Experiment> findByIdAndResearcher(UUID id, Researcher researcher) {
        try {
            return Optional.of(em.createQuery("SELECT e FROM Experiment e WHERE e.id = :id AND e.researcher = :researcher", Experiment.class)
                    .setParameter("id", id)
                    .setParameter("researcher", researcher)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Experiment> findByIdAndProject(UUID id, Project project) {
        try {
            return Optional.of(em.createQuery("SELECT e FROM Experiment e WHERE e.id = :id AND e.project = :project", Experiment.class)
                    .setParameter("id", id)
                    .setParameter("project", project)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Experiment> findAllByResearcher(Researcher researcher) {
        return em.createQuery("SELECT e FROM Experiment e WHERE e.researcher = :researcher", Experiment.class)
                .setParameter("researcher", researcher)
                .getResultList();
    }

    @Override
    public List<Experiment> findAllByProject(Project project) {
        return em.createQuery("SELECT e FROM Experiment e WHERE e.project = :project", Experiment.class)
                .setParameter("project", project)
                .getResultList();
    }
}
