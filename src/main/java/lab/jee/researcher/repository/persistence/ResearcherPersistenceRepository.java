package lab.jee.researcher.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
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
        return em.createQuery("SELECT r FROM Researcher r", Researcher.class).getResultList();
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
    public void delete(UUID id) {
        Researcher entity = em.find(Researcher.class, id);
        em.refresh(entity);
        em.remove(entity);
    }

    @Override
    public Optional<Researcher> findByLogin(String login) {
        try {
            return Optional.of(em.createQuery("SELECT r FROM Researcher r WHERE r.login = :login", Researcher.class)
                    .setParameter("login", login)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
