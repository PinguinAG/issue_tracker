package ebuero.aatasiei.tracker.services.impl.helpers;

import ebuero.aatasiei.tracker.model.entities.Developer;
import ebuero.aatasiei.tracker.model.entities.Issue;
import ebuero.aatasiei.tracker.repos.internal.AbstractRepository;
import ebuero.aatasiei.tracker.services.interfaces.internal.IssueService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static ebuero.aatasiei.tracker.utils.MorePreconditions.checkArgumentNonNull;

/**
 * Abstract services that helps manage {@link Issue issues}.
 *
 * @param <T>   a concrete {@link Issue} type.
 * @param <REP> a repository that can be used to manage the concrete issue type.
 *
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
public abstract class AbstractIssueService<T extends Issue, REP extends AbstractRepository<T, Long>>
        implements IssueService<T> {

    /**
     * A repository that can be used to manage {@link T} instances.
     */
    protected final REP repository;

    protected AbstractIssueService(REP repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public T updateIssue(T issue) {

        checkArgumentNonNull(issue, "Issue can not be null!");
        checkArgumentNonNull(issue.getId(), "Issue must have been already persisted!");

        return repository.save(issue);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Optional<T> getIssue(Long id) {

        checkArgumentNonNull(id, "Id can not be null!");

        return repository.findOne(id);
    }

    /**
     * Populates the description and assigned developer on the issue.
     *
     * @param issue             - the issue to update. Not null.
     * @param description       - the description to add.
     * @param assignedDeveloper - the asssigned developer. Not null.
     */
    protected void populateIssue(Issue issue, String description, Developer assignedDeveloper) {
        issue.setDescription(description);
        issue.setAssignedDeveloper(assignedDeveloper);
    }
}
