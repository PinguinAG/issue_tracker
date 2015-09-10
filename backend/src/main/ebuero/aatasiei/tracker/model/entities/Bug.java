package ebuero.aatasiei.tracker.model.entities;

import com.google.common.base.MoreObjects;
import ebuero.aatasiei.tracker.model.enums.BugPriority;
import ebuero.aatasiei.tracker.model.enums.BugStatus;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

import static ebuero.aatasiei.tracker.model.enums.IssueType.Values.BUG;

/**
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
@Entity
@DiscriminatorValue(BUG)
public class Bug extends Issue {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BugPriority priority = BugPriority.CRITICAL;

    protected Bug() {
        setStatus(BugStatus.NEW);
    }

    public Bug(String title) {
        super(title);
    }

    public BugPriority getPriority() {
        return priority;
    }

    public void setPriority(BugPriority priority) {
        this.priority = priority;
    }

    public BugStatus getStatus() {
        return BugStatus.valueOf(status);
    }

    public void setStatus(BugStatus status) {
        this.status = status.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Bug)) return false;

        if (!super.equals(o)) return false;

        final Bug bug = (Bug) o;
        return priority == bug.priority && status == bug.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), priority, status);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("priority", priority)
                .add("status", status)
                .toString();
    }
}
