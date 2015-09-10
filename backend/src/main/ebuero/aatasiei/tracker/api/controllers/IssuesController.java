package ebuero.aatasiei.tracker.api.controllers;

import ebuero.aatasiei.tracker.api.model.IssuesResponse;
import ebuero.aatasiei.tracker.services.interfaces.IssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@RestController
@RequestMapping("/issues")
public class IssuesController {

    @Autowired
    private IssuesService service;

    @RequestMapping(value="/", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public IssuesResponse getAll(){
        return new IssuesResponse(service.getAllOrderedByCreationDate());
    }

    @RequestMapping(value="/open/", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public IssuesResponse getAllOpen(){
        return new IssuesResponse(service.getAllOpenOrderedByCreationDate());
    }
}
