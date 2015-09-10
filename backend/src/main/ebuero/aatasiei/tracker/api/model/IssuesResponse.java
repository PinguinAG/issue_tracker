package ebuero.aatasiei.tracker.api.model;

import ebuero.aatasiei.tracker.model.entities.Issue;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
public class IssuesResponse {

    private List<IssueResponse> issues;

    public IssuesResponse(List<Issue> issues) {
        this.issues = issues.stream()
                .map(IssueResponse::new)
                .collect(Collectors.toList());
    }

    public List<IssueResponse> getIssues() {
        return issues;
    }
}
