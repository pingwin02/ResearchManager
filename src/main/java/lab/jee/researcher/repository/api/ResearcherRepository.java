package lab.jee.researcher.repository.api;

import lab.jee.repository.api.Repository;
import lab.jee.researcher.entity.Researcher;

import java.util.Optional;
import java.util.UUID;

public interface ResearcherRepository extends Repository<Researcher, UUID> {

    Optional<Researcher> findByLogin(String login);
}
