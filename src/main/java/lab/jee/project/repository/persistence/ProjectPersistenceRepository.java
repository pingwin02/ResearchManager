package lab.jee.project.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lab.jee.project.entity.Project;
import lab.jee.project.repository.api.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Project> query = cb.createQuery(Project.class);
        Root<Project> root = query.from(Project.class);
        query.select(root);
        return em.createQuery(query).getResultList();
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
    public void detach(Project entity) {
        em.detach(entity);
    }

    @Override
    public void delete(UUID id) {
        Project entity = em.find(Project.class, id);
        em.refresh(entity);
        em.remove(entity);
    }
}
