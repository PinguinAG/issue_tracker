package ebuero.aatasiei.tracker.api.internal.helpers;

import ebuero.aatasiei.tracker.api.model.NewIssueRequest;
import ebuero.aatasiei.tracker.model.entities.Developer;
import ebuero.aatasiei.tracker.model.entities.Issue;
import ebuero.aatasiei.tracker.services.interfaces.DeveloperService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import static com.google.common.base.Strings.nullToEmpty;
import static ebuero.aatasiei.tracker.utils.MorePreconditions.checkArgumentNonNull;
import static ebuero.aatasiei.tracker.utils.MorePreconditions.checkArgumentNotBlank;
import static java.util.Objects.nonNull;

/**
 * Abstract controller containing default behaviour for when dealing with {@link Issue} instances.
 *
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
public abstract class AbstractIssueController {

    protected final DeveloperService developerService;

    protected AbstractIssueController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    /**
     * Updates the issue based on the provided request. Also validates some of the required fields.
     *
     * @param request - the request to use when updating the issue. Not null.
     * @param issue   - the issue to update. Not null.
     */
    protected void updateIssue(@RequestBody NewIssueRequest request, Issue issue) {
        validateTitleAndStatus(request);
        issue.setTitle(request.getTitle().trim());
        issue.setDescription(nullToEmpty(request.getDescription()).trim());
        issue.setAssignedDeveloper(getAssignedDeveloper(request.getAssignedDeveloperId()));
    }

    /**
     * Attempts to retrieve the {@link Developer} instance matching the passed id.
     *
     * @param assignedDeveloperId - a developer id.
     *
     * @return the developer, if found. null, otherwise.
     */
    // FIXME: move to services
    @Transactional(readOnly = true)
    protected Developer getAssignedDeveloper(Long assignedDeveloperId) {

        Developer developer = null;

        if (nonNull(assignedDeveloperId)) {
            developer = developerService.getDeveloper(assignedDeveloperId).orElse(null);
        }

        return developer;
    }

    /**
     * Validates that the request, its title, and its status are not null or empty (in the case of strings).
     *
     * @param request - the request to validate. Not null.
     */
    protected void validateTitleAndStatus(NewIssueRequest request) {
        checkArgumentNonNull(request, "Issue is null.");
        checkArgumentNotBlank(request.getTitle(), "Title is null or empty.");
        checkArgumentNonNull(request.getStatus(), "Status is null.");
    }

}
