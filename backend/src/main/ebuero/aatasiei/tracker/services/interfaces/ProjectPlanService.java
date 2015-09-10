package ebuero.aatasiei.tracker.services.interfaces;

import ebuero.aatasiei.tracker.model.entities.WeeklyPlan;

import java.util.List;

/**
 * Service that retrieves and computes the project plan.
 *
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
public interface ProjectPlanService {

    /**
     * The mythical amount of points a developer can complete per week.
     */
    long DEVELOPER_POINTS_PER_WEEK = 10L;

    /**
     * Retrieves the project plan as a list of weekly plans. The index in the list represents the week index.
     *
     * @return a list of {@link WeeklyPlan}. Never null.
     */
    List<WeeklyPlan> getProjectPlan();

    /**
     * Generates a new project plan.
     */
    void recomputeProjectPlan();
}
