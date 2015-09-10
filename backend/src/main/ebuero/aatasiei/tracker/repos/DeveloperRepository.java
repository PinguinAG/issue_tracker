package ebuero.aatasiei.tracker.repos;

import ebuero.aatasiei.tracker.model.entities.Developer;
import ebuero.aatasiei.tracker.repos.internal.AbstractRepository;

/**
 * Repository that manages {@link Developer} entities.
 *
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
public interface DeveloperRepository extends AbstractRepository<Developer, Long> {

    /**
     * Deletes a given entity.
     *
     * @param entity - {@link Developer} instance. Not null.
     *
     * @throws IllegalArgumentException in case the given entity is null.
     */
    void delete(Developer entity);


    /**
     * @return the number of entities.
     */
    long count();
}
