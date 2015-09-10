package ebuero.aatasiei.tracker.api.controllers;

import ebuero.aatasiei.tracker.api.internal.helpers.AbstractIssueController;
import ebuero.aatasiei.tracker.api.model.IssueResponse;
import ebuero.aatasiei.tracker.api.model.NewIssueRequest;
import ebuero.aatasiei.tracker.model.entities.Developer;
import ebuero.aatasiei.tracker.model.entities.Story;
import ebuero.aatasiei.tracker.model.enums.StoryStatus;
import ebuero.aatasiei.tracker.services.interfaces.DeveloperService;
import ebuero.aatasiei.tracker.services.interfaces.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.nullToEmpty;
import static ebuero.aatasiei.tracker.model.enums.StoryStatus.NEW;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@RestController
@RequestMapping("/story")
public class StoryController extends AbstractIssueController {

    private final StoryService service;

    @Autowired
    public StoryController(DeveloperService developerService, StoryService service) {
        super(developerService);
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueResponse> get(@PathVariable Long id) {

        Optional<Story> foundStory = service.getIssue(id);

        if (!foundStory.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new IssueResponse(foundStory.get()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueResponse> update(@PathVariable Long id, @RequestBody NewIssueRequest request) {
        Optional<Story> foundStory = service.getIssue(id);

        if (!foundStory.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final Story story = updateStory(foundStory.get(), request);
        return new ResponseEntity<>(new IssueResponse(service.updateIssue(story)), HttpStatus.OK);
    }

    private Story updateStory(Story story, @RequestBody NewIssueRequest request) {

        updateIssue(request, story);

        validateEstimate(request);

        story.setEstimatedPoints(request.getEstimate());
        story.setStatus(getStoryStatus(request));

        return story;
    }


    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueResponse> create(@RequestBody NewIssueRequest request) {

        final Story createdStory = createStory(request);

        return new ResponseEntity<>(new IssueResponse(createdStory), HttpStatus.CREATED);
    }

    // FIXME: move to services
    @Transactional
    private Story createStory(NewIssueRequest request) {

        validateTitleAndStatus(request);
        validateEstimate(request);

        final StoryStatus status = getStoryStatus(request);

        checkArgument(status == NEW || nonNull(request.getEstimate()), "Stories that are ESTIMATED or CLOSED must " +
                "have their estimate set");

        final Developer developer = getAssignedDeveloper(request.getAssignedDeveloperId());

        return service.createStory(request.getTitle().trim(), nullToEmpty(request.getDescription()).trim(), developer, request.getEstimate(), status);
    }

    private void validateEstimate(NewIssueRequest request) {
        checkArgument(isNull(request.getEstimate()) || request.getEstimate() >= 0, "Negative estimate.");
    }

    private StoryStatus getStoryStatus(NewIssueRequest request) {
        StoryStatus status = null;
        try {
            status = StoryStatus.valueOf(request.getStatus());
        } catch (IllegalArgumentException ex) {
            checkArgument(false, String.format("%s is not a proper status.", String.valueOf(request.getStatus())));
        }
        return status;
    }

}
