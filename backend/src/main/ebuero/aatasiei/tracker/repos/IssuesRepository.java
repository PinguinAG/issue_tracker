package ebuero.aatasiei.tracker.repos;

import ebuero.aatasiei.tracker.model.entities.Issue;
import ebuero.aatasiei.tracker.repos.internal.AbstractRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository that manages {@link Issue} entities.
 *
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
public interface IssuesRepository extends AbstractRepository<Issue, Long> {

    /**
     * Retrieves all {@link Issue issues} in the ascending order of their creation date.
     *
     * @return iterable over all the issues that are found. Never null.
     */
    Iterable<Issue> findAllByOrderByCreationDateAsc();

    /**
     * Retrieves all {@link Issue issues} that are still open (not {@link ebuero.aatasiei.tracker.model.enums.StoryStatus#COMPLETED completed} or
     * {@link ebuero.aatasiei.tracker.model.enums.BugStatus#RESOLVED resolved}) in the ascending order of their
     * creation date.
     *
     * @return iterable over all the issues that are found. Never null.
     */
    @Query("select i from Issue i " +
            "where i.status not in ('RESOLVED', 'COMPLETED') " +
            "order by i.creationDate")
    Iterable<Issue> findAllOpenIssuesOrderByCreationDateAsc();

}
