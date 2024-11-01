package lab.jee.project.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lab.jee.project.entity.Project;
import lab.jee.project.repository.api.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class ProjectPersistenceRepository implements ProjectRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Project> find(UUID id) {
        return Optional.ofNullable(em.find(Project.class, id));
    }

    @Override
    public List<Project> findAll() {
        return em.createQuery("SELECT p FROM Project p", Project.class).getResultList();
    }

    @Override
    public void create(Project entity) {
        em.persist(entity);
    }

    @Override
    public void update(Project entity) {
        em.merge(entity);
    }

    @Override
    public void delete(UUID id) {
        em.remove(em.find(Project.class, id));
    }
}
