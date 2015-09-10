package ebuero.aatasiei.tracker.repos.internal;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Optional;

/**
 * Abstract repository providing basic create, read, update method.
 *
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
@NoRepositoryBean
public interface AbstractRepository<T, ID extends Serializable> extends Repository<T, ID> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity
     *
     * @return the saved entity
     */
    T save(T entity);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Optional<T> findOne(ID id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Iterable<T> findAll();
}
