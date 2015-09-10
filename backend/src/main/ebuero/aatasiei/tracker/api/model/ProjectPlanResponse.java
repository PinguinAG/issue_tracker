package ebuero.aatasiei.tracker.api.model;

import com.google.common.collect.Lists;
import ebuero.aatasiei.tracker.model.entities.WeeklyPlan;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
public class ProjectPlanResponse {

    private final List<WeeklyPlanResponse> plan;

    public ProjectPlanResponse(List<WeeklyPlan> projectPlan) {
        this.plan = Lists.newArrayList();
        this.plan.addAll(projectPlan.stream().map(WeeklyPlanResponse::new).collect(Collectors.toList()));
    }

    public List<WeeklyPlanResponse> getPlan() {
        return plan;
    }
}
