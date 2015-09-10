package ebuero.aatasiei.tracker.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import ebuero.aatasiei.tracker.model.entities.Bug;
import ebuero.aatasiei.tracker.model.entities.Issue;
import ebuero.aatasiei.tracker.model.entities.Story;
import ebuero.aatasiei.tracker.model.enums.IssueType;

import java.util.Objects;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IssueResponse extends NewIssueRequest {

    private Long id;
    private DeveloperResponse assignedDeveloper;

    public IssueResponse() {
    }

    public IssueResponse(Issue issue) {

        this.creationDate = issue.getCreationDate();
        this.issueType = issue.getIssueType().toString();

        if (Objects.nonNull(issue.getAssignedDeveloper())) {
            this.assignedDeveloper = new DeveloperResponse(issue.getAssignedDeveloper());
        }

        this.description = issue.getDescription();
        this.title = issue.getTitle();

        this.id = issue.getId();

        if (Objects.nonNull(issue.getIssueType())) {
            populateConcreteFields(issue, issue.getIssueType());
        }
    }

    private void populateConcreteFields(Issue issue, IssueType issueType) {
        switch (issueType) {
            case BUG:
                populateBugFields((Bug) issue);
                break;
            case STORY:
                populateStoryFields((Story) issue);
                break;
        }
    }

    private void populateStoryFields(Story issue) {
        this.estimate = issue.getEstimatedPoints();
        this.status = issue.getStatus().toString();
    }

    private void populateBugFields(Bug issue) {
        this.priority = issue.getPriority().toString();
        this.status = issue.getStatus().toString();
    }

    public Long getId() {
        return id;
    }

    public DeveloperResponse getAssignedDeveloper() {
        return assignedDeveloper;
    }
}
