package ebuero.aatasiei.tracker.services.impl;

import ebuero.aatasiei.tracker.model.entities.Developer;
import ebuero.aatasiei.tracker.model.entities.Story;
import ebuero.aatasiei.tracker.model.enums.StoryStatus;
import ebuero.aatasiei.tracker.repos.StoryRepository;
import ebuero.aatasiei.tracker.services.impl.helpers.AbstractIssueService;
import ebuero.aatasiei.tracker.services.interfaces.ProjectPlanService;
import ebuero.aatasiei.tracker.services.interfaces.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static ebuero.aatasiei.tracker.model.enums.StoryStatus.NEW;
import static ebuero.aatasiei.tracker.utils.MorePreconditions.checkArgumentNonNull;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@Service
public class StoryServiceImpl extends AbstractIssueService<Story, StoryRepository> implements StoryService {

    private final ProjectPlanService projectPlanService;

    @Autowired
    StoryServiceImpl(StoryRepository repository, ProjectPlanService projectPlanService) {
        super(repository);
        this.projectPlanService = projectPlanService;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Story createStory(String title, String description, Developer assignedDeveloper, Integer estimate, StoryStatus status) {

        checkArgumentNonNull(title, "Title can not be null!");
        checkArgumentNonNull(status, "Status can not be null!");

        checkArgument(isNull(estimate) || estimate >= 0, "Negative estimate.");
        checkArgument(status == NEW || nonNull(estimate), "Stories that are ESTIMATED or CLOSED must " +
                "have their estimate set");

        Story story = new Story(title);

        populateIssue(story, description, assignedDeveloper);

        story.setEstimatedPoints(estimate);
        story.setStatus(status);

        Story savedStory = repository.save(story);

        projectPlanService.recomputeProjectPlan();

        return savedStory;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Story updateIssue(Story story) {
        Story updatedStory = super.updateIssue(story);

        projectPlanService.recomputeProjectPlan();

        return updatedStory;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Story> getEstimatedStoriesInDescendingOrderWithEstimationsLowerOrEqual(int points) {
        final String estimated = StoryStatus.ESTIMATED.toString();
        return repository.findByStatusAndEstimatedPointsLessThanEqualOrderByEstimatedPointsDesc(estimated, points);
    }
}
