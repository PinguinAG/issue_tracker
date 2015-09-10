package ebuero.aatasiei.tracker.web;

import ebuero.aatasiei.tracker.model.entities.WeeklyPlan;
import ebuero.aatasiei.tracker.services.interfaces.DeveloperService;
import ebuero.aatasiei.tracker.services.interfaces.ProjectPlanService;
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
public class PlanWebController {

    @Autowired
    private ProjectPlanService projectPlanService;

    @Autowired
    private DeveloperService developerService;

    @RequestMapping(value = "/plan.html", method = RequestMethod.GET)
    String plan() {
        return "plan";
    }

    @ModelAttribute("weeklyPlans")
    public List<WeeklyPlan> populateWeeklyPlans() {
        return projectPlanService.getProjectPlan();
    }

    @ModelAttribute("maxPoints")
    public Long populateMaxPoints() {
        return developerService.getDeveloperCount() * ProjectPlanService.DEVELOPER_POINTS_PER_WEEK;
    }
}
