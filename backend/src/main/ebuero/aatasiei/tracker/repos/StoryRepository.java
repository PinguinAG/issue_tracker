package ebuero.aatasiei.tracker.repos;

import ebuero.aatasiei.tracker.model.entities.Story;
import ebuero.aatasiei.tracker.repos.internal.AbstractRepository;

import java.util.List;

/**
 * Repository that manages {@link Story} entities.
 *
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
public interface StoryRepository extends AbstractRepository<Story, Long> {

    /**
     * Retrieves all the {@link Story stories} that have their status matching the provided one, and their estimated
     * points below or equal the provided value.
     * <p>
     * Equivalent to: <code>SELECT s FROM Story s WHERE s.status = :allowedStatus AND s.estimatedPoints <= :points</code>
     *
     * @param allowedStatus - the status to match. Not null.
     * @param points        - the maximum number of points. Not null.
     *
     * @return a list of {@link Story stories}. Never null.
     *
     */
    List<Story> findByStatusAndEstimatedPointsLessThanEqualOrderByEstimatedPointsDesc(String allowedStatus, Integer points);

}
