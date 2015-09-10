package ebuero.aatasiei.tracker.repos;

import ebuero.aatasiei.tracker.model.entities.WeeklyPlan;
import ebuero.aatasiei.tracker.repos.internal.AbstractRepository;

/**
 * Repository that manages {@link WeeklyPlan} entities.
 *
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
public interface WeeklyPlanRepository extends AbstractRepository<WeeklyPlan, Long> {

    /**
     * Retrieves all the weekly plans in the ascending order of the index.
     *
     * @return - iterable over all the weekly plans. Never null.
     */
    Iterable<WeeklyPlan> findAllByOrderByIndexAsc();

    /**
     * Removes all the weekly plans from the repository.
     */
    void deleteAll();
}
