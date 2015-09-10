package ebuero.aatasiei.tracker.services.interfaces.internal;

import ebuero.aatasiei.tracker.model.entities.Issue;

import java.util.Optional;

/**
 * Service used to interact with {@link Issue} entities.
 *
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
public interface IssueService<T extends Issue> {


    /**
     * Persists all the changes on the provided issue.
     *
     * @param issue - the issue to update. Not null.
     *
     * @return the persisted issue entity.
     */
    T updateIssue(T issue);

    /**
     * Retrieves the issue with the specified id, if it exists.
     *
     * @param id - the id to look for. Not null.
     *
     * @return {@link Optional} possibly containing the found {@link T}. {@link Optional#empty()} otherwise.
     */
    Optional<T> getIssue(Long id);
}
