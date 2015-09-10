package ebuero.aatasiei.tracker.api.controllers;

import ebuero.aatasiei.tracker.api.internal.helpers.AbstractIssueController;
import ebuero.aatasiei.tracker.api.model.IssueResponse;
import ebuero.aatasiei.tracker.api.model.NewIssueRequest;
import ebuero.aatasiei.tracker.model.entities.Bug;
import ebuero.aatasiei.tracker.model.entities.Developer;
import ebuero.aatasiei.tracker.model.enums.BugPriority;
import ebuero.aatasiei.tracker.model.enums.BugStatus;
import ebuero.aatasiei.tracker.services.interfaces.BugService;
import ebuero.aatasiei.tracker.services.interfaces.DeveloperService;
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
import static ebuero.aatasiei.tracker.utils.MorePreconditions.checkArgumentNonNull;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@RestController
@RequestMapping("/bug")
public class BugController extends AbstractIssueController {

    private final BugService service;

    @Autowired
    public BugController(DeveloperService developerService, BugService service) {
        super(developerService);
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueResponse> get(@PathVariable Long id) {

        Optional<Bug> foundBug = service.getIssue(id);

        if (!foundBug.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new IssueResponse(foundBug.get()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueResponse> update(@PathVariable Long id, @RequestBody NewIssueRequest request) {

        Optional<Bug> foundBug = service.getIssue(id);

        if (!foundBug.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Bug bug = updateBug(foundBug.get(), request);

        return new ResponseEntity<>(new IssueResponse(service.updateIssue(bug)), HttpStatus.OK);
    }

    private Bug updateBug(final Bug bug, NewIssueRequest request) {

        updateIssue(request, bug);

        bug.setPriority(getBugPriority(request));
        bug.setStatus(getBugStatus(request));

        return bug;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueResponse> create(@RequestBody NewIssueRequest request) {

        Bug createdBug = createBug(request);

        return new ResponseEntity<>(new IssueResponse(createdBug), HttpStatus.CREATED);
    }

    //FIXME: move to services
    @Transactional
    private Bug createBug(NewIssueRequest request) {

        validateTitleAndStatus(request);

        checkArgumentNonNull(request.getPriority(), "Priority is null.");

        final Developer developer = getAssignedDeveloper(request.getAssignedDeveloperId());

        final BugStatus status = getBugStatus(request);
        final BugPriority priority = getBugPriority(request);

        return service.createBug(request.getTitle().trim(), nullToEmpty(request.getDescription()).trim(), developer,
                priority, status);
    }

    private BugPriority getBugPriority(NewIssueRequest request) {
        BugPriority priority = null;
        try {
            priority = BugPriority.valueOf(request.getPriority());
        } catch (IllegalArgumentException ex) {
            checkArgument(false, String.format("%s is not a proper priority.", String.valueOf(request.getPriority())));
        }
        return priority;
    }

    private BugStatus getBugStatus(NewIssueRequest request) {
        BugStatus status = null;
        try {
            status = BugStatus.valueOf(request.getStatus());
        } catch (IllegalArgumentException ex) {
            checkArgument(false, String.format("%s is not a proper status.", String.valueOf(request.getStatus())));
        }
        return status;
    }

}
