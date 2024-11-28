package lab.jee.researcher.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lab.jee.researcher.entity.Researcher;
import lab.jee.researcher.repository.api.ResearcherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class ResearcherPersistenceRepository implements ResearcherRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Researcher> find(UUID id) {
        return Optional.ofNullable(em.find(Researcher.class, id));
    }

    @Override
    public List<Researcher> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Researcher> query = cb.createQuery(Researcher.class);
        Root<Researcher> root = query.from(Researcher.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Researcher entity) {
        em.persist(entity);
    }

    @Override
    public void update(Researcher entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Researcher entity) {
        em.detach(entity);
    }

    @Override
    public void delete(UUID id) {
        Researcher entity = em.find(Researcher.class, id);
        em.refresh(entity);
        em.remove(entity);
    }

    @Override
    public Optional<Researcher> findByLogin(String login) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Researcher> query = cb.createQuery(Researcher.class);
            Root<Researcher> root = query.from(Researcher.class);
            query.select(root).where(cb.equal(root.get("login"), login));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
