package ebuero.aatasiei.tracker.services.interfaces;

import ebuero.aatasiei.tracker.model.entities.WeeklyPlan;

import java.util.List;

/**
 * Service used to interact with {@link WeeklyPlan} entities.
 *
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
public interface WeeklyPlanService {

    /**
     * Retrieves all the weekly plans.
     *
     * @return a list containing {@link WeeklyPlan} instances. Never null.
     */
    List<WeeklyPlan> getAllWeeklyPlans();

    /**
     * Removes all the weekly plans.
     */
    void clearWeeklyPlans();

    /**
     * Creates a new weekly plan using the provided index.
     *
     * @param index - the index of the week. Must be a positive integer.
     *
     * @return the created {@link WeeklyPlan}.
     */
    WeeklyPlan createWeeklyPlan(int index);

    /**
     * Creates a new weekly plan using the provided index.
     *
     * @param weeklyPlan - a weekly plan containing updates that need to be saved. Not null.
     *
     * @return the updated {@link WeeklyPlan}.
     */
    WeeklyPlan update(WeeklyPlan weeklyPlan);
}
