package ebuero.aatasiei.tracker.services.interfaces;

import ebuero.aatasiei.tracker.model.entities.Issue;

import java.util.List;

/**
 * Service used to interact with multiple {@link Issue} entities as a whole.
 *
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
public interface IssuesService {

    /**
     * Retrieves all {@link Issue issues} ordered by their creation date (in ascending order).
     *
     * @return list containing all the issues. Never null.
     */
    List<Issue> getAllOrderedByCreationDate();

    /**
     * Retrieves all open {@link Issue issues} ordered by their creation date (in ascending order).
     * <p>
     * In this context open means not {@link ebuero.aatasiei.tracker.model.enums.StoryStatus#COMPLETED completed} or
     * {@link ebuero.aatasiei.tracker.model.enums.BugStatus#RESOLVED resolved}.
     * </p>
     *
     * @return list containing all the issues. Never null.
     */
    List<Issue> getAllOpenOrderedByCreationDate();

}
