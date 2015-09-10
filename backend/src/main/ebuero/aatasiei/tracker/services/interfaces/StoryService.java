package ebuero.aatasiei.tracker.services.interfaces;

import ebuero.aatasiei.tracker.model.entities.Developer;
import ebuero.aatasiei.tracker.model.entities.Story;
import ebuero.aatasiei.tracker.model.enums.StoryStatus;
import ebuero.aatasiei.tracker.services.interfaces.internal.IssueService;

import java.util.List;

/**
 * Service used to interact with {@link Story} entities.
 *
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
public interface StoryService extends IssueService<Story> {

    /**
     * Creates a single story and returns it.
     *
     * @param title             the title of the story. Not null.
     * @param description       the description of the story.
     * @param assignedDeveloper the assigned developer.
     * @param estimate          the estimated story points value for the story.
     * @param status            the current status of the story. Not null.
     *
     * @return the created {@link Story}.
     */
    Story createStory(String title, String description, Developer assignedDeveloper, Integer estimate, StoryStatus
            status);

    /**
     * Retrieves a list of estimated stories in descending order, with estimates lower or equal than the provided value.
     *
     * @param points - the maximum estimate allowed.
     *
     * @return a list of {@link Story stories}. Never null.
     */
    List<Story> getEstimatedStoriesInDescendingOrderWithEstimationsLowerOrEqual(int points);
}
