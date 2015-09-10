package ebuero.aatasiei.tracker.services.interfaces;

import ebuero.aatasiei.tracker.model.entities.Developer;

import java.util.List;
import java.util.Optional;

/**
 * Service used to interact with {@link Developer} entities.
 *
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
public interface DeveloperService {

    /**
     * Retrieves a list with all the developers.
     *
     * @return {@link List} containing all the developers.
     */
    List<Developer> getAllDevelopers();

    /**
     * Creates a single developer and returns it.
     *
     * @param name the name of the developer.
     *
     * @return the created {@link Developer}.
     */
    Developer createDeveloper(String name);

    /**
     * Retrieves the developer with the specified id, if it exists.
     *
     * @param id - the id to look for. Not null.
     *
     * @return {@link Optional} possibly containing the found {@link Developer}. {@link Optional#empty()} otherwise.
     */
    Optional<Developer> getDeveloper(Long id);

    /**
     * Deletes a persisted developer
     *
     * @param developer - the developer to be deleted. Not null.
     */
    void deleteDeveloper(Developer developer);

    /**
     * @return the total number of developers.
     */
    long getDeveloperCount();
}
