package ebuero.aatasiei.tracker.services.impl;

import com.google.common.collect.Lists;
import ebuero.aatasiei.tracker.model.entities.Story;
import ebuero.aatasiei.tracker.model.entities.WeeklyPlan;
import ebuero.aatasiei.tracker.services.interfaces.DeveloperService;
import ebuero.aatasiei.tracker.services.interfaces.ProjectPlanService;
import ebuero.aatasiei.tracker.services.interfaces.StoryService;
import ebuero.aatasiei.tracker.services.interfaces.WeeklyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@Service
public class ProjectPlanServiceImpl implements ProjectPlanService {

    // FIXME: remove circular dependency
    @Autowired
    private DeveloperService developerService;

    // FIXME: remove circular dependency
    @Autowired
    private StoryService storyService;

    private final WeeklyPlanService weeklyPlanService;

    @Autowired
    ProjectPlanServiceImpl(WeeklyPlanService weeklyPlanService) {
        this.weeklyPlanService = weeklyPlanService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<WeeklyPlan> getProjectPlan() {
        return weeklyPlanService.getAllWeeklyPlans();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void recomputeProjectPlan() {
        weeklyPlanService.clearWeeklyPlans();

        final long developerCount = developerService.getDeveloperCount();
        if (developerCount == 0L) {
            return;
        }

        final int maxPoints = (int) (developerCount * DEVELOPER_POINTS_PER_WEEK);
        final List<Story> estimatedStories = storyService.getEstimatedStoriesInDescendingOrderWithEstimationsLowerOrEqual
                (maxPoints);

        if (estimatedStories.isEmpty()) {
            return;
        }

        List<Story> stories = Lists.newArrayList(estimatedStories);

        checkStoriesInAscOrder(stories);

        createDistributionPlan(stories, maxPoints);
    }

    // standard greedy algorithm ~= O(n*log(n)) but object removal from array list might slow it down
    private void createDistributionPlan(final List<Story> stories, final int maxPoints) {

        // fail safe to avoid running forever
        int initialStoryCount = stories.size();

        int weekIndex = 1;
        WeeklyPlan currentPlan = weeklyPlanService.createWeeklyPlan(weekIndex);
        int currentPoints = 0;

        List<Story> leftOverStories = stories;

        while (!leftOverStories.isEmpty() && initialStoryCount >= weekIndex) {

            final int availablePoints = maxPoints - currentPoints;

            if (canStillSelectStories(leftOverStories, availablePoints)) {

                final boolean isCurrentPlanEmpty = currentPlan.getStories().isEmpty();

                final int storyIndex = getLargestStoryThatFits(leftOverStories, isCurrentPlanEmpty, availablePoints);
                final Story currentStory = leftOverStories.get(storyIndex);

                leftOverStories = updateStoriesList(leftOverStories, storyIndex);

                currentPoints += currentStory.getEstimatedPoints();
                currentPlan.addStory(currentStory);

            } else {

                // store the stories added to the current plan
                weeklyPlanService.update(currentPlan);

                // create the next plan
                currentPoints = 0;
                weekIndex++;
                currentPlan = weeklyPlanService.createWeeklyPlan(weekIndex);
            }
        }

        // save the last plan created
        if (currentPoints != 0) {
            weeklyPlanService.update(currentPlan);
        }
    }

    private boolean canStillSelectStories(List<Story> stories, int availablePoints) {
        return availablePoints >= stories.get(stories.size() - 1).getEstimatedPoints();
    }

    private List<Story> updateStoriesList(List<Story> stories, int storyIndex) {

        if (storyIndex == 0) {
            stories = stories.subList(1, stories.size());
        } else {
            stories.remove(storyIndex);
        }
        return stories;
    }

    private int getLargestStoryThatFits(List<Story> stories, final boolean isCurrentPlanEmpty, int
            availablePoints) {

        checkArgument(!stories.isEmpty(), "Stories is empty");

        if (isCurrentPlanEmpty) {
            // get the largest
            return 0;
        } else {
            final Integer indexOfTheLargestStoryThatFits = binarySearch(stories, availablePoints);
            return indexOfTheLargestStoryThatFits;
        }

    }

    private void checkStoriesInAscOrder(List<Story> stories) {
        for (int current = 1, last = 0; current < stories.size(); last = current++) {

            final Story currentStory = stories.get(current);
            final Story previousStory = stories.get(last);

            checkState(currentStory.getEstimatedPoints() <= previousStory.getEstimatedPoints(),
                    "Returned stories not in descending order");
        }
    }

    // copied from the Collections.binarySearch and customized
    private static Integer binarySearch(List<? extends Story> list, Integer estimatedPoints) {

        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Integer midVal = list.get(mid).getEstimatedPoints();
            int cmp = Integer.compare(estimatedPoints, midVal);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return low;  // key not found
    }
}
