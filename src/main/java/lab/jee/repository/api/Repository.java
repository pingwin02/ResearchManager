package lab.jee.repository.api;

import java.util.List;
import java.util.Optional;

public interface Repository<E, K> {

    Optional<E> find(K id);

    List<E> findAll();

    void create(E entity);

    void update(E entity);

    void delete(K id);

}

