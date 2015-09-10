package ebuero.aatasiei.tracker.services.impl;

import ebuero.aatasiei.tracker.Application;
import ebuero.aatasiei.tracker.model.entities.Story;
import ebuero.aatasiei.tracker.model.entities.WeeklyPlan;
import ebuero.aatasiei.tracker.model.enums.StoryStatus;
import ebuero.aatasiei.tracker.services.interfaces.DeveloperService;
import ebuero.aatasiei.tracker.services.interfaces.IssuesService;
import ebuero.aatasiei.tracker.services.interfaces.ProjectPlanService;
import ebuero.aatasiei.tracker.services.interfaces.StoryService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static ebuero.aatasiei.tracker.model.enums.StoryStatus.ESTIMATED;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ProjectPlanServiceIntegrationTests {

    @Autowired
    protected ProjectPlanService service;

    @Autowired
    protected StoryService storyService;

    @Autowired
    protected DeveloperService developerService;

    @Autowired
    protected IssuesService issuesService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void recreateEmptyPlan() {

        assertThatPlanIsEmpty();
    }

    @After
    public void tearDown() {
        jdbcTemplate.execute("TRUNCATE SCHEMA public AND COMMIT");
    }

    private void assertThatPlanIsEmpty() {
        final List<WeeklyPlan> previousPlan = service.getProjectPlan();
        assertThat(previousPlan, is(empty()));

        service.recomputeProjectPlan();

        final List<WeeklyPlan> plan = service.getProjectPlan();
        assertThat(plan, is(empty()));
    }

    @Test
    public void createEmptyPlanWhenNoDeveloperIsStored() {

        createThreeStories(ESTIMATED);

        assertThatPlanIsEmpty();
    }

    @Test
    public void createEmptyPlanWhenNoStoryIsEstimated() {

        createThreeStories(StoryStatus.NEW);

        assertThat(developerService.getDeveloperCount(), is(equalTo(0L)));
        developerService.createDeveloper("Test Dev");
        assertThat(developerService.getDeveloperCount(), is(equalTo(1L)));

        assertThatPlanIsEmpty();
    }

    private void createThreeStories(StoryStatus status) {
        assertThat(issuesService.getAllOrderedByCreationDate(), is(empty()));
        storyService.createStory("test1", "test description", null, status != ESTIMATED ? null : 1, status);
        storyService.createStory("test2", "test description", null, status != ESTIMATED ? null : 2, status);
        storyService.createStory("test3", "test description", null, status != ESTIMATED ? null : 3, status);
        assertThat(issuesService.getAllOrderedByCreationDate(), hasSize(3));
    }

    @Test
    public void createPlanForFourDevelopersExampleStories() {

        assertThat(developerService.getDeveloperCount(), is(equalTo(0L)));
        developerService.createDeveloper("Test Dev 1");
        developerService.createDeveloper("Test Dev 2");
        developerService.createDeveloper("Test Dev 3");
        developerService.createDeveloper("Test Dev 4");
        assertThat(developerService.getDeveloperCount(), is(equalTo(4L)));


        assertThat(issuesService.getAllOrderedByCreationDate(), is(empty()));

        storyService.createStory("Story 1", "test description", null, 14, ESTIMATED);
        storyService.createStory("Story 2", "test description", null, 22, ESTIMATED);
        storyService.createStory("Story 3", "test description", null, 4, ESTIMATED);
        storyService.createStory("Story 5", "test description", null, 14, ESTIMATED);
        storyService.createStory("Story 7", "test description", null, 20, ESTIMATED);
        storyService.createStory("Story 15", "test description", null, 10, ESTIMATED);
        assertThat(issuesService.getAllOrderedByCreationDate(), hasSize(6));

        final List<WeeklyPlan> plan = service.getProjectPlan();

        assertThat(plan, hasSize(3));

        for (WeeklyPlan weeklyPlan : plan) {
            int points = getStoryPoints(weeklyPlan);

            assertThat(points, is(lessThanOrEqualTo(40)));
        }

    }

    @Test
    public void createPlanForFourDevelopersExactStories() {

        assertThat(developerService.getDeveloperCount(), is(equalTo(0L)));
        developerService.createDeveloper("Test Dev 1");
        developerService.createDeveloper("Test Dev 2");
        developerService.createDeveloper("Test Dev 3");
        developerService.createDeveloper("Test Dev 4");
        assertThat(developerService.getDeveloperCount(), is(equalTo(4L)));


        assertThat(issuesService.getAllOrderedByCreationDate(), is(empty()));

        storyService.createStory("Story 1", "test description", null, 40, ESTIMATED);
        storyService.createStory("Story 2", "test description", null, 40, ESTIMATED);
        storyService.createStory("Story 3", "test description", null, 40, ESTIMATED);
        storyService.createStory("Story 5", "test description", null, 40, ESTIMATED);
        storyService.createStory("Story 7", "test description", null, 40, ESTIMATED);
        storyService.createStory("Story 15", "test description", null, 40, ESTIMATED);
        assertThat(issuesService.getAllOrderedByCreationDate(), hasSize(6));

        final List<WeeklyPlan> plan = service.getProjectPlan();

        assertThat(plan, hasSize(6));

        for (WeeklyPlan weeklyPlan : plan) {
            int points = getStoryPoints(weeklyPlan);

            assertThat(points, is(equalTo(40)));
        }

    }

    @Test
    public void createPlanForFourDevelopersAllStoriesAbove40() {

        assertThat(developerService.getDeveloperCount(), is(equalTo(0L)));
        developerService.createDeveloper("Test Dev 1");
        developerService.createDeveloper("Test Dev 2");
        developerService.createDeveloper("Test Dev 3");
        developerService.createDeveloper("Test Dev 4");
        assertThat(developerService.getDeveloperCount(), is(equalTo(4L)));


        assertThat(issuesService.getAllOrderedByCreationDate(), is(empty()));

        storyService.createStory("Story 1", "test description", null, 41, ESTIMATED);
        storyService.createStory("Story 2", "test description", null, 41, ESTIMATED);
        storyService.createStory("Story 3", "test description", null, 41, ESTIMATED);
        storyService.createStory("Story 5", "test description", null, 41, ESTIMATED);
        storyService.createStory("Story 7", "test description", null, 41, ESTIMATED);
        storyService.createStory("Story 15", "test description", null, 41, ESTIMATED);
        assertThat(issuesService.getAllOrderedByCreationDate(), hasSize(6));

        final List<WeeklyPlan> plan = service.getProjectPlan();

        assertThat(plan, is(empty()));
    }

    @Test
    public void createPlanForFourDevelopersAllStories1() {

        assertThat(developerService.getDeveloperCount(), is(equalTo(0L)));
        developerService.createDeveloper("Test Dev 1");
        developerService.createDeveloper("Test Dev 2");
        developerService.createDeveloper("Test Dev 3");
        developerService.createDeveloper("Test Dev 4");
        assertThat(developerService.getDeveloperCount(), is(equalTo(4L)));


        assertThat(issuesService.getAllOrderedByCreationDate(), is(empty()));

        storyService.createStory("Story 1", "test description", null, 1, ESTIMATED);
        storyService.createStory("Story 2", "test description", null, 1, ESTIMATED);
        storyService.createStory("Story 3", "test description", null, 1, ESTIMATED);
        storyService.createStory("Story 5", "test description", null, 1, ESTIMATED);
        storyService.createStory("Story 7", "test description", null, 1, ESTIMATED);
        storyService.createStory("Story 15", "test description", null, 1, ESTIMATED);
        assertThat(issuesService.getAllOrderedByCreationDate(), hasSize(6));

        final List<WeeklyPlan> plan = service.getProjectPlan();

        assertThat(plan, hasSize(1));

        int points = getStoryPoints(plan.get(0));

        assertThat(points, is(equalTo(6)));
    }

    private int getStoryPoints(WeeklyPlan weeklyPlan) {
        return weeklyPlan.getStories().stream().
                mapToInt(Story::getEstimatedPoints).
                reduce(0, (left, right) -> left + right);
    }
}
