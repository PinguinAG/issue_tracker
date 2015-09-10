package ebuero.aatasiei.tracker.api.controllers;

import ebuero.aatasiei.tracker.api.model.ProjectPlanResponse;
import ebuero.aatasiei.tracker.model.entities.WeeklyPlan;
import ebuero.aatasiei.tracker.services.interfaces.ProjectPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@RestController
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    private ProjectPlanService service;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectPlanResponse get() {
        final List<WeeklyPlan> projectPlan = service.getProjectPlan();
        return new ProjectPlanResponse(projectPlan);
    }

}
