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
public class WeeklyPlanResponse {

    private final List<IssueResponse> stories;

    public WeeklyPlanResponse(WeeklyPlan plan) {
        this.stories = Lists.newArrayList();
        this.stories.addAll(plan.getStories().stream().map(IssueResponse::new).collect(Collectors.toList()));
    }

    public List<IssueResponse> getStories() {
        return stories;
    }
}
