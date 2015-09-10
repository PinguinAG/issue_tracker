package ebuero.aatasiei.tracker.model.entities;

import com.google.common.collect.Sets;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.beans.Transient;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Entity representing a week in a project plan. A project plan will contain multiple weekly plans.
 *
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
@Entity
public class WeeklyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer index;

    // only reason to load a weekly plan is to get the stories
    @OneToMany(targetEntity = Story.class, fetch = FetchType.EAGER)
    private Collection<Story> stories = Sets.newHashSet();

    protected WeeklyPlan() {
    }

    /**
     * Constructor that supplies the week plan index.
     *
     * @param index - positive value.
     */
    public WeeklyPlan(int index) {
        checkArgument(index > 0, "Index must be 1 based (positive).");
    }

    public Long getId() {
        return id;
    }

    protected Integer getIndex() {
        return index;
    }

    protected void setIndex(Integer index) {
        this.index = index;
    }

    public Collection<Story> getStories() {
        return stories;
    }

    protected void setStories(Collection<Story> stories) {
        this.stories = stories;
    }

    /**
     * Adds a story to the list of stories of this weekly plan.
     *
     * @param story - persisted {@link Story}. Not null.
     */
    public void addStory(Story story) {
        requireNonNull(story);
        this.stories.add(story);
    }

    /**
     * The sum of the estimation points, for all the stories contained by this instance.
     *
     * @return int value.
     */
    @Transient
    public int getTotalPoints() {
        return stories.stream().mapToInt(Story::getEstimatedPoints).reduce(0, (left, right) -> left + right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        final List<String> asStrings = stories.stream().map(Story::toString).collect(Collectors.toList());
        return String.join(",", asStrings);
    }
}
