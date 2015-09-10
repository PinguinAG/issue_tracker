package ebuero.aatasiei.tracker.model.entities;

import com.google.common.base.MoreObjects;
import ebuero.aatasiei.tracker.model.enums.StoryStatus;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

import static ebuero.aatasiei.tracker.model.enums.IssueType.Values.STORY;

/**
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
@Entity
@DiscriminatorValue(STORY)
public class Story extends Issue {

    private Integer estimatedPoints;

    protected Story() {
        setStatus(StoryStatus.NEW);
    }

    public Story(String title) {
        super(title);
    }

    public Integer getEstimatedPoints() {
        return estimatedPoints;
    }

    public void setEstimatedPoints(Integer estimatedPoints) {
        this.estimatedPoints = estimatedPoints;
    }

    public StoryStatus getStatus() {
        return StoryStatus.valueOf(status);
    }

    public void setStatus(StoryStatus status) {
        this.status = status.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Story)) return false;

        if (!super.equals(o)) return false;

        final Story story = (Story) o;
        return Objects.equals(estimatedPoints, story.estimatedPoints) &&
                status == story.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), estimatedPoints, status);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("estimatedPoints", estimatedPoints)
                .add("status", status)
                .toString();
    }
}
