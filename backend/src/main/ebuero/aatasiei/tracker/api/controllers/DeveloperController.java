package ebuero.aatasiei.tracker.api.controllers;

import ebuero.aatasiei.tracker.api.model.DeveloperResponse;
import ebuero.aatasiei.tracker.api.model.DevelopersResponse;
import ebuero.aatasiei.tracker.api.model.NewDeveloperRequest;
import ebuero.aatasiei.tracker.model.entities.Developer;
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

import static ebuero.aatasiei.tracker.utils.MorePreconditions.checkArgumentNonNull;
import static ebuero.aatasiei.tracker.utils.MorePreconditions.checkArgumentNotBlank;

@RestController
@RequestMapping("/")
public class DeveloperController {

    private final DeveloperService service;

    @Autowired
    public DeveloperController(DeveloperService service) {
        this.service = service;
    }

    @RequestMapping(value = "developers/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public DevelopersResponse getAll() {
        return new DevelopersResponse(service.getAllDevelopers());
    }

    @RequestMapping(value = "developer/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeveloperResponse> create(@RequestBody NewDeveloperRequest request) {

        checkArgumentNonNull(request, "Developer is null.");
        checkArgumentNotBlank(request.getName(), "Developer name is null or empty.");

        final Developer createdDeveloper = service.createDeveloper(request.getName());

        return new ResponseEntity<>(new DeveloperResponse(createdDeveloper), HttpStatus.CREATED);
    }

    @RequestMapping(value = "developer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeveloperResponse> get(@PathVariable Long id) {

        Optional<Developer> foundDeveloper = service.getDeveloper(id);

        if (foundDeveloper.isPresent()) {
            return new ResponseEntity<>(new DeveloperResponse(foundDeveloper.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "developer/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        // TODO: replace with an exists check
        Optional<Developer> foundDeveloper = service.getDeveloper(id);

        if (foundDeveloper.isPresent()) {

            service.deleteDeveloper(foundDeveloper.get());

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
