package ebuero.aatasiei.tracker.services.interfaces;

import ebuero.aatasiei.tracker.model.entities.Bug;
import ebuero.aatasiei.tracker.model.entities.Developer;
import ebuero.aatasiei.tracker.model.enums.BugPriority;
import ebuero.aatasiei.tracker.model.enums.BugStatus;
import ebuero.aatasiei.tracker.services.interfaces.internal.IssueService;

/**
 * Service used to interact with {@link Bug} entities.
 *
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
public interface BugService extends IssueService<Bug> {

    /**
     * Creates a single bug and returns it.
     *
     * @param title             the title of the bug. Not null.
     * @param description       the description of the bug.
     * @param assignedDeveloper the assigned developer.
     * @param priority          the priority of the bug.
     * @param status            the current status of the bug. Not null.
     *
     * @return the created {@link Bug}.
     */
    Bug createBug(String title, String description, Developer assignedDeveloper, BugPriority priority, BugStatus
            status);

}
