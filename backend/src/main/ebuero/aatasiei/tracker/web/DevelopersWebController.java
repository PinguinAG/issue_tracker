package ebuero.aatasiei.tracker.web;

import ebuero.aatasiei.tracker.model.entities.Developer;
import ebuero.aatasiei.tracker.services.interfaces.DeveloperService;
import ebuero.aatasiei.tracker.services.interfaces.IssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@Controller
public class DevelopersWebController {

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private IssuesService issuesService;

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    String index() {
        return "index";
    }

    @ModelAttribute("developers")
    public List<Developer> populateDevelopers() {
        return developerService.getAllDevelopers();
    }
}
