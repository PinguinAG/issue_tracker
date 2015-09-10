package ebuero.aatasiei.tracker.repos;

import ebuero.aatasiei.tracker.model.entities.Bug;
import ebuero.aatasiei.tracker.repos.internal.AbstractRepository;

/**
 * Repository that manages {@link Bug} entities.
 *
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
public interface BugRepository extends AbstractRepository<Bug, Long> {
}
