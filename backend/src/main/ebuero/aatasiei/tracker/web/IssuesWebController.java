package ebuero.aatasiei.tracker.web;

import com.google.common.base.Strings;
import ebuero.aatasiei.tracker.api.model.IssueResponse;
import ebuero.aatasiei.tracker.model.entities.Issue;
import ebuero.aatasiei.tracker.model.enums.IssueType;
import ebuero.aatasiei.tracker.services.interfaces.BugService;
import ebuero.aatasiei.tracker.services.interfaces.IssuesService;
import ebuero.aatasiei.tracker.services.interfaces.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@Controller
public class IssuesWebController {

    @Autowired
    private IssuesService issuesService;

    @Autowired
    private BugService bugService;

    @Autowired
    private StoryService storyService;

    @RequestMapping(value = "/issues.html", method = RequestMethod.GET)
    String issues(@RequestParam(value = "type", required = false) IssueType type,
                  @RequestParam(value = "issue", required = false) Long issueId,
                  @RequestParam(value = "filter", required = false) String filter,
                  Model model) {

        IssueResponse response = getIssueResponse(type, issueId);

        model.addAttribute("editedIssue", response);
        if (Strings.isNullOrEmpty(filter)) {
            filter = "all";
        }
        model.addAttribute("issues", populateIssues(filter));

        return "issues";
    }

    private IssueResponse getIssueResponse(IssueType type, Long issueId) {

        IssueResponse response = new IssueResponse();

        if (nonNull(issueId)) {

            checkArgument(nonNull(type), "Type must not be null when an issue id is specified");
            final Optional<? extends Issue> issue = getIssue(type, issueId);

            if (issue.isPresent()) {
                response = new IssueResponse(issue.get());
            }
        }

        return response;
    }

    private Optional<? extends Issue> getIssue(IssueType type, Long issueId) {
        switch (type) {
            case BUG:
                return bugService.getIssue(issueId);
            case STORY:
                return storyService.getIssue(issueId);
        }
        return storyService.getIssue(issueId);
    }

    private List<Issue> populateIssues(String filter) {

        switch (filter.toLowerCase(Locale.ENGLISH)) {
            case "open":
                return issuesService.getAllOpenOrderedByCreationDate();
            default:
                return issuesService.getAllOrderedByCreationDate();
        }

    }
}
